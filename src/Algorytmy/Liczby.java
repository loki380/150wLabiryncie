package Algorytmy;

import java.util.*;

public class Liczby {
    // GENERATOR LICZB DO PLANSZY, ZAWSZE BEDZIE ISTNIALO ROZWIAZANIE ŁAMIGŁÓWKI !
    public void generuj(int dane[][], List <Punkt> sciezka) {
        Random generator = new Random();
        List<Punkt> lista = new ArrayList<Punkt>();
        List<Punkt> lista1 = new ArrayList<Punkt>();
        int i=0, j=0,x = 0, y = 0, suma = 0;
        while (i+j!=9) {
            suma=0;
            while (suma != 147) {
                for(int aa=0;aa<6;aa++){
                    for(int bb=0;bb<6;bb++){
                        dane[aa][bb]=0;
                    }
                }
                sciezka.clear();
                lista.clear();
                lista.add(new Punkt(0, 0));
                lista.add(new Punkt(5, 5));
                int a = 4;
                int b, flaga = 0, flaga1=0;
                i = 0;
                j = 0;
                suma=0;
                for (int k = 0; k < 13; k++) {
                    lista1.clear();
                    int bezpowtorzen = 0;
                    while (bezpowtorzen == 0) { //sprawdzamy czy nie kliknieto ponownie tego samego guzika
                        int t = generator.nextInt(2); // losujemy czy pojedziemy lewo, prawo czy gora, dol
                        x = i;
                        y = j;
                        if (t == 0) {
                            if (i == 0) {   // zapewniamy sobie aby nie mozna bylo jechac po skosach oraz wychodzic poza plansze
                                x = (i + generator.nextInt(2));
                            } else if (i == 5)
                                x = (i + generator.nextInt(2) - 1);
                            else {
//                                if (j == 5 || j==0) {
//                                    x = (i + generator.nextInt(2));
//                                } else{
                                    x = (i + generator.nextInt(3) - 1);
                                //}
                            }
                        } else {
                            if (j == 0) { // zapewniamy sobie aby nie mozna bylo jechac po skosach oraz wychodzic poza plansze
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
                        // Sprawdzamy czy program nie zabladzil w petli podczas ruchow
                        if(i!=0 && i!=5 && j!=0 && j!=5) {
                            if(lista1.size()>=4) {
                                flaga=1;
                                break;
                            }
                        }
                        else if (i+j==5){ // || i+j==1
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

                    if (k < 12) {       // Ustawiamy wartosc punku
                        if (a < -8) {
                            b = generator.nextInt(9) - 8;
                            dane[i][j] = 11 + b;
                            a = a - b;
                        } else if (a > 8) {
                            b = generator.nextInt(9);
                            dane[i][j] = 11 + b;
                            a = a - b;
                        } else {
                            b = generator.nextInt(16) - 8;
                            dane[i][j] = 11 + b;
                            a = a - b;
                        }
                    } else {
                        dane[i][j] = 11 + a;
                        if(dane[i][j]>19 || dane[i][j]<3) break;
                    }
                    suma += dane[i][j];
                    sciezka.add(new Punkt(i,j)); // dodajemy punkt do sciezki prawidlowego rozwiazania
                }
            }
            dane[0][0] = 1;
            dane[5][5] = 2;
            for(int aa=0;aa<6;aa++){
                for(int bb=0;bb<6;bb++){
                    if(dane[aa][bb]==0)
                        dane[aa][bb]= generator.nextInt(19)+1;
                }
            }
        }
    }
}
