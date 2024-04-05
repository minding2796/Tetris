package org.tetris.screen;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class DrawerPanel extends JPanel {
	Consumer<Graphics> function = null;
	public void paint(Graphics g, Consumer<Graphics> function) {
		this.function = function;
		this.paint(g);
		updateUI();
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (function == null) return;
		function.accept(g);
	}
}
