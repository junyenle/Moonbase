package Driver;

import javax.swing.JFrame;

public class Game {

	public static void main(String[] args)
	{
		JFrame window = new JFrame("Coliseum");
		window.setContentPane(new GamePanel());
		window.setUndecorated(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
	}
	
}
