package javagames.framework;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.*;
import javagames.util.*;

import javagames.player.KeyboardInput;
import javagames.player.RelativeMouseInput;

public abstract class GameFramework extends JFrame implements Runnable {
	
	private BufferStrategy bs;
	private volatile boolean running;
	private Thread gameThread;
	protected int vx;
	protected int vy;
	protected int vw;
	protected int vh;
	protected FrameRate frameRate;
	protected RelativeMouseInput mouse;
	protected KeyboardInput keyboard;
	protected Color appBackground = GameConstants.APP_BACKGROUND;
	protected Color appBorder = GameConstants.APP_BORDER;
	protected Color appFPSColor = Color.GREEN;
	protected Font appFont = new Font("Courier New", Font.PLAIN, 14);
	protected String appTitle = GameConstants.APP_TITLE;
	protected float appBorderScale = GameConstants.BORDER_SCALE;
	protected int appWidth = GameConstants.APP_WIDTH;
	protected int appHeight = GameConstants.APP_HEIGHT;
	protected float appWorldWidth = GameConstants.WORLD_WIDTH;
	protected float appWorldHeight = GameConstants.WORLD_HEIGHT;
	protected long appSleep = GameConstants.APP_SLEEP;
	protected boolean appMaintainRatio = GameConstants.MAINTAIN_RATIO;
	protected boolean appDisableCursor = GameConstants.DISABLE_CURSOR;
	protected int textPos = 0;
	public	  Random rng;
	
	public GameFramework() {
		rng = new Random();
	}

	protected abstract void createFramework();

	protected abstract void renderFrame(Graphics g);

	public abstract int getScreenWidth();

	public abstract int getScreenHeight();

	protected void createAndShowGUI() {
		createFramework();
		if (appDisableCursor) {
			disableCursor();
		}
		gameThread = new Thread(this);
		gameThread.start();
	}

	protected void setupInput(Component component) {
		keyboard = new KeyboardInput();
		component.addKeyListener(keyboard);
		mouse = new RelativeMouseInput(component);
		component.addMouseListener(mouse);
		component.addMouseMotionListener(mouse);
		component.addMouseWheelListener(mouse);
	}

	protected void createBufferStrategy(Canvas component) {
		component.createBufferStrategy(2);
		bs = component.getBufferStrategy();
	}

	protected void createBufferStrategy(Window window) {
		window.createBufferStrategy(2);
		bs = window.getBufferStrategy();
	}

	protected void setupViewport(int sw, int sh) {
		int w = (int) (sw * appBorderScale);
		int h = (int) (sh * appBorderScale);
		int x = (sw - w) / 2;
		int y = (sh - h) / 2;
		vw = w;
		vh = (int) (w * appWorldHeight / appWorldWidth);
		if (vh > h) {
			vw = (int) (h * appWorldWidth / appWorldHeight);
			vh = h;
		}
		vx = x + (w - vw) / 2;
		vy = y + (h - vh) / 2;
	}

	public Vector2f getWorldSize()
	{
		return new Vector2f(appWorldWidth, appWorldHeight);
	}
	
	protected Matrix3x3f getViewportTransform() {
		return Utility.createViewport(appWorldWidth, appWorldHeight,
				getScreenWidth(), getScreenHeight());
	}

	protected Matrix3x3f getReverseViewportTransform() {
		return Utility.createReverseViewport(appWorldWidth, appWorldHeight,
				getScreenWidth(), getScreenHeight());
	}

	protected Vector2f getWorldMousePosition() {
		Matrix3x3f screenToWorld = getReverseViewportTransform();
		Point mousePos = mouse.getPosition();
		Vector2f screenPos = new Vector2f(mousePos.x, mousePos.y);
		return screenToWorld.mul(screenPos);
	}

	protected Vector2f getRelativeWorldMousePosition() {
		float sx = appWorldWidth / (getScreenWidth() - 1);
		float sy = appWorldHeight / (getScreenHeight() - 1);
		Matrix3x3f viewport = Matrix3x3f.scale(sx, -sy);
		Point p = mouse.getPosition();
		return viewport.mul(new Vector2f(p.x, p.y));
	}

	public void run() {
		running = true;
		initialize();
		long curTime = System.nanoTime();
		long lastTime = curTime;
		double nsPerFrame;
		while (running) {
			curTime = System.nanoTime();
			nsPerFrame = curTime - lastTime;
			gameLoop((float) (nsPerFrame / 1.0E9));
			lastTime = curTime;
		}
		terminate();
	}

	protected void initialize() {
		frameRate = new FrameRate();
		frameRate.initialize();
	}

	protected void terminate() {
	}

	private void gameLoop(float delta) {
		processInput(delta);
		updateObjects(delta);
		renderFrame();
		sleep(appSleep);
	}

	private void renderFrame() {
		do {
			do {
				Graphics g = null;
				try {
					g = bs.getDrawGraphics();
					renderFrame(g);
				} finally {
					if (g != null) {
						g.dispose();
					}
				}
			} while (bs.contentsRestored());
			bs.show();
		} while (bs.contentsLost());
	}

	private void sleep(long sleep) {
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException ex) {
		}
	}

	protected void processInput(float delta) {
		keyboard.poll();
		mouse.poll();
		mouse.setWorldPosition(getWorldMousePosition());
	}

	protected void updateObjects(float delta) {
	}

	protected void render(Graphics g) {
		g.setFont(appFont);
		g.setColor(appFPSColor);
		frameRate.calculate();
		textPos = Utility.drawString(g, 20, 0, frameRate.getFrameRate());
	}

	private void disableCursor() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image image = tk.createImage("");
		Point point = new Point(0, 0);
		String name = "CanBeAnything";
		Cursor cursor = tk.createCustomCursor(image, point, name);
		setCursor(cursor);
	}

	protected void shutDown() {
		if (Thread.currentThread() != gameThread) {
			try {
				running = false;
				gameThread.join();
				onShutDown();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.exit(0);
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					shutDown();
				}
			});
		}
	}

	protected void onShutDown() {
	}

	protected static void launchApp(final GameFramework app) {
		app.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				app.shutDown();
			}
		});
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				app.createAndShowGUI();
			}
		});
	}
}