package org.tetris;

import org.tetris.screen.Painter;
import org.tetris.screen.Screen;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarFile;

public class Tetris {
	private static Screen screen;
	private static Game game;
	private static long score;
	public static void main(String[] args) {
		game = new Game();
		game.registerListener(new Listener());
		screen = new Screen();
		new Thread(() -> {
			while (true) {
				Painter.updateScreen(getScreen(), getGame());
				try {
					Thread.sleep(10L);
				} catch (InterruptedException ignored) {
				}
			}
		}, "ScreenUpdateThread").start();
		loadMods();
	}
	public static void loadMods() {
		File dir = new File("mods");
		if (!dir.exists()) dir.mkdir();
		if (dir.listFiles() == null) return;
		for (File modFile : dir.listFiles()) {
			try {
				JarFile jarFile = new JarFile(modFile);
				URLClassLoader loader = new URLClassLoader(new URL[] {new URL("jar:" + modFile.toURI().toURL() + "!/")});
				String className = new String(loader.getResourceAsStream("mainClass.data").readAllBytes());
				Class<?> clazz = loader.loadClass(className);
				Object instance = clazz.newInstance();
				if (instance instanceof TetrisMod mod) {
					mod.onEnable();
				}
			} catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException ignored) {
			}
		}
	}
	public static Screen getScreen() {
		return screen;
	}
	public static Game getGame() {
		return game;
	}
	public static void gameOver() {
		game.unregisterAll();
		screen.removeKeyListener(screen.listener);
		game = null;
	}
	public static long getScore() {
		return score;
	}
	public static void addScore(long score) {
		Tetris.score += score;
	}
	public static long calcScore(int lineCount) {
		if (lineCount == 0) return 0;
		long score = 1L;
		for (int ignored : new int[lineCount]) {
			score *= 2L;
		}
		return score * 100L;
	}
}
