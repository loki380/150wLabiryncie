package Grafika;

import Algorytmy.Punkt;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Odpowiedz extends JFrame {
    private JButton tab[][] = new JButton[6][6] ;
    // Tworzymy nowe okienko
    Odpowiedz(int[][] dane, List <Punkt> sciezka) {

        setSize(494, 524);
        setTitle("Prawidlowe rozwiazanie");
        setLocation(1010, 0);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(new Color(0, 0, 0));

        wypelnijPlansze(dane, sciezka);
    }
    // Wypelniamy plansze prawidlowym rozwiazaniem
    public void wypelnijPlansze(int dane[][], List <Punkt> sciezka){
        int x, y;
        for (int i=0;i<6;i++) {
            x = i*80+i*2+2;
            for (int j = 0; j < 6; j++) {
                y=j*80+j*2+2;
                tab[i][j] = new JButton(Integer.toString(dane[i][j]));
                tab[i][j].setBounds(x, y, 80, 80);
                tab[i][j].setFont(new Font("Arial", Font.PLAIN, 20));
                if ((i==0 && j==0) || (i==5 && j==5)) (tab[i][j]).setBackground(new Color(0,153,0));
                else {
                    int flaga=1;
                    for(Punkt aktualny : sciezka){
                        if(aktualny.getX()==i && aktualny.getY()==j) flaga=0;
                    }
                    if(flaga==0){
                        tab[i][j].setBackground(new Color(0,153,0));
                    }else
                        tab[i][j].setBackground(Color.white);
                }
                add(tab[i][j]);
            }
        }
    }
}
