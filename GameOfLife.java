import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**  
 * @author Emanuele Cordioli
 * @author Gian Marco Amicabile
 * 
 * Versione del Game of Life che usa la libreria grafica Swing. 
 * E' possibile fare morire o far nascere una cellula cliccandoci sopra con il mouse, si può posizionare 
 * un oscillatore o una navicella spaziale cliccando col mouse prima sul widget di selezione del tipo di 
 * disegno e poi sulla cellula dove incollare la figura. La velocità del passaggio delle generazioni è gestita 
 * tramite uno slider e il passaggio da una generazione all'altra è effettuato in parallelo usando un numero 
 * variabile di thread, specificato al momento della costruzione del game of life. 
 */
public class GameOfLife extends JFrame{
     // creazione delle figure che possone essere inserite 
     private static final int[][] CELLULA = {{1}};
	 private static final int[][] OSCILLATORE = {{1, 1, 1}};
	 private static final int[][] NAVICELLA = {{0, 1, 0},
											   {0, 0, 1},
											   {1, 1, 1}};													
	 private static final int[][] ASTRONAVE = {{1, 0, 0, 1, 0},
											   {0, 0, 0, 0, 1},
											   {1, 0, 0, 0, 1},
											   {0, 1, 1, 1, 1}};													
	 private static final int[][] TRIGGER = {{0, 1, 0, 0},
											{1, 0, 0, 1},
								            {1, 0, 0, 1},
											{0, 0, 1, 0}};	   
	 private static final int[][] SHUTTLE = {{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
	                                       {0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0},
	                                       {0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0},
	                                       {0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0},
	                                       {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	                                       {0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0},
	                                       {0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0},
	                                       {0, 1, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1, 0},
	                                       {1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1},
	                                       {0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0}};
	 private static final int[][] DIAMANTE = {{0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0},
	                                          {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	                                          {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
	                                          {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	                                          {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
	                                          {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	                                          {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
	                                          {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	                                          {0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0}};
	 private static final int[][] PULSATORE = {{0, 1, 1, 0, 1, 1, 0},
											   {1, 0, 0, 1, 0, 0, 1},
											   {0, 1, 1, 0, 1, 1, 0}}; 
	 private static final int[][] FUSIONE = {{0, 0, 1, 0, 0, 0, 0, 1, 0, 0},
											{1, 1, 0, 1, 1, 1, 1, 0, 1, 1},
											{0, 0, 1, 0, 0, 0, 0, 1, 0, 0}};
	private static final int[][] GALASSIA = {{1, 1, 0, 1, 1, 1, 1, 1, 1},
											 {1, 1, 0, 1, 1, 1, 1, 1, 1},
											 {1, 1, 0, 0, 0, 0, 0, 0, 0},
											 {1, 1, 0, 0, 0, 0, 0, 1, 1},
											 {1, 1, 0, 0, 0, 0, 0, 1, 1},
											 {1, 1, 0, 0, 0, 0, 0, 1, 1},
											 {0, 0, 0, 0, 0, 0, 0, 1, 1},
											 {1, 1, 1, 1, 1, 1, 0, 1, 1},
											 {1, 1, 1, 1, 1, 1, 0, 1, 1}}; 
	private static final int[][] OTTAGONO = {{0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0},
	                                         {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0},
	                                         {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	                                         {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0},
	                                         {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
	                                         {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
	                                         {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
	                                         {1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1},
											 {1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1},
	                                         {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
	                                         {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
	                                         {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
	                                         {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0},
	                                         {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	                                         {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0},
	                                         {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0}}; 
											 
     private static final int VIVA = 1, MORTA = 0,                   // stati possibili di una cellula  
                              RIGHE = 55, COLONNE = 90;              // dimensioni della matrice di cellule
     private JLabel lblVelocita, lblGenerazione;                     // etichette per velocità e generazione
     private JButton btnNext, btnStart, btnRand, btnClear, btnInser; // bottoni per la gestione 
     private Cellula[][] cellule;                                    // matrice contenente le cellule     
     private Cellula[][] appo;                                       // matrice di appoggio per memorizzare la matrice 'cellule'  
     private JSlider sldVel;                                         // slider per la gestione della velocità
     private int generazione;                                        // generazione attuale
     private boolean start = false;                                  // indica che si stanno calcolando in successione le generazioni
     private Widget wdgScelta;                                       // widget per l'inserimento di oscillatori o navicelle
     private Schiavo[] schiavi;                                      // array di thread per il calcolo della nuova generazioen
     private int nextColonna, nextRiga;                              // indici utilizzati dai thread per muoversi sulla matrice 
     private int numSchiavi;                                         // numero di thread da utilizzare
     private int[][] figura;                                         // figura scelta per l' inserimento nella matrice di cellule
     
     /** 
      * Main che crea il Game Of Life
      * @param argomenti del main
      */
     public static void main(String[] args){
        try{
           new GameOfLife(1); // INSERIRE QUI IL NUMERO DEI THREAD CHE SI INTENDONO UTILIZZARE
        }
        catch(InterruptedException e){}
     }
    
     /**  
      * Costruttore della classe GameOfLife.
      * @param numero di thread da utilizzare. 
      */
     public GameOfLife(int nSchiavi) throws InterruptedException{
        inizializzaVariabili(nSchiavi);
        disegnaMatriceCellule();
        dimensioniElementi();
        aggiungiElementi();
        impostaAscoltatori();
        
        sldVel.setBackground(Color.LIGHT_GRAY);
        this.setBounds(100, 10, 1100, 760);
        this.setTitle("GIOCO DELLA VITA");
        this.setResizable(false);
        this.setLayout(null);
        this.getContentPane().setBackground(Color.LIGHT_GRAY);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);    
     }
     
     /**  
      * Inizializza i bottoni, le etichette e tutte le altre variabili del Game Of Life.
      * @param numero di thread da utilizzare.
      */
     private void inizializzaVariabili(int n){
        Font f = new Font("", Font.BOLD, 14);
        lblVelocita = new JLabel("  VELOCITA' :");
        lblGenerazione = new JLabel("GENERAZIONE : " + generazione);
        lblVelocita.setFont(f);
        lblGenerazione.setFont(f);
        btnRand = new JButton("RANDOM");
        btnStart = new JButton("START");
        btnNext = new JButton("NEXT");
        btnClear = new JButton("CLEAR");
        btnInser = new JButton("INSERISCI");
        sldVel = new JSlider(1, 999, 500);
        cellule = new Cellula[RIGHE][COLONNE]; 
        appo = new Cellula[RIGHE][COLONNE]; 
        wdgScelta = new Widget();
        figura = CELLULA;   // inizialmente la figura di default da inserire è una cellula
        generazione = 0;
        numSchiavi = n;
     }
     
     /**  
      * Crea la matrice di cellule di dimensioni prefissate.
      */
     private void disegnaMatriceCellule(){
        int m = 19, n = 8; // variabili usate per il posizionamento delle cellule
        for(int i = 0; i < RIGHE; i++){
           for(int j = 0; j < COLONNE; j++){          
              cellule[i][j] = new Cellula(i,j);
              // per motivi di ordine non sono mostrate le cellule ai bordi della matrice:
              // durante la verifica non vengono considerate quindi resterebbero sempre morte
              if(i != 0 && j != 0 && i != (RIGHE - 1) && j != (COLONNE - 1)){ 
                 cellule[i][j].setBounds(m, n, 12, 12); // dimensioni delle cellule
                 m += 12;
                 this.add(cellule[i][j]);
              }
           }
           n += 12;
           m = 19;
        }     
     }
     
     /**  
      * Setta le dimensioni dei diversi elementi che compongono il Game Of Life.
      */
     private void dimensioniElementi(){
        lblVelocita.setBounds(670, 670, 130, 35);
        lblGenerazione.setBounds(30, 670, 170, 35);
        btnRand.setBounds(540, 670, 110, 35);
        btnStart.setBounds(330, 670, 90, 35);
        btnNext.setBounds(225, 670, 90, 35);
        btnClear.setBounds(435, 670, 90, 35);
        btnInser.setBounds(945, 670, 125, 35);
        sldVel.setBounds(775, 672, 150, 35);
        wdgScelta.setBounds(900, 20, 173, 636);
     }
    
     /**  
      * Aggiunge tutti gli elementi al pannello della finestra del Game Of Life.
      */
     private void aggiungiElementi(){
        this.add(lblVelocita);
        this.add(lblGenerazione);
        this.add(btnRand);
        this.add(btnStart);
        this.add(btnNext);
        this.add(btnClear);
        this.add(btnInser);
        this.add(sldVel);
        this.add(wdgScelta);
     }
    
     /**  
      * Aggiunge a ciascun bottone il proprio ascoltatore.
      */
     private void impostaAscoltatori(){
       
        // il bottone NEXT permette di calcolare la prossima generazione
        btnNext.addActionListener(new ActionListener(){
           @Override 
           public void actionPerformed(ActionEvent r){
              prossimaGenerazione();            
           }
        });
        
        // il bottone CLEAR resetta la matrice di cellule e azzera la generazione attuale
        btnClear.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent r){
              for(int i = 0; i < RIGHE; i++)
                 for(int j = 0; j < COLONNE; j++)
                    cellule[i][j].muori();
              generazione = 0;
              lblGenerazione.setText("GENERAZIONE : " + generazione);
           }
        });
        
        // il bottone START calcola una dopo l'altra le generazioni future 
        // una volta avviate le generazioni diventa bottone STOP per fermarle
        btnStart.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent event){
              if(start == false){
                 btnStart.setText("STOP");
                 btnClear.setEnabled(false);
                 btnNext.setEnabled(false);
                 btnRand.setEnabled(false);
                 start = true; // indica che � iniziata la produzione di nuove generazioni
                 SchiavoStart t = new SchiavoStart();
                 t.start(); 
              }
              else{
                   start = false; // interrompe la produzione di nuove generazioni
                   btnStart.setText("START");
                   btnClear.setEnabled(true);
                   btnNext.setEnabled(true);
                   btnRand.setEnabled(true);
              }  
           }
        });
        
        // il bottone RAND permette di produrre una generazione casuale
        btnRand.addActionListener(new ActionListener (){
           @Override     
           public void actionPerformed(ActionEvent r){
              for(int i = 0; i < RIGHE; i++){
                 for(int j = 0; j < COLONNE; j++){
                    cellule[i][j].muori();
                    if(((int)(Math.random() * 100) % 3) > 1) cellule[i][j].nasci();   
                 }    
              }
           }
        });
        
        // il bottone INSERT fa comparire il widget per inserire una figura
        btnInser.addActionListener(new ActionListener (){
           @Override     
           public void actionPerformed(ActionEvent r){
              wdgScelta.setVisible(true);
              // nascondo momentaneamente le cellule che coprono il widget
              for(int i = 0; i < RIGHE - 1; i++)
                 for(int j = 74; j < COLONNE - 1; j++)
                    cellule[i][j].setVisible(false);
           }
        });
     }
     
     /**  
      * Verifica lo stato delle 8 cellule vicine alla cellula indicata per poi applicare le leggi del Game Of Life:
      *  - cellula viva con meno di 2 cellule vicine vicine: muore per isolamento;
      *  - cellula viva con più di 3 cellule vicine vive: muore per sovraffollamento; 
      *  - cellula morta con 2 o 3 cellule vicine vive : nasce;
      *  - altrimenti la cellula resta nello stato in cui si trova.
      * @param r : riga della cellula da controllare.
      * @param c : colonna della cellula da controllare.
      */
     public void verificaCellula(int r, int c){
        int viciniVivi=0;
        viciniVivi += appo[r - 1][c - 1].stato;
        viciniVivi += appo[r - 1][c].stato;
        viciniVivi += appo[r - 1][c + 1].stato;
        viciniVivi += appo[r][c - 1].stato;
        viciniVivi += appo[r][c + 1].stato;
        viciniVivi += appo[r + 1][c - 1].stato;
        viciniVivi += appo[r + 1][c].stato;
        viciniVivi += appo[r + 1][c + 1].stato;
        if(appo[r][c].stato == VIVA && viciniVivi != 2 && viciniVivi != 3)
           cellule[r][c].muori();
        if(appo[r][c].stato == MORTA && viciniVivi == 3 )
           cellule[r][c].nasci();
     }
     
     /**  
      * Dopo aver memorizzato la generazione attuale nella matrice appo, calcola la prossima generazione.
      */
     public void prossimaGenerazione(){
        for(int i = 0; i < RIGHE; i++)
           for(int j = 0; j < COLONNE; j++) 
              appo[i][j] = new Cellula(cellule[i][j]);
        startSchiavi(numSchiavi);
        waitSchiavi();
        // azzero gli indici per la prossima generazione
        nextRiga = 0;
        nextColonna = 0;
        generazione++;
        lblGenerazione.setText("GENERAZIONE : " + generazione);        
     }
     
     /**  
      * Thread utilizzato dal bottone START per calcolare la successione di generazioni.
      */
     public class SchiavoStart extends Thread{
        
        /**  
         * Fa partire lo SchiavoStart nel calcolo delle generazioni successive
         * intervallate da un tempo impostato mediante lo slider per la velocità.
         */
        @Override
        public void run(){
           while(start){
              prossimaGenerazione();
              try{
                  Thread.sleep(1000 - sldVel.getValue());
              }catch(InterruptedException e){};
           }
        }
     } 
     
     /**  
      * Thread utilizzato in parallelo insieme agli altri per il calcolo effettivo della prossima generazione.
      */
     private class Schiavo extends Thread{
        private String nome;
        
        /** 
         * Costruttore di Schiavo.
         * @param nome da assegnare al thread.
         */
        public Schiavo(String s){
           this.nome = s;
        }
        
        /**  
         * Fa partire lo Schiavo che si occupa di verificare cellula dopo cellula per 
         * determinare la prossima generazione (mutua esclusione su ogni cellula in modo che due
         * thread diversi lavorino solo su cellule differenti).
         */
        @Override
        public void run(){
           int nextRiga;
           int nextColonna; 
           do{synchronized(GameOfLife.this){
                 nextColonna = GameOfLife.this.nextColonna++;
                 nextRiga=GameOfLife.this.nextRiga;
                 if(!(nextColonna < COLONNE)){
                    nextRiga = GameOfLife.this.nextRiga++;
                    nextColonna=GameOfLife.this.nextColonna = 0;
                 }
              }
              // non vengono verificate le cellule ai bordi per evitare di uscire dai limiti della matrice
              if(nextRiga < (RIGHE - 1) && nextColonna < (COLONNE - 1) && nextRiga > 0 && nextColonna > 0){
                 //System.out.println("Thread "+this.nome+": sto verificando la cella ("+ nextRiga+ " , " +nextColonna+")");
                 verificaCellula(nextRiga, nextColonna);
              }
           }while(nextRiga < (RIGHE - 1) || nextColonna < (COLONNE - 1));
           //System.out.println("Thread "+this.nome+": ho finito");
        }
     }   
     
     /**  
      * Crea i thread per il calcolo in parallelo della generazione e li fa partire.
      * @param numero di thread da utilizzare.
      */  
     private void startSchiavi(int numDiSchiavi){
        schiavi = new Schiavo[numDiSchiavi];
        for (int pos = 0; pos < numDiSchiavi; pos++)
           (schiavi[pos] = new Schiavo(""+pos)).start();
     }
     
     /**  
      * Attende la terminazione di tutti i thread.
      */
     private void waitSchiavi(){
        for (Schiavo s: schiavi)
           try {s.join();
           }
           catch (InterruptedException e){}
     }
     
     /**
      * Rappresenta una delle possibili figure da scegliere, composta da 
      * un' etichetta, un bottone e una matrice per la composizione.
      */
     public class Formazione{
         protected JLabel etichetta;
         protected JButton bottone;
         protected int[][] matrice;
         
         /**
          * Costruttore della classe Formazione.
          * @param nome : nome della formazione.
          * @param immagine : immagine da applicare al bottone.
          * @param forma : matrice di interi che rappresenta la figura.
          */
         public Formazione(String nome, ImageIcon immagine, int[][] forma){
            this.etichetta = new JLabel(nome);
            this.bottone = new JButton(immagine);
            this.matrice = forma;
            
            // i bottoni di ogni formazione permettono di selezionare la figura  da inserire
            this.bottone.addActionListener(new ActionListener(){
                 @Override 
                 public void actionPerformed(ActionEvent r){
                    figura = matrice;
                    // una volta scelta la figura da inserire ricopro il widget di selezione
                    for(int i = 0; i < RIGHE - 1; i++) 
                       for(int j = 74; j < COLONNE - 1; j++)
                          cellule[i][j].setVisible(true);
                    wdgScelta.setVisible(false);
                 }
              }); 
         } 
     }
     
     /**  
      * Widget che compare una volta premuto il bottone INSER; permette di aggiungere
      * alla matrice di cellule una figura tra quelle che abbiamo creato nelle formazioni.
      */
     private class Widget extends JPanel{
        
        private Formazione[] formazioni; 
        
        /**  
         * Costruttore della classe Widget.
         */
        public Widget(){
           
           formazioni = new Formazione[10];  // collezione di formazioni differenti
           formazioni[0] = new Formazione("NAVICELLA", new ImageIcon("navicella.jpg"), NAVICELLA);
           formazioni[1] = new Formazione("ASTRONAVE", new ImageIcon("astronave.jpg"), ASTRONAVE);
           formazioni[2] = new Formazione("SHUTTLE", new ImageIcon("shuttle.jpg"), SHUTTLE);
           formazioni[3] = new Formazione("DIAMANTE", new ImageIcon("diamante.jpg"), DIAMANTE);
           formazioni[4] = new Formazione("TRIGGER", new ImageIcon("trigger.jpg"), TRIGGER);
           formazioni[5] = new Formazione("PULSATORE", new ImageIcon("pulsatore.jpg"), PULSATORE);
           formazioni[6] = new Formazione("GALASSIA", new ImageIcon("galassia.jpg"), GALASSIA);
           formazioni[7] = new Formazione("OSCILLATORE", new ImageIcon("oscillatore.jpg"), OSCILLATORE);
           formazioni[8] = new Formazione("OTTAGONO", new ImageIcon("ottagono.jpg"), OTTAGONO);
           formazioni[9] = new Formazione("FUSIONE", new ImageIcon("fusione.jpg"), FUSIONE);
           
           // posizionamento di ogni formazione nel widget
           int x = 72, y = 24, w = 17, z = 20;
           for(int c=0; c < formazioni.length; c++){
              formazioni[c].etichetta.setBounds(x, y, 100, 35);
              formazioni[c].bottone.setBounds(w, z, 40, 40);
              y += 58; 
              z += 58;
              if(c == 3){
                 // separo le formazioni che rappresentano navi spaziali da quelle di oscillatori
                 JLabel separatore = new JLabel("___________________");
                 separatore.setBounds(w, z-20, 140, 40);
                 this.add(separatore);
                 y+=30;
                 z+=30;
              }
              this.add(formazioni[c].etichetta);
              this.add(formazioni[c].bottone);
           }       
 
           this.setBackground(Color.WHITE);
           this.setVisible(false);
           this.setLayout(null);
        }
     }
    
     /** 
      * Cella che partecipa al Game Of Life ed evolve generazione dopo generazione,
      * nascendo/morendo a seconda dello stato delle 8 cellule adiacenti nella matrice.
      */
     private class Cellula extends JButton{
         
        private final int colonna;
        private final int riga;
        private int stato;
        
        /**  
         * Costruttore di copia della classe Cellula che crea un oggetto con le stesse 
         * caratteristiche di quello passato come parametro.
         * @param cellula da "clonare".
         */
        public Cellula(Cellula c){
           this.colonna = c.colonna;
           this.riga = c.riga;
           this.stato = c.stato;
           this.setBackground(this.stato == MORTA ? Color.WHITE : Color.DARK_GRAY);
        }
        
        /**  
         * Costruttore della classe Cellula (inizialmente ogni cellula è morta).
         */
        public Cellula(int r, int c){
           this.colonna = c;
           this.riga = r;
           this.stato = MORTA;
           setBackground(Color.WHITE);
                     
           this.addMouseListener(new MouseListener(){
               
              // al passaggio (entrante) del cursore su una cellula, quest' utlima viene ombreggiata
              @Override
              public void mouseEntered(MouseEvent arg0){
                 creaOmbra();                           
              } 
               
              // al passaggio (uscente) del cursore su una cellula, a quest' ultima viene tolta l'ombreggiatura
              @Override
              public void mouseExited(MouseEvent arg0){
                 rimuoviOmbra();
              }
              
              // per inserire una figura bisogna cliccare (premere e rilasciare) dove vogliamo posizionarla
              @Override
              public void mouseClicked(MouseEvent arg0){ 
    		     if(nonSuperaBordi()){ 
        		    for(int i = 0; i < figura.length; i++)
        	           for(int j = 0; j < figura[i].length ; j++)
        				  if(figura != CELLULA && figura[i][j] == 1)
        		 		     cellule[riga + i][colonna + j].nasci();
        		    figura = CELLULA;
        	     }	 
              }
              
              // per cambiare lo stato di una cellula basta premere su di essa
              @Override
              public void mousePressed(MouseEvent arg0){
                 if(figura == CELLULA){
      			    if(cellule[riga][colonna].stato == VIVA)
      				   cellule[riga][colonna].muori();     			  
      				else
      				   cellule[riga][colonna].nasci(); 
                 }
              }
              
              @Override
              public void mouseReleased(MouseEvent arg0){}                
           });  
        }  
             
        /**  
         * Crea un ombra sulla matrice a seconda della figura che si sta per inserire.
         */
        private void creaOmbra(){
           if(nonSuperaBordi()){
              for(int i = 0; i < figura.length; i++)
                 for(int j = 0; j < figura[i].length; j++)
                 if(figura[i][j] == 1)
        	  	    cellule[riga + i][colonna + j].setBackground((figura[i][j] == 1 && cellule[riga + i][colonna + j].stato == MORTA) ? Color.LIGHT_GRAY : Color.DARK_GRAY);        				        			  	 
           }        
        }  
           
        /**  
         * Rimuove l'obra creata con il metodo creaOmbra.
         */
        private void rimuoviOmbra(){
           if(nonSuperaBordi()){
              for(int i = 0; i < figura.length; i++)
                 for(int j = 0; j < figura[i].length; j++)      			
        	  	    cellule[riga + i][colonna + j].setBackground((cellule[riga + i][colonna + j].stato == VIVA ) ? Color.DARK_GRAY : Color.WHITE );        				       				 
           }          
        }
        
        /**  
         * Impedisce di posizionare una figura di cellule al di fuori dei bordi della matrice.
         */
        private boolean nonSuperaBordi(){
           return (riga + figura.length < RIGHE  && colonna + figura[0].length < COLONNE);
        } 
        
        /**  
         * Permette di uccidere una cellula. 
         */
        public void muori(){
           this.stato = MORTA;
           this.setBackground(Color.WHITE);
        }
        
        /**  
         * Permettere di dar vita a una cellula.
         */ 
        public void nasci(){
           this.stato = VIVA;
           this.setBackground(Color.DARK_GRAY);
        }
     }
}