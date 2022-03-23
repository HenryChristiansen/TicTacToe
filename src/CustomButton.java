import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class CustomButton extends JButton {
	Font font;
	String status;
	boolean isEmpty;

	CustomButton() {
		isEmpty = true;
		setVisible(true);
		font = new Font("Garamond", Font.PLAIN, 100);
		this.setFont(font);
		this.setBorder(BorderFactory.createLineBorder(Color.white, 2));
		this.setBackground(Color.black);
		this.setForeground(Color.white);
		this.setContentAreaFilled(false);
		this.setFocusable(false);
		setText(" ");

		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!TicTacToe.play) {
					TicTacToe.playGameError.setVisible(true);
				}
				if (isEmpty && !TicTacToe.singlePlayer && TicTacToe.play && !TicTacToe.randomTurnTwoPlayer) {
					if (TicTacToe.xTurn) {
						isEmpty = false;
						setText("X");
						TicTacToe.xTurn = false;
						synchronized (TicTacToe.syncObject) {
							TicTacToe.syncObject.notify();
						}
						TicTacToe.changeTurnText('O');
					} else {
						isEmpty = false;
						setText("O");
						TicTacToe.xTurn = true;
						synchronized (TicTacToe.syncObject) {
							TicTacToe.syncObject.notify();
						}
						TicTacToe.changeTurnText('X');
					}
				}

				if (!TicTacToe.randomTurnTwoPlayer && isEmpty && TicTacToe.singlePlayer && TicTacToe.turn.getText().equals("X's Turn")
						&& TicTacToe.play) {
					setText("X");
					isEmpty = false;
					synchronized (TicTacToe.syncObject) {
						TicTacToe.syncObject.notify();
					}
				}
				
				if (isEmpty && TicTacToe.randomTurnTwoPlayer && TicTacToe.play) {
					isEmpty = false;
					if (TicTacToe.turn.getText().equals("X's Turn")) {
						setText("X");
					} else {
						setText("O");
					}
					synchronized (TicTacToe.syncObject) {
						TicTacToe.syncObject.notify();
					}
				}

			}
		});
	}

	public void computerGoHere() {
		this.setText("O");
	}
	
	public void setEmpty(boolean b) {
		this.isEmpty = b;
	}

	public void setWinBorder(int size) {
		this.setBorder(BorderFactory.createLineBorder(Color.red, size));
	}

}
