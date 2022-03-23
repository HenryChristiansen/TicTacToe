import javax.swing.*;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;
import java.io.PrintStream;

@SuppressWarnings("serial")
public class TicTacToe extends JFrame {
	public static boolean singlePlayer = false;
	public static boolean xTurn = true;
	public static boolean play = false;
	public static boolean randomTurnTwoPlayer = false;
	JLabel title = new JLabel("Tic Tac Toe");
	static JLabel turn = new JLabel("X's Turn");
	CustomButton[][] g = new CustomButton[3][3];
	Font font = new Font("Garamond", Font.PLAIN, 40);
	Font btnFont = new Font("Garamond", Font.PLAIN, 20);
	static Object syncObject = new Object();
	JButton playAgain = new JButton("Play Again");
	JButton playButton = new JButton("Play");
	JButton switchGameMode = new JButton("Change Mode");
	JLabel modeChangeError = new JLabel("Finish Game First");
	JLabel modeText = new JLabel("Mode: Two Player");
	JPanel gamePanel = new JPanel(new GridLayout(3, 3));
	static Object repeat = new Object();
	static JLabel playGameError = new JLabel("Press Play to Start");
	Random randomGenerator = new Random();
	int modeHelper = 2;

	TicTacToe() {
		this.setSize(635, 739);
		this.getContentPane().setBackground(Color.black);
		this.setVisible(true);
		this.setLayout(null);
		modeChangeError.setForeground(Color.red);
		modeChangeError.setFont(new Font("Garamond", Font.PLAIN, 17));
		modeChangeError.setBounds(450, 66, 150, 20);
		modeChangeError.setVisible(false);
		playGameError.setForeground(Color.red);
		playGameError.setFont(new Font("Garamond", Font.PLAIN, 17));
		playGameError.setVisible(false);
		playGameError.setBounds(50,66,150,20);
		modeText.setBounds(250, 66, 200, 20);
		modeText.setFont(new Font("Garamond", Font.PLAIN, 17));
		modeText.setForeground(Color.white);
		playAgain.setBounds(50, 24, 120, 40);
		playButton.setBounds(50, 24, 120, 40);
		switchGameMode.setBounds(450, 24, 120, 40);
		playAgain.setFont(btnFont);
		playButton.setFont(btnFont);
		switchGameMode.setFont(btnFont);
		playAgain.setBorder(BorderFactory.createLineBorder(Color.white, 1));
		playButton.setBorder(BorderFactory.createLineBorder(Color.white, 1));
		switchGameMode.setBorder(BorderFactory.createLineBorder(Color.white, 1));
		playAgain.setForeground(Color.white);
		playAgain.setBackground(Color.black);
		playAgain.setContentAreaFilled(false);
		playAgain.setFocusable(false);
		playAgain.setVisible(false);
		playButton.setForeground(Color.white);
		playButton.setBackground(Color.black);
		playButton.setContentAreaFilled(false);
		playButton.setFocusable(false);
		playButton.setVisible(true);
		switchGameMode.setForeground(Color.white);
		switchGameMode.setBackground(Color.black);
		switchGameMode.setContentAreaFilled(false);
		switchGameMode.setFocusable(false);
		turn.setBounds(50, 24, 120, 40);
		turn.setForeground(Color.white);
		turn.setFont(new Font("Garamond", Font.PLAIN, 34));
		turn.setVisible(false);
		title.setBounds(215, 30, 300, 28);
		title.setForeground(Color.white);
		title.setFont(font);
		gamePanel.setBackground(Color.black);
		gamePanel.setSize(600, 600);
		gamePanel.setLocation(10, 90);
		gamePanel.setVisible(true);

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				g[i][j] = new CustomButton();
				gamePanel.add(g[i][j]);
			}
		}

		playAgain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (playAgain.isVisible()) {
					File f = new File("C:\\Users\\henry\\eclipse-workspace\\TicTacToe\\src\\Computer_Out.txt");
					PrintStream printer;
					try {
						printer = new PrintStream(f);
						printer.print("");
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
					gamePanel.removeAll();
					for (int i = 0; i < 3; i++) {
						for (int j = 0; j < 3; j++) {
							g[i][j] = new CustomButton();
							gamePanel.add(g[i][j]);
						}
					}
					gamePanel.revalidate();
					play = true;
					playAgain.setVisible(false);
					turn.setVisible(true);
					playGameError.setVisible(false);
					modeChangeError.setVisible(false);
					xTurn = true;
					turn.setText("X's Turn");
					synchronized (repeat) {
						repeat.notify();
					}
				}
			}
		});

		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (playButton.isVisible()) {
					playButton.setVisible(false);
					play = true;
					turn.setVisible(true);
					playGameError.setVisible(false);
				}
			}
		});

		switchGameMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 1 = Two Player | 2 = Single Player | 3 = Random Turn
				
				if (modeHelper == 1 && !play) {
					modeHelper = 2;
					singlePlayer = false;
					randomTurnTwoPlayer = false;
					modeText.setBounds(250, 66, 200, 20);
					modeText.setText("Mode: Two Player");
					modeChangeError.setVisible(false);
				} else if (modeHelper == 2 && !play) {
					modeHelper = 3;
					singlePlayer = true;
					randomTurnTwoPlayer = false;
					modeText.setBounds(246, 66, 200, 20);
					modeText.setText("Mode: Single Player");
					modeChangeError.setVisible(false);
				} else if (modeHelper == 3 && !play) {
					modeHelper = 1;
					singlePlayer = false;
					randomTurnTwoPlayer = true;
					modeText.setBounds(200, 66, 460, 20);
					modeText.setText("Mode: Two Player Random Turn");
					modeChangeError.setVisible(false);
				} else {
					modeChangeError.setVisible(true);
				}
				
			}
				
		});

		this.add(title);
		this.add(gamePanel);
		this.add(turn);
		this.add(switchGameMode);
		this.add(playAgain);
		this.add(modeText);
		this.add(modeChangeError);
		this.add(playButton);
		this.add(playGameError);

		this.setResizable(false);
		this.revalidate();
	}

	public static void main(String[] args) {
		TicTacToe game = new TicTacToe();
		game.revalidate();
		boolean run = true;
		
		while (run) {
			while (!play) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			while(play && randomTurnTwoPlayer) {
				synchronized (syncObject) {
					try {
						syncObject.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				int randomNum = game.randomGenerator.nextInt(2);
				
				if (randomNum == 0) {
					turn.setText("X's Turn");
				} else if (randomNum == 1) {
					turn.setText("O's Turn");
				} else {
					System.out.println("Error");
				}
				
				if (checkIfXWin(game.g)) {
					play = false;
					turn.setVisible(false);
					int[] f = createLine(game.g);
			
					game.g[f[0]][f[1]].setWinBorder(6);
					game.g[f[2]][f[3]].setWinBorder(6);
					game.g[f[4]][f[5]].setWinBorder(6);
					
					game.playAgain.setVisible(true);
				} else if (checkIfOWin(game.g)) {
					play = false;
					turn.setVisible(false);
					int[] f = createLine(game.g);
			
					game.g[f[0]][f[1]].setWinBorder(6);
					game.g[f[2]][f[3]].setWinBorder(6);
					game.g[f[4]][f[5]].setWinBorder(6);
					
					game.playAgain.setVisible(true);
				} else if (checkCatFight(game.g)) {
					play = false;
					turn.setVisible(false);
					game.playAgain.setVisible(true);
					for (int i = 0; i < 3; i++) {
						for (int j = 0; j < 3; j++) {
							game.g[i][j].setWinBorder(2);
						}
					}
				} else {
					;
				}
				
			}

			while (play && singlePlayer) {
				synchronized (syncObject) {
					try {
						syncObject.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				turn.setText("O's Turn");
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				int[] a = new int[2];
				try {
					a = game.computerMove(game.g);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
//				game.g[a[0]][a[1]].computerGoHere();
//				game.g[a[0]][a[1]].setEmpty(false);
				
				boolean validMove = false;
				int counter = 0;
				while (!validMove) {
					counter++;
					if (game.g[a[0]][a[1]].getText().equals("X") || game.g[a[0]][a[1]].getText().equals("O")) {
						validMove = false;
					} else {
						validMove = true;
						game.g[a[0]][a[1]].computerGoHere();
						game.g[a[0]][a[1]].setEmpty(false);
					}
					if (counter > 10) {
						validMove = true;
						int[] f = new int[2];
						f = game.chooseRandom(game.g);
						game.g[f[0]][f[1]].computerGoHere();
					}
				}

				
				turn.setText("X's Turn");
				
				if (checkIfXWin(game.g)) {
					play = false;
					turn.setVisible(false);
					int[] f = createLine(game.g);
			
					game.g[f[0]][f[1]].setWinBorder(6);
					game.g[f[2]][f[3]].setWinBorder(6);
					game.g[f[4]][f[5]].setWinBorder(6);
					
					game.playAgain.setVisible(true);
				} else if (checkIfOWin(game.g)) {
					play = false;
					turn.setVisible(false);
					int[] f = createLine(game.g);
			
					game.g[f[0]][f[1]].setWinBorder(6);
					game.g[f[2]][f[3]].setWinBorder(6);
					game.g[f[4]][f[5]].setWinBorder(6);
					
					game.playAgain.setVisible(true);
				} else if (checkCatFight(game.g)) {
					play = false;
					turn.setVisible(false);
					game.playAgain.setVisible(true);
					for (int i = 0; i < 3; i++) {
						for (int j = 0; j < 3; j++) {
							game.g[i][j].setWinBorder(2);
						}
					}
				} else {
					;
				}

			}
			
			
			while (play && !singlePlayer) {
				synchronized (syncObject) {
					try {
						syncObject.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				if (checkIfXWin(game.g)) {
					play = false;
					turn.setVisible(false);
					int[] f = createLine(game.g);
			
					game.g[f[0]][f[1]].setWinBorder(6);
					game.g[f[2]][f[3]].setWinBorder(6);
					game.g[f[4]][f[5]].setWinBorder(6);
					
					game.playAgain.setVisible(true);
				} else if (checkIfOWin(game.g)) {
					play = false;
					turn.setVisible(false);
					int[] f = createLine(game.g);
			
					game.g[f[0]][f[1]].setWinBorder(6);
					game.g[f[2]][f[3]].setWinBorder(6);
					game.g[f[4]][f[5]].setWinBorder(6);
					
					game.playAgain.setVisible(true);
				} else if (checkCatFight(game.g)) {
					play = false;
					turn.setVisible(false);
					game.playAgain.setVisible(true);
					for (int i = 0; i < 3; i++) {
						for (int j = 0; j < 3; j++) {
							game.g[i][j].setWinBorder(2);
						}
					}
				} else {
					;
				}

			}
			
			synchronized (repeat) {
				try {
					repeat.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private static int[] createLine(CustomButton g[][]) {
		String r = "";
		int[] returnValues = new int[6];

		// X Column 1 down
		if (g[0][0].getText().equals("X") && g[1][0].getText().equals("X") && g[2][0].getText().equals("X")) {
			r = "0 0 1 0 2 0";
		}
		// X Column 2 down
		else if (g[0][1].getText().equals("X") && g[1][1].getText().equals("X") && g[2][1].getText().equals("X")) {
			r = "0 1 1 1 2 1";
		}
		// X Column 3 down
		else if (g[0][2].getText().equals("X") && g[1][2].getText().equals("X") && g[2][2].getText().equals("X")) {
			r = "0 2 1 2 2 2";
		}
		// X row 1 across
		else if (g[0][0].getText().equals("X") && g[0][1].getText().equals("X") && g[0][2].getText().equals("X")) {
			r = "0 0 0 1 0 2";
		}
		// X row 2 across
		else if (g[1][0].getText().equals("X") && g[1][1].getText().equals("X") && g[1][2].getText().equals("X")) {
			r = "1 0 1 1 1 2";
		}
		// X row 3 across
		else if (g[2][0].getText().equals("X") && g[2][1].getText().equals("X") && g[2][2].getText().equals("X")) {
			r = "2 0 2 1 2 2";
		}
		// X UL to LR Diagonal
		else if (g[0][0].getText().equals("X") && g[1][1].getText().equals("X") && g[2][2].getText().equals("X")) {
			r = "0 0 1 1 2 2";
		}
		// X LL to UR Diagonal
		else if (g[2][0].getText().equals("X") && g[1][1].getText().equals("X") && g[0][2].getText().equals("X")) {
			r = "2 0 1 1 0 2";
		}
		// O Column 1 down
		else if (g[0][0].getText().equals("O") && g[1][0].getText().equals("O") && g[2][0].getText().equals("O")) {
			r = "0 0 1 0 2 0";
		}
		// O Column 2 down
		else if (g[0][1].getText().equals("O") && g[1][1].getText().equals("O") && g[2][1].getText().equals("O")) {
			r = "0 1 1 1 2 1";
		}
		// O Column 3 down
		else if (g[0][2].getText().equals("O") && g[1][2].getText().equals("O") && g[2][2].getText().equals("O")) {
			r = "0 2 1 2 2 2";
		}
		// O row 1 across
		else if (g[0][0].getText().equals("O") && g[0][1].getText().equals("O") && g[0][2].getText().equals("O")) {
			r = "0 0 0 1 0 2";
		}
		// O row 2 across
		else if (g[1][0].getText().equals("O") && g[1][1].getText().equals("O") && g[1][2].getText().equals("O")) {
			r = "1 0 1 1 1 2";
		}
		// O row 3 across
		else if (g[2][0].getText().equals("O") && g[2][1].getText().equals("O") && g[2][2].getText().equals("O")) {
			r = "2 0 2 1 2 2";
		}
		// O UL to LR Diagonal
		else if (g[0][0].getText().equals("O") && g[1][1].getText().equals("O") && g[2][2].getText().equals("O")) {
			r = "0 0 1 1 2 2";
		}
		// O LL to UR Diagonal
		else if (g[2][0].getText().equals("O") && g[1][1].getText().equals("O") && g[0][2].getText().equals("O")) {
			r = "2 0 1 1 0 2";
		} else {
			;
		}

		String[] v = r.split(" ");
		for (int i = 0; i < 6; i++) {
			returnValues[i] = Integer.parseInt(v[i]);
		}

		return returnValues;

	}

	private int[] computerMove(CustomButton[][] h) throws FileNotFoundException {
		File in = new File("C:\\Users\\henry\\eclipse-workspace\\TicTacToe\\src\\Must_Go_Here_Scenarios.txt");
		File out = new File("C:\\Users\\henry\\eclipse-workspace\\TicTacToe\\src\\Computer_Out.txt");
		Scanner scan = new Scanner(in);
		PrintStream printer = new PrintStream(out);
		Scanner computerScanner = new Scanner(out);
		boolean takenCareOf = false;
		boolean hasMove = false;
		int[] returnValues = new int[2];
		while (scan.hasNextLine()) {
			takenCareOf = false;
			String[] numbers = scan.nextLine().split(" ");
			int[] numbs = new int[numbers.length];
			for (int i = 0; i < numbers.length; i++) {
				numbs[i] = Integer.valueOf(numbers[i]);
			}
			if (h[numbs[0]][numbs[1]].getText().equals("X") && h[numbs[2]][numbs[3]].getText().equals("X")) {
				if (h[numbs[4]][numbs[5]].getText().equals("O")) {
					printer.println(numbs[0] + " " + numbs[1] + " " + numbs[2] + " " + numbs[3] + " " + numbs[4] + " "
							+ numbs[5]);
				}
				while (computerScanner.hasNextLine()) {
					String[] numbers2 = computerScanner.nextLine().split(" ");
					int[] numbs2 = new int[numbers2.length];
					for (int i = 0; i < numbers.length; i++) {
						numbs2[i] = Integer.parseInt(numbers2[i]);
					}
					if (Arrays.equals(numbs2, numbs)) {
						takenCareOf = true;
					} else {
						takenCareOf = false;
					}

				}
				if (!takenCareOf) {
					returnValues[0] = numbs[4];
					returnValues[1] = numbs[5];
					hasMove = true;
					printer.println(numbs[0] + " " + numbs[1] + " " + numbs[2] + " " + numbs[3] + " " + numbs[4] + " "
							+ numbs[5]);
				}

			}
		}

		if (!hasMove) {
			if (h[1][1].isEmpty) {
				returnValues[0] = 1;
				returnValues[1] = 1;
			} else {
				if (oHasTwo(h)) {
					int [] s = oHasTwoCoordinates(h);
					returnValues[0] = s[0];
					returnValues[1] = s[1];
				}
				int[] f = new int[2];
				f = chooseRandom(h);
				returnValues[0] = f[0];
				returnValues[1] = f[1];
			}
		}

		scan.close();
		computerScanner.close();
		printer.close();
		return returnValues;
	}

	private int[] oHasTwoCoordinates(CustomButton[][] h) throws FileNotFoundException {
		int[] c = new int[2];
		File in = new File("C:\\Users\\henry\\eclipse-workspace\\TicTacToe\\src\\Must_Go_Here_Scenarios.txt");
		Scanner scan = new Scanner(in);
		while (scan.hasNextLine()) {
			String[] numbers = scan.nextLine().split(" ");
			int[] numbs = new int[numbers.length];
			for (int i = 0; i < numbers.length; i++) {
				numbs[i] = Integer.valueOf(numbers[i]);
			}
			if (h[numbs[0]][numbs[1]].getText().equals("O") && h[numbs[2]][numbs[3]].getText().equals("O") && !h[numbs[4]][numbs[5]].getText().equals("X")) {
				c[0] = numbs[4];
				c[1] = numbs[5];
			} else {
				;
			}
		}
		
		scan.close();
		return c;
	}
	
	private boolean oHasTwo(CustomButton h[][]) throws FileNotFoundException {
		boolean b = false;
		File in = new File("C:\\Users\\henry\\eclipse-workspace\\TicTacToe\\src\\Must_Go_Here_Scenarios.txt");
		Scanner scan = new Scanner(in);
		while (scan.hasNextLine() && !b) {
			String[] numbers = scan.nextLine().split(" ");
			int[] numbs = new int[numbers.length];
			for (int i = 0; i < numbers.length; i++) {
				numbs[i] = Integer.valueOf(numbers[i]);
			}
			if (h[numbs[0]][numbs[1]].getText().equals("O") && h[numbs[2]][numbs[3]].getText().equals("O") && !h[numbs[4]][numbs[5]].getText().equals("X")) {
				b = true;
			} else {
				b = false;
			}
		}
		
		scan.close();
		return b;
	}

	private int[] chooseRandom(CustomButton[][] z) {
		int[] r = new int[2];
		Random rand = new Random();
		boolean hasMove = false;
		while (!hasMove) {
			int randNum;
			randNum = rand.nextInt(9) + 1;
			switch (randNum) {
			case 1:
				r[0] = 0;
				r[1] = 0;
				break;
			case 2:
				r[0] = 1;
				r[1] = 0;
				break;
			case 3:
				r[0] = 2;
				r[1] = 0;
				break;
			case 4:
				r[0] = 0;
				r[1] = 1;
				break;
			case 5:
				r[0] = 1;
				r[1] = 1;
				break;
			case 6:
				r[0] = 2;
				r[1] = 1;
				break;
			case 7:
				r[0] = 0;
				r[1] = 2;
				break;
			case 8:
				r[0] = 1;
				r[1] = 2;
				break;
			case 9:
				r[0] = 2;
				r[1] = 2;
				break;
			}

			if (z[r[0]][r[1]].isEmpty) {
				hasMove = true;
			} else {
				hasMove = false;
			}
		}
		return r;
	}

	public static void changeTurnText(char c) {
		turn.setText(c + "'s Turn");
	}

	public static boolean checkIfXWin(CustomButton g[][]) {
		boolean xWon = false;

		// X Column 1 down
		if (g[0][0].getText().equals("X") && g[1][0].getText().equals("X") && g[2][0].getText().equals("X")) {
			xWon = true;
		}
		// X Column 2 down
		else if (g[0][1].getText().equals("X") && g[1][1].getText().equals("X") && g[2][1].getText().equals("X")) {
			xWon = true;
		}
		// X Column 3 down
		else if (g[0][2].getText().equals("X") && g[1][2].getText().equals("X") && g[2][2].getText().equals("X")) {
			xWon = true;
		}
		// X row 1 across
		else if (g[0][0].getText().equals("X") && g[0][1].getText().equals("X") && g[0][2].getText().equals("X")) {
			xWon = true;
		}
		// X row 2 across
		else if (g[1][0].getText().equals("X") && g[1][1].getText().equals("X") && g[1][2].getText().equals("X")) {
			xWon = true;
		}
		// X row 3 across
		else if (g[2][0].getText().equals("X") && g[2][1].getText().equals("X") && g[2][2].getText().equals("X")) {
			xWon = true;
		}
		// X UL to LR Diagonal
		else if (g[0][0].getText().equals("X") && g[1][1].getText().equals("X") && g[2][2].getText().equals("X")) {
			xWon = true;
		}
		// X LL to UR Diagonal
		else if (g[2][0].getText().equals("X") && g[1][1].getText().equals("X") && g[0][2].getText().equals("X")) {
			xWon = true;
		} else {
			xWon = false;
		}
		return xWon;
	}

	public static boolean checkIfOWin(CustomButton g[][]) {
		boolean oWon = false;

		// O Column 1 down
		if (g[0][0].getText().equals("O") && g[1][0].getText().equals("O") && g[2][0].getText().equals("O")) {
			oWon = true;
		}
		// O Column 2 down
		else if (g[0][1].getText().equals("O") && g[1][1].getText().equals("O") && g[2][1].getText().equals("O")) {
			oWon = true;
		}
		// O Column 3 down
		else if (g[0][2].getText().equals("O") && g[1][2].getText().equals("O") && g[2][2].getText().equals("O")) {
			oWon = true;
		}
		// O row 1 across
		else if (g[0][0].getText().equals("O") && g[0][1].getText().equals("O") && g[0][2].getText().equals("O")) {
			oWon = true;
		}
		// O row 2 across
		else if (g[1][0].getText().equals("O") && g[1][1].getText().equals("O") && g[1][2].getText().equals("O")) {
			oWon = true;
		}
		// O row 3 across
		else if (g[2][0].getText().equals("O") && g[2][1].getText().equals("O") && g[2][2].getText().equals("O")) {
			oWon = true;
		}
		// O UL to LR Diagonal
		else if (g[0][0].getText().equals("O") && g[1][1].getText().equals("O") && g[2][2].getText().equals("O")) {
			oWon = true;
		}
		// O LL to UR Diagonal
		else if (g[2][0].getText().equals("O") && g[1][1].getText().equals("O") && g[0][2].getText().equals("O")) {
			oWon = true;
		} else {
			oWon = false;
		}
		return oWon;
	}

	public static boolean checkCatFight(CustomButton q[][]) {
		boolean catFight = false;
		int n = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (q[i][j].getText().equals("X") || q[i][j].getText().equals("O")) {
					n++;
				}
			}
		}

		if (n == 9) {
			catFight = true;
		} else {
			catFight = false;
		}
		return catFight;
	}
}
