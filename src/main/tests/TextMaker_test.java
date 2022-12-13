package main.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import main.display.TextMaker;

class TextMaker_test {
	@Test
	void makeText_test() {
		double unknownDivider = 7.0; //Not sure what it does precisely
    	double fontScale = 3;
    	double xScale = 1.2;
    	double yScale = 2;

        double fontSize = (fontScale * 100) / unknownDivider;
        Color whiteColor = Color.WHITE;
        double xCellpos = 0 + xScale * 100 / unknownDivider;
        double yCellpos = 0 + yScale * 100 / unknownDivider;

        Text text = new Text("#*#Lorem_ipSUUUM123");
        text.setFont(Font.font(fontSize));
        text.relocate(xCellpos, yCellpos);
        text.setFill(whiteColor);

        Text testtext = TextMaker.formatWhiteText("#*#Lorem_ipSUUUM123", 0, 0, 100);

		assertEquals(testtext.getText(), text.getText());
		assertEquals(testtext.getFont(), text.getFont());
		assertEquals(testtext.getX(), text.getX());
		assertEquals(testtext.getY(), text.getY());
		assertEquals(testtext.getFill(), text.getFill());
	}

	@Test
	void swapPos_test() {
		Text text1 = new Text("Lorem");
		Text text2 = new Text("Ipsum");

		text1.setX(100);
		text2.setX(200);

		text1.setY(90);
		text2.setY(300);

		TextMaker.SwapPos(text1, text2);

		assertEquals(text1.getX(),200);
		assertEquals(text2.getX(),100);
		assertEquals(text1.getY(),300);
		assertEquals(text2.getY(),90);

		//maintain text
		assertEquals(text1.getText(), "Lorem");
		assertEquals(text2.getText(), "Ipsum");
	}

	@Test
	void swapText_test() {
		Text text1 = new Text("Lorem");
		Text text2 = new Text("Ipsum");

		text1.setX(100);
		text2.setX(200);

		text1.setY(90);
		text2.setY(300);

		TextMaker.SwapText(text1, text2);

		//maintain position
		assertEquals(text1.getX(),100);
		assertEquals(text2.getX(),200);
		assertEquals(text1.getY(),90);
		assertEquals(text2.getY(),300);

		assertEquals(text1.getText(), "Ipsum");
		assertEquals(text2.getText(), "Lorem");
	}
}
