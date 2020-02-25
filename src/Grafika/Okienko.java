package Grafika;

import Algorytmy.Liczby;
import Algorytmy.Punkt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Okienko extends JFrame implements ActionListener, MouseListener{
    private int licznik=0;
    private Punkt poprzedni = new Punkt(0,0);
    private JButton tab[][] = new JButton[6][6] ;
    private JButton bNowagra = new JButton("NOWA GRA");
    private JButton bCofnij = new JButton("COFNIJ");
    private JButton bZapisz = new JButton("ZAPISZ");
    private JButton bWczytaj = new JButton("WCZYTAJ");
    private JButton bOdpowiedz = new JButton("ODPOWIEDZ");
    private JButton bInstrukcja = new JButton("INSTRUKCJA");
    private JButton bWyjscie = new JButton("ZAKONCZ");
    private JPanel pPlansza = new JPanel();
    private int[][] dane = new int[6][6];
    private static List<Punkt> sciezka = new ArrayList<Punkt>();
    private static List<Punkt> ruchy = new ArrayList<Punkt>();
    // Okno glowne programu
    public Okienko(){
        setSize(800,524);
        setTitle("150 w Labiryncie");
        setResizable(false);
        setLayout(null);
        int szer = Toolkit.getDefaultToolkit().getScreenSize().width;
        int wys = Toolkit.getDefaultToolkit().getScreenSize().height;
        int szerRamki = getSize().width;
        int wysRamki = getSize().height;
        setLocation((szer-szerRamki)/2,(wys-wysRamki)/2);
        getContentPane().setBackground(new Color(255,0, 11));

        // PLANSZA

        pPlansza.setBounds(0, 0, 494, 494);
        pPlansza.setBackground(new Color(0, 0, 0));
        pPlansza.setLayout(null);
        add(pPlansza);

        wypelnijPlansze(false);
        sterowanie();
    }
    // Dodaje Interfejs
    public void sterowanie() {
        bNowagra.setBounds(547, 27, 200, 50);
        bNowagra.setBackground(Color.white);
        bNowagra.setFont(new Font("Arial", Font.PLAIN, 20));
        bNowagra.setForeground(Color.black);
        add(bNowagra);
        bNowagra.addActionListener(this);

        bCofnij.setBounds(547, 92, 200, 50);
        bCofnij.setBackground(Color.white);
        bCofnij.setFont(new Font("Arial", Font.PLAIN, 20));
        bCofnij.setForeground(Color.black);
        add(bCofnij);
        bCofnij.addActionListener(this);

        bZapisz.setBounds(547, 157, 200, 50);
        bZapisz.setBackground(Color.white);
        bZapisz.setFont(new Font("Arial", Font.PLAIN, 20));
        bZapisz.setForeground(Color.black);
        add(bZapisz);
        bZapisz.addActionListener(this);

        bWczytaj.setBounds(547, 222, 200, 50);
        bWczytaj.setBackground(Color.white);
        bWczytaj.setFont(new Font("Arial", Font.PLAIN, 20));
        bWczytaj.setForeground(Color.black);
        add(bWczytaj);
        bWczytaj.addActionListener(this);

        bOdpowiedz.setBounds(547, 287, 200, 50);
        bOdpowiedz.setBackground(Color.white);
        bOdpowiedz.setFont(new Font("Arial", Font.PLAIN, 20));
        bOdpowiedz.setForeground(Color.black);
        add(bOdpowiedz);
        bOdpowiedz.addActionListener(this);

        bInstrukcja.setBounds(547, 352, 200, 50);
        bInstrukcja.setBackground(Color.white);
        bInstrukcja.setFont(new Font("Arial", Font.PLAIN, 20));
        bInstrukcja.setForeground(Color.black);
        add(bInstrukcja);
        bInstrukcja.addActionListener(this);

        bWyjscie.setBounds(547, 417, 200, 50);
        bWyjscie.setBackground(Color.white);
        bWyjscie.setFont(new Font("Arial", Font.PLAIN, 20));
        bWyjscie.setForeground(Color.black);
        add(bWyjscie);
        bWyjscie.addActionListener(this);
    }
    // Generuje nowe liczby, dodaje buttony do planszy
    public void wypelnijPlansze(boolean wczytane) {
        if(wczytane==false) {
            poprzedni.setPoint(0, 0);
            Liczby generator = new Liczby();
            generator.generuj(dane, sciezka);
            ruchy.clear();
            licznik = 0;
            int x, y;
            for (int i = 0; i < 6; i++) {
                x = i * 80 + i * 2 + 2;
                for (int j = 0; j < 6; j++) {
                    y = j * 80 + j * 2 + 2;
                    tab[i][j] = new JButton(Integer.toString(dane[i][j]));
                    tab[i][j].setBounds(x, y, 80, 80);
                    tab[i][j].setFont(new Font("Arial", Font.PLAIN, 20));
                    if ((i == 0 && j == 0) || (i == 5 && j == 5)) (tab[i][j]).setBackground(new Color(0, 153, 0));
                    else tab[i][j].setBackground(Color.white);
                    pPlansza.add(tab[i][j]);
                    tab[i][j].addMouseListener(this);
                }
            }
        }else{
            int x, y;
            for (int i = 0; i < 6; i++) {
                x = i * 80 + i * 2 + 2;
                for (int j = 0; j < 6; j++) {
                    y = j * 80 + j * 2 + 2;
                    tab[i][j] = new JButton(Integer.toString(dane[i][j]));
                    tab[i][j].setBounds(x, y, 80, 80);
                    tab[i][j].setFont(new Font("Arial", Font.PLAIN, 20));
                    int flaga=1;
                    for(Punkt aktualne : ruchy){
                        if(aktualne.getX()==i && aktualne.getY()==j) flaga=0;
                    }
                    if(flaga==0){
                        poprzedni.setPoint(i,j);
                        licznik++;
                    }
                    if ((i == 0 && j == 0) || (i == 5 && j == 5)) (tab[i][j]).setBackground(new Color(0, 153, 0));
                    else if(flaga==0) {
                        tab[i][j].setBackground(new Color(128, 128, 128));
                    }else{
                        tab[i][j].setBackground(Color.white);
                    }
                    pPlansza.add(tab[i][j]);
                    tab[i][j].addMouseListener(this);
                }
            }
        }
    }
    // Czysci plansze
    public void wyczyscPlansze(){
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                //dane[i][j]=0;
                pPlansza.remove(tab[i][j]);
            }
        }
        pPlansza.setVisible(false);
        getContentPane().remove(pPlansza);
        add(pPlansza);
        pPlansza.setVisible(true);
    }
    // Sprawdzamy czy rozwiazanie uzytkownika jest poprawne
    public void sprawdz(int i, int j){
        int suma=0;
        if(i+j==9) {
            for (Punkt aktualny : ruchy) {
                suma += dane[aktualny.getX()][aktualny.getY()];
            }
            if (suma == 147) {
                JOptionPane.showMessageDialog(this, "Gratulacje, Wygrałeś !");
            } else {
                int odp=JOptionPane.showConfirmDialog(this, "Niestety, Przegrałeś ;(\nGramy dalej ?","Koniec Gry",JOptionPane.YES_NO_OPTION);
                if(odp==JOptionPane.YES_OPTION){
                    wyczyscPlansze();
                    wypelnijPlansze(false);
                }else if(odp==JOptionPane.NO_OPTION){
                    System.exit(0);
                }
            }
        }else{
            int odp=JOptionPane.showConfirmDialog(this, "Niestety, Przegrałeś ;(\nGramy dalej ?","Koniec Gry",JOptionPane.YES_NO_OPTION);
            if(odp==JOptionPane.YES_OPTION){
                cofnij();
            }else if(odp==JOptionPane.NO_OPTION){
                System.exit(0);
            }
        }
    }
    // Szukamy poprawne rozwiązanie łamigłówki
    public void szukaj() {
        Random generator = new Random();
        List<Punkt> lista = new ArrayList<Punkt>();
        List<Punkt> lista1 = new ArrayList<Punkt>();
        int i=0, j=0,x=0, y=0, suma=0;
        while(i+j!=9){
            suma=0;
            while(suma!=147){
                sciezka.clear();
                lista.clear();
                lista.add(new Punkt(0, 0));
                lista.add(new Punkt(5, 5));
                int flaga=0, flaga1=0;
                i=0; j=0; suma=0;
                for (int k = 0; k < 13; k++) {
                    lista1.clear();
                    int bezpowtorzen = 0;
                    while (bezpowtorzen == 0) {
                        int t = generator.nextInt(2);
                        x = i;
                        y = j;
                        if (t == 0) {
                            if (i == 0) {
                                x = (i + generator.nextInt(2));
                            } else if (i == 5)
                                x = (i + generator.nextInt(2) - 1);
                            else {
                                x = (i + generator.nextInt(3) - 1);
                            }
                        } else {
                            if (j == 0) {
                                y = (j + generator.nextInt(2));
                            } else if (j == 5)
                                y = (j + generator.nextInt(2) - 1);
                            else {
                                y = (j + generator.nextInt(3) - 1);
                            }
                        }
                        bezpowtorzen = 1;
                        Punkt tmp = new Punkt(x, y);
                        for (Punkt jest : lista) {
                            if (jest.getX() == tmp.getX() && jest.getY() == tmp.getY()) bezpowtorzen = 0;
                        }
                        if(i!=0 && i!=5 && j!=0 && j!=5) {
                            if(lista1.size()>=4) {
                                flaga=1;
                                break;
                            }
                        }
                        else if (i+j==5){
                            if(lista1.size()>=2) {
                                flaga=1;
                                break;
                            }
                        }
                        else {
                            if(lista1.size()>=3) {
                                flaga=1;
                                break;
                            }
                        }
                        flaga1=0;
                        for (Punkt jest : lista1) {
                            if (jest.getX() == tmp.getX() && jest.getY() == tmp.getY()) flaga1=1;
                        }
                        if(flaga1==0) lista1.add(new Punkt(x, y));
                    }
                    if(flaga==1) break;
                    i = x;
                    j = y;
                    lista.add(new Punkt(i, j));
                    suma += dane[i][j];
                    sciezka.add(new Punkt(i,j));
                }

            }
        }
    }
    // Funkcja cofająca ruch
    public void cofnij(){
        try {
            if(poprzedni.getX()==0 && poprzedni.getY()==0) {
            }else{
                tab[poprzedni.getX()][poprzedni.getY()].setBackground(Color.white);
                ruchy.remove(ruchy.size() - 1);
                poprzedni.setPoint(ruchy.get(ruchy.size() - 1).getX(), ruchy.get(ruchy.size() - 1).getY());
                licznik--;
            }
        }catch (IndexOutOfBoundsException ex){
            poprzedni.setPoint(0,0);
            licznik=0;
            ruchy.clear();
        }
    }
    // Wczytywanie z pliku
    public void wczytaj(){
        JFileChooser fc = new JFileChooser();
        if(fc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
            File plik = fc.getSelectedFile();
            try{
                Scanner skaner =  new Scanner(plik);
                boolean czybylo;
                poprzedni.setPoint(0,0);
                ruchy.clear();
                licznik=0;
                for(int i = 0; i < 6; i++) {
                    for(int j = 0; j < 6; j++) {
                        dane[i][j] = skaner.nextInt();
                        czybylo = skaner.nextBoolean();
                        if(czybylo==true) {
                            ruchy.add(new Punkt(i, j));
                        }
                    }
                }
                wyczyscPlansze();
                wypelnijPlansze(true);
                szukaj();
                skaner.close();
            }catch(FileNotFoundException ex){

            }
        }
    }
    // Zapisywanie pliku
    public void zapisz(){
        JFileChooser fc = new JFileChooser();
        if(fc.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
            File plik = fc.getSelectedFile();
            try{
                PrintWriter pw = new PrintWriter(plik);
                //Scanner skaner =  new Scanner(plik);
                for(int i = 0; i < 6; i++) {
                    for(int j = 0; j < 6; j++) {
                        //dane[i][j] = skaner.nextInt();
                        pw.println(dane[i][j]);
                        int flaga=1;
                        for(Punkt aktualne : ruchy){
                            if(aktualne.getX()==i && aktualne.getY()==j) flaga=0;
                        }
                        if(flaga==1){
                            pw.println("false");
                        }else
                            pw.println(true);

                    }
                }
                pw.close();
            }catch(FileNotFoundException ex){

            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object x = e.getSource();
        if (x == bWyjscie){
            System.exit(0);
        }else if(x == bNowagra){
            wyczyscPlansze();
            wypelnijPlansze(false);
        }else if(x==bCofnij){
            cofnij();
        }else if(x==bInstrukcja){
            ImageIcon iconic = new ImageIcon(getClass().getResource("instrukcja.png"));
            JOptionPane.showMessageDialog(this, "Od jednego narożnego, zielonego pola trzeba dojść do drugiego,\n" +
                    "przechodząc w każdym kroku z pola na pole tylko w rzędzie lub kolumnie, nigdy na ukos.\n" +
                    "Trasa powinna obejmować 15 pól z liczbami, których suma musi wynosić 150.\n"+
                    "W małym przykładzie z rozwiązaniem na trasie jest 13 pól z sumą równą 100.", "Instrukcja", JOptionPane.INFORMATION_MESSAGE, iconic);
        }else if(x==bOdpowiedz){
            Odpowiedz odp = new Odpowiedz(dane, sciezka);
            odp.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            odp.setVisible(true);
        }else if(x==bZapisz){
            zapisz();
        }else if(x==bWczytaj){
            wczytaj();
        }
    }

    @Override
    // Co dzieje się podczas klikniecia myszka na guzik
    public void mouseClicked(MouseEvent e) {
        boolean z = (e.getButton()==MouseEvent.BUTTON1);
        Object y = e.getSource();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (y == tab[i][j]) {
                    // WYKLUCZAMY RUCHY NA PIERWSZA I OSTATNIA LICZBE
                    if((i==0 && j==0)||(i==5 && j==5)) {
                    }
                    // USTAWIAMY MOZLIWOSC RUCHU TYLKO GORA,DOL,LEWO,PRAWO
                    else if((poprzedni.getX()-i==1 && poprzedni.getY()-j==0) || (poprzedni.getX()-i==0 && poprzedni.getY()-j==-1) || (poprzedni.getX()-i==-1 && poprzedni.getY()-j==0) || (poprzedni.getX()-i==0 && poprzedni.getY()-j==1)){
                        int flaga=1;
                        for(Punkt aktualne : ruchy){
                            if(aktualne.getX()==i && aktualne.getY()==j) flaga=0;
                        }
                        if(flaga==1) {
                            ruchy.add(new Punkt(i, j));
                            poprzedni.setPoint(i,j);
                            if (z) {
                                tab[i][j].setBackground(new Color(128, 128, 128));
                                licznik++;
                                if (licznik == 13) {
                                    sprawdz(i, j);
                                }
                            } else {

                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
