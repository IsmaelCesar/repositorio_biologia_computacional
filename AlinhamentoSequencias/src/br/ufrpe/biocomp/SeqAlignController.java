package br.ufrpe.biocomp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class SeqAlignController implements Initializable {

	@FXML
	private TextField seq1;

	@FXML
	private TextField seq2;

	@FXML
	private RadioButton global;

	@FXML
	private RadioButton semiglobal, linha, coluna;

	@FXML
	private TextField match;

	@FXML
	private TextField missmatch;

	@FXML
	private TextField gap;

	@FXML
	private Label seq1Res, seq2Res;

	@FXML
	private GridPane grid;

    ImageView arrows[][];
    VBox box[][];

	private Cell[][] globalAlign(String seq1, String seq2){
		int gap = Integer.parseInt(this.gap.getText());
		int missmatch = Integer.parseInt(this.missmatch.getText());
		int match = Integer.parseInt(this.match.getText());

		Cell[][] res = new Cell[seq1.length()+1][seq2.length()+1];

		for(int i = 0; i < res.length;i++){
			for(int j = 0; j < res[i].length;j++){
				res[i][j] = new Cell();
				res[i][j].i = i;
				res[i][j].j = j;
			}
		}

		for(int i = 1; i < res.length;i++){
			res[i][0].value = res[i-1][0].value+gap;
			res[i][0].gaptop = res[i-1][0];
			arrows[i][0] = new ImageView(new Image(getClass().getResourceAsStream("toparrow.png")));
            arrows[i][0].fitWidthProperty().set(15);
            arrows[i][0].fitHeightProperty().set(15);
		}
		for(int i = 1; i < res[0].length;i++){
			res[0][i].value = res[0][i-1].value+gap;
			res[0][i].gapleft = res[0][i-1];
            arrows[0][i] = new ImageView(new Image(getClass().getResourceAsStream("leftarrow.png")));
            arrows[0][i].fitWidthProperty().set(15);
            arrows[0][i].fitHeightProperty().set(15);
		}


		for(int i = 1; i < res.length;i++){
			for(int j = 1; j < res[i].length;j++){
				if(seq1.charAt(i-1) == seq2.charAt(j-1)){
					int max  = Math.max(res[i-1][j].value + gap, Math.max(res[i][j-1].value + gap,res[i-1][j-1].value+match));
					res[i][j].value = max;
					if(res[i-1][j-1].value +match == max){
						res[i][j].matchmiss = res[i-1][j-1];
						arrows[i][j] = new ImageView(new Image(getClass().getResourceAsStream("topleftarrow.png")));
                        arrows[i][j].fitWidthProperty().set(15);
                        arrows[i][j].fitHeightProperty().set(15);
					}
					if(res[i-1][j].value + gap == max){
						res[i][j].gaptop = res[i-1][j];
                        new ImageView(new Image(getClass().getResourceAsStream("toparrow.png")));
                        arrows[i][j].fitWidthProperty().set(15);
                        arrows[i][j].fitHeightProperty().set(15);
					}
					if(res[i][j-1].value + gap == max){
						res[i][j].gapleft =res[i][j-1];
                        new ImageView(new Image(getClass().getResourceAsStream("leftarrow.png")));
                        arrows[i][j].fitWidthProperty().set(15);
                        arrows[i][j].fitHeightProperty().set(15);
					}
				}else{
					int max  = Math.max(res[i-1][j].value + gap, Math.max(res[i][j-1].value + gap,res[i-1][j-1].value+missmatch));
					res[i][j].value = max;
					if(res[i-1][j-1].value +missmatch == max){
						res[i][j].matchmiss = res[i-1][j-1];
                        arrows[i][j] = new ImageView(new Image(getClass().getResourceAsStream("topleftarrow.png")));
                        arrows[i][j].fitWidthProperty().set(15);
                        arrows[i][j].fitHeightProperty().set(15);
					}
					if(res[i-1][j].value + gap == max){
						res[i][j].gaptop = res[i-1][j];
                        arrows[i][j] = new ImageView(new Image(getClass().getResourceAsStream("toparrow.png")));
                        arrows[i][j].fitWidthProperty().set(15);
                        arrows[i][j].fitHeightProperty().set(15);
					}
					if(res[i][j-1].value + gap == max){
						res[i][j].gapleft =res[i][j-1];
						arrows[i][j] = new ImageView(new Image(getClass().getResourceAsStream("leftarrow.png")));
                        arrows[i][j].fitWidthProperty().set(15);
                        arrows[i][j].fitHeightProperty().set(15);
					}
				}
			}
		}
		return res;
	}

	private Cell[][] semiglobalAlign(String seq1, String seq2){
		int gap = Integer.parseInt(this.gap.getText());
		int missmatch = Integer.parseInt(this.missmatch.getText());
		int match = Integer.parseInt(this.match.getText());

		Cell[][] res = new Cell[seq1.length()+1][seq2.length()+1];

		for(int i = 0; i < res.length;i++){
			for(int j = 0; j < res[i].length;j++){
				res[i][j] = new Cell();
				res[i][j].i = i;
				res[i][j].j = j;
			}
		}
		for(int i = 1;i < res.length;i++) {
			res[i][0].gaptop = res[i-1][0];
			arrows[i][0] = new ImageView(new Image(getClass().getResourceAsStream("toparrow.png")));
			arrows[i][0].fitWidthProperty().set(15);
			arrows[i][0].fitHeightProperty().set(15);
		}
		for(int i = 1;i < res[0].length;i++) {
			res[0][i].gapleft = res[0][i-1];
			arrows[0][i] = new ImageView(new Image(getClass().getResourceAsStream("leftarrow.png")));
			arrows[0][i].fitWidthProperty().set(15);
			arrows[0][i].fitHeightProperty().set(15);
		}

		for(int i = 1; i < res.length;i++){
			for(int j = 1; j < res[i].length;j++){
				if(seq1.charAt(i-1) == seq2.charAt(j-1)){

					int max  = Math.max(res[i-1][j].value + gap, Math.max(res[i][j-1].value + gap,res[i-1][j-1].value+match));
					res[i][j].value = max;
					if(res[i-1][j-1].value +match == max){
						res[i][j].matchmiss = res[i-1][j-1];
						arrows[i][j] = new ImageView(new Image(getClass().getResourceAsStream("topleftarrow.png")));
						arrows[i][j].fitWidthProperty().set(15);
						arrows[i][j].fitHeightProperty().set(15);
					}
					if(res[i-1][j].value + gap == max){
						res[i][j].gaptop = res[i-1][j];
						arrows[i][j] = new ImageView(new Image(getClass().getResourceAsStream("toparrow.png")));
						arrows[i][j].fitWidthProperty().set(15);
						arrows[i][j].fitHeightProperty().set(15);
					}
					if(res[i][j-1].value + gap == max){
						res[i][j].gapleft =res[i][j-1];
						arrows[i][j] = new ImageView(new Image(getClass().getResourceAsStream("leftarrow.png")));
						arrows[i][j].fitWidthProperty().set(15);
						arrows[i][j].fitHeightProperty().set(15);
					}
				}else{
					int max  = Math.max(res[i-1][j].value + gap, Math.max(res[i][j-1].value + gap,res[i-1][j-1].value+missmatch));
					res[i][j].value = max;
					if(res[i-1][j-1].value +missmatch == max){
						res[i][j].matchmiss = res[i-1][j-1];
						arrows[i][j] = new ImageView(new Image(getClass().getResourceAsStream("topleftarrow.png")));
						arrows[i][j].fitWidthProperty().set(15);
						arrows[i][j].fitHeightProperty().set(15);
					}
					if(res[i-1][j].value + gap == max){
						res[i][j].gaptop = res[i-1][j];
						arrows[i][j] = new ImageView(new Image(getClass().getResourceAsStream("toparrow.png")));
						arrows[i][j].fitWidthProperty().set(15);
						arrows[i][j].fitHeightProperty().set(15);
					}
					if(res[i][j-1].value + gap == max){
						res[i][j].gapleft =res[i][j-1];
						arrows[i][j] = new ImageView(new Image(getClass().getResourceAsStream("leftarrow.png")));
						arrows[i][j].fitWidthProperty().set(15);
						arrows[i][j].fitHeightProperty().set(15);
					}
				}
			}
		}
		return res;

	}

	@FXML
	void iniciar(ActionEvent event) {
        grid.getChildren().clear();
	    if(seq1.getText().length() > 14 && seq2.getText().length() > 14) {
		    return;
        }
		if(global.isSelected()) {
            arrows = new ImageView[seq1.getText().length()+1][seq2.getText().length()+1];
			Cell[][] res = globalAlign(seq1.getText(), seq2.getText());
			Label[][] aux = new Label[res.length][res[0].length];
			box = new VBox[res.length][res[0].length];
			for(int i = 0; i < res.length;i++){
				Label l[] = new Label[res[i].length];
				for(int j = 0; j < res[i].length;j++) {
					l[j] = new Label(res[i][j].value + "");
					l[j].setMinWidth(15);
                    l[j].setMinHeight(15);
				}
				aux[i] = l;
			}

			Label aux_l = new Label("");
			grid.addColumn(0, aux_l);

			Label[] l = new Label[seq1.getText().length()+1];
			l[0] = new Label("&");
			for(int i = 1; i < l.length;i++) {
				l[i] = new Label(seq1.getText().charAt(i-1)+"");
			}

			grid.addColumn(0, l);

			l = new Label[seq2.getText().length()+1];
			l[0] = new Label("&");
			for(int i = 1; i < l.length;i++) {
				l[i] = new Label(seq2.getText().charAt(i-1)+"");
			}
			grid.addRow(0, l);
			rollback(res,aux, seq1.getText(), seq2.getText());
			arrows[0][0] = new ImageView();
			arrows[0][0].setFitWidth(15);
			arrows[0][0].setFitHeight(15);
			for (int i = 0 ; i < res.length;i++){
			    for(int j = 0; j < res[i].length;j++){
			        if(!(i == 0 && j == 0)){
			            box[i][j] = new VBox();
                        box[i][j].getChildren().add(arrows[i][j]);
			            box[i][j].getChildren().add(aux[i][j]);
                    }else{
                        box[i][j] = new VBox();
                        box[i][j].getChildren().add(arrows[i][j]);
                        box[i][j].getChildren().add(aux[i][j]);
                    }
                }
            }
			for(int i = 1; i <= res.length;i++){
				grid.addRow(i, box[i-1]);
			}
		}else if(semiglobal.isSelected()) {
            arrows = new ImageView[seq1.getText().length()+1][seq2.getText().length()+1];
			Cell[][] res = semiglobalAlign(seq1.getText(),seq2.getText());
			Label[][] aux = new Label[res.length][res[0].length];
            box = new VBox[res.length][res[0].length];
			for(int i = 0; i < res.length;i++){
				Label l[] = new Label[res[i].length];
				for(int j = 0; j < res[i].length;j++) {
					l[j] = new Label(res[i][j].value + "");
				}
				aux[i] = l;
			}
			Label aux_l = new Label("");
			grid.addColumn(0, aux_l);
			Label[] l = new Label[seq1.getText().length()+1];
			l[0] = new Label("&");
			for(int i = 1; i < l.length;i++) {
				l[i] = new Label(seq1.getText().charAt(i-1)+"");
			}
			grid.addColumn(0, l);
			l = new Label[seq2.getText().length()+1];
			l[0] = new Label("&");
			for(int i = 1; i < l.length;i++) {
				l[i] = new Label(seq2.getText().charAt(i-1)+"");
			}
			grid.addRow(0, l);

			if(linha.isSelected()) {
				Cell[] linha = res[res.length-1];
				int max = linha[0].value, maxi = 0;
				for(int i = 1; i < linha.length;i++) {
					if(linha[i].value >= max) {
						max = linha[i].value;
						maxi = i;
					}
				}
				rollback_semi(res,aux,res.length-1, maxi, seq1.getText(), seq2.getText());
                arrows[0][0] = new ImageView();
                arrows[0][0].setFitWidth(15);
                arrows[0][0].setFitHeight(15);
                for (int i = 0 ; i < res.length;i++){
                    for(int j = 0; j < res[i].length;j++){
                        if(!(i == 0 && j == 0)){
                            box[i][j] = new VBox();
                            box[i][j].getChildren().add(arrows[i][j]);
                            box[i][j].getChildren().add(aux[i][j]);
                        }else{
                            box[i][j] = new VBox();
                            box[i][j].getChildren().add(arrows[i][j]);
                            box[i][j].getChildren().add(aux[i][j]);
                        }
                    }
                }
                for(int i = 1; i <= res.length;i++){
                    grid.addRow(i, box[i-1]);
                }
			}else {
				int max = res[0][res[0].length-1].value, maxi = 0;
				for(int i = 1; i < res.length;i++) {
					if(res[i][res[i].length-1].value >= max) {
						max = res[i][res[i].length-1].value;
						maxi = i;
					}
				}
				rollback_semi(res,aux,maxi,res[0].length-1, seq1.getText(), seq2.getText());
                arrows[0][0] = new ImageView();
                arrows[0][0].setFitWidth(15);
                arrows[0][0].setFitHeight(15);
                for (int i = 0 ; i < res.length;i++){
                    for(int j = 0; j < res[i].length;j++){
                        if(!(i == 0 && j == 0)){
                            box[i][j] = new VBox();
                            box[i][j].getChildren().add(arrows[i][j]);
                            box[i][j].getChildren().add(aux[i][j]);
                        }else{
                            box[i][j] = new VBox();
                            box[i][j].getChildren().add(arrows[i][j]);
                            box[i][j].getChildren().add(aux[i][j]);
                        }
                    }
                }
                for(int i = 1; i <= res.length;i++){
                    grid.addRow(i, box[i-1]);
                }
			}
		}
	}


	private void rollback_semi(Cell[][] res, Label[][] aux, int x, int y, String seq1, String seq2) {

		String str_res_a = "";
		String str_res_b = "";
		if(seq1.length() > seq2.length()) {
			if(seq1.length()-1 > x) {
				for(int i = seq1.length()-1; i > x;i--) {
					str_res_a = "\t" + seq1.charAt(i) + str_res_a;
					str_res_b = "\t_" + str_res_b;
				}
			}else {
				for(int i = seq2.length()-1; i > y;i--) {
					str_res_b = "\t" + seq2.charAt(i) + str_res_b;
					str_res_a = "\t_" + str_res_a;
				}	
			}
		}else if (seq1.length() < seq2.length()){
			if(seq2.length()-1 > y) {
				for(int i = seq2.length()-1; i > y;i--) {
					str_res_b = "\t" + seq2.charAt(i) + str_res_b;
					str_res_a = "\t_" + str_res_a;
				}
			}else {
				for(int i = seq1.length()-1; i > x;i--) {
					str_res_a = "\t" + seq1.charAt(i) + str_res_a;
					str_res_b = "\t_" + str_res_b;
				}
			}
		}else{
		    if(seq1.length() > y){
                for(int i = seq2.length()-1; i >= y;i--) {
                    str_res_b = "\t" + seq2.charAt(i) + str_res_b;
                    str_res_a = "\t_" + str_res_a;
                }
            }else{
                for(int i = seq1.length()-1; i >= x;i--) {
                    str_res_a = "\t" + seq1.charAt(i) + str_res_a;
                    str_res_b = "\t_" + str_res_b;
                }
            }
        }
		if(!str_res_a.isEmpty()) {
			str_res_a = str_res_a.substring(1);
			str_res_b = str_res_b.substring(1);
		}
		Cell c_aux = res[x][y];
		if(c_aux.matchmiss != null) {
			if(seq1.charAt(c_aux.i-1) == seq2.charAt(c_aux.j-1))
				aux[c_aux.i][c_aux.j].setStyle("-fx-background-color: green; -fx-text-fill: white;-fx-font-size: 15");
			else
				aux[c_aux.i][c_aux.j].setStyle("-fx-background-color: yellow; -fx-font-size: 15");
		}else if(c_aux.matchmiss != null) {
			aux[c_aux.i][c_aux.j].setStyle("-fx-background-color: red; -fx-font-size: 15");
		}else if(c_aux.matchmiss != null) {
			aux[c_aux.i][c_aux.j].setStyle("-fx-background-color: red; -fx-font-size: 15");
		}
		int match = Integer.parseInt(this.match.getText());
		int missmatch = Integer.parseInt(this.missmatch.getText());
		int gap = Integer.parseInt(this.gap.getText());
		while(x > 0 || y > 0) {
			boolean aux1 = x > 0 && y > 0 && ((res[x - 1][y - 1].value + match) == res[x][y].value);
			boolean aux2 = x > 0 && y > 0 && (res[x - 1][y - 1].value + missmatch == res[x][y].value);
			boolean aux3 = x > 0 && (res[x - 1][y].value + gap == res[x][y].value);
			boolean aux4 = y > 0 && (res[x][y - 1].value + gap == res[x][y].value);

			if(aux1 || aux2) {
				str_res_a = seq1.charAt(x-1) +"\t"+ str_res_a;
				str_res_b = seq2.charAt(y-1) +"\t"+ str_res_b;
				aux[x][y].setStyle("-fx-background-color: green; -fx-text-fill: white;-fx-font-size: 15");
				x--;
				y--;
			}else if(aux3) {
				str_res_a = seq1.charAt(x-1) +"\t" +  str_res_a;
				str_res_b = "_\t" + str_res_b;
				aux[x][y].setStyle("-fx-background-color: red; -fx-text-fill: white;-fx-font-size: 15");
				x--;
			}else if(aux4) {
				str_res_a = "_\t" + str_res_a;
				str_res_b = seq2.charAt(y-1) +"\t"+ str_res_b;
				aux[x][y].setStyle("-fx-background-color: red; -fx-text-fill: white;-fx-font-size: 15");
				y--;
			}else {
				if(x == 0) {
					str_res_a = "_\t" + str_res_a;
					str_res_b = seq2.charAt(y-1) +"\t"+ str_res_b;
					aux[x][y].setStyle("-fx-background-color: red; -fx-text-fill: white;-fx-font-size: 15");
					y--;
				}else {
					str_res_a = seq1.charAt(x-1) +"\t"+ str_res_a;
					str_res_b = "_\t" + str_res_b;
					aux[x][y].setStyle("-fx-background-color: red; -fx-text-fill: white;-fx-font-size: 15");
					x--;
				}
			}
		}
		seq1Res.setText(str_res_a);
		seq2Res.setText(str_res_b);
	}

	private void rollback(Cell[][] res, Label[][] aux, String seq1, String seq2) {
		Cell c_aux = res[res.length-1][res[0].length-1];
		for(int i = 0; i < res.length;i++){
			for(int j = 0; j < res[0].length;j++){
				aux[i][j].setStyle("-fx-font-size: 15");
			}
		}
		String seq1Res = "";
		String seq2Res = "";
		if(c_aux.matchmiss != null) {
			aux[c_aux.i][c_aux.j].setStyle("-fx-background-color: green; -fx-text-fill: white;-fx-font-size: 15");
		}else if(c_aux.matchmiss != null) {
			aux[c_aux.i][c_aux.j].setStyle("-fx-background-color: red; -fx-font-size: 15");
		}else if(c_aux.matchmiss != null) {
			aux[c_aux.i][c_aux.j].setStyle("-fx-background-color: red; -fx-font-size: 15");
		}
		do{
			if(c_aux.matchmiss != null){
                if(c_aux.i != 0 && c_aux.j != 0)
                    arrows[c_aux.i][c_aux.j].setImage(new Image(getClass().getResourceAsStream("topleftarrow.png")));
				seq1Res = seq1.charAt(c_aux.i-1) +"\t"+ seq1Res;
				seq2Res = seq2.charAt(c_aux.j-1) +"\t"+ seq2Res;
				c_aux = c_aux.matchmiss;
				aux[c_aux.i][c_aux.j].setStyle("-fx-background-color: green; -fx-text-fill: white;-fx-font-size: 15");
			}else if(c_aux.gaptop != null){
                if(c_aux.i != 0 && c_aux.j != 0)
                    arrows[c_aux.i][c_aux.j].setImage(new Image(getClass().getResourceAsStream("toparrow.png")));
				seq1Res = seq1.charAt(c_aux.i-1) +"\t" +seq1Res;
				seq2Res = "_\t" + seq2Res;
				c_aux = c_aux.gaptop;
				aux[c_aux.i][c_aux.j].setStyle("-fx-background-color: red; -fx-font-size: 15");
			}else if(c_aux.gapleft != null){
                if(c_aux.i != 0 && c_aux.j != 0)
                    arrows[c_aux.i][c_aux.j].setImage(new Image(getClass().getResourceAsStream("leftarrow.png")));
				seq1Res = "_\t" + seq1Res;
				seq2Res = seq2.charAt(c_aux.j-1) + "\t"+seq2Res;
				c_aux = c_aux.gapleft;
				aux[c_aux.i][c_aux.j].setStyle("-fx-background-color: red; -fx-font-size: 15");

			}
		}while(c_aux.i > 0 || c_aux.j > 0);
		this.seq1Res.setText(seq1Res);
		this.seq2Res.setText(seq2Res);
	}

	@FXML
	void DesativarEscolherColuna(ActionEvent event) {
		linha.setDisable(true);
		coluna.setDisable(true);
	}

	@FXML
	void ativarEscolherColuna(ActionEvent event) {
		linha.setDisable(false);
		coluna.setDisable(false);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ToggleGroup tg = new ToggleGroup();
		global.setToggleGroup(tg);
		semiglobal.setToggleGroup(tg);
		global.setSelected(true);

		ToggleGroup tg2 = new ToggleGroup();
		linha.setToggleGroup(tg2);
		coluna.setToggleGroup(tg2);
		linha.setSelected(true);

		DesativarEscolherColuna(null);
	}
}
