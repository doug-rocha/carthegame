
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nonacept.carthegame;

/**
 *
 * @author Douglas Rocha
 */
import com.nonacept.audio.mp3.Player;
import com.nonacept.carthegame.Options.OpcoesEmbut;
import com.nonacept.carthegame.BackManager.GameThread;
import com.nonacept.carthegame.ETC.AnimacaoIntro;
import com.nonacept.carthegame.Options.Resolutions;
import com.nonacept.carthegame.Options.DefResolutions;
import com.nonacept.carthegame.Idiomas.TutoLang;
import com.nonacept.carthegame.IO.SaveGame;
import com.nonacept.carthegame.Options.OpcoesVar;
import com.nonacept.carthegame.Idiomas.Idioma;
import com.nonacept.carthegame.Entidades.Itens;
import com.nonacept.carthegame.Entidades.Paredes;
import com.nonacept.carthegame.Entidades.Cenario;
import com.nonacept.carthegame.Entidades.Caixote;
import com.nonacept.carthegame.Entidades.Bots;
import com.nonacept.carthegame.Entidades.Cars;
import com.nonacept.carthegame.Entidades.CenarioChanger;
import com.nonacept.carthegame.Entidades.Chegada;
import com.nonacept.carthegame.Entidades.DestroyedCar;
import com.nonacept.carthegame.Entidades.Teleportes;
import com.nonacept.carthegame.IO.Fontes;
import com.nonacept.carthegame.IO.LevelsList;
import com.nonacept.carthegame.Idiomas.Creditos;
import com.nonacept.carthegame.Options.FrPS;
import com.thoughtworks.xstream.XStream;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import enigma.console.*;
import enigma.core.Enigma;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.UIManager;

//A CLASSE Game HERDA AS FUNCIONALIDADES DE JFRAME
public class Game extends JFrame implements KeyListener, MouseListener, FocusListener {

    public boolean pauseMusica = false;
    public int posicaoPauseMusica = 0;
    public int jseg1 = 0, jseg2 = 0;
    public boolean jinfovoltando = false;
    public boolean jalbumArtVoltando = false;
    public int jtimeInfo = 0;
    public Toolkit tk;
    public Dimension tela;
    public boolean telacheia;
    public double masterVolume;
    public float masterVolFloat;
    public Player player;
    // <op>
    public static final String ver = "0.9.51 alpha", gameID = "DS_CTG";
    private static boolean directGame = false;
    public static boolean devVer = true;
    public final double verint = com.nonacept.carthegame.IO.LevelParser.ver;
    public File musicDiretory, menuMusicDiretory;
    public File levelDirectory;
    public File[] musicFiles, musicasMD;
    public File sSDiretory;
    public int countmus, countmenumus, gameTipo, lvlAtual;
    public String Infosm;
    public ArrayList<Paredes> walls = new ArrayList<>();
    public ArrayList<Teleportes> portals = new ArrayList<>();
    public ArrayList<Chegada> linhasChega = new ArrayList<>();
    public ArrayList<Caixote> caixos = new ArrayList<>();
    public ArrayList<DestroyedCar> destruidos = new ArrayList<>();
    public ArrayList<Cenario> itensCenario = new ArrayList<>();
    public ArrayList<CenarioChanger> mapLinks = new ArrayList<>();
    public Paredes[] wall;
    public Teleportes[] portal;
    public Chegada[] chegadas;
    public Caixote[] caixo;
    public DestroyedCar[] destruidoscar;
    public Cenario[] cenas;
    public Itens[] itensJg2;
    public CenarioChanger[] mapLink;
    public ArrayList<Itens> listItensJg2 = new ArrayList<>();
    public Idioma iMsg;
    public TutoLang tutoMsg;
    public Creditos credMsg;
    public String lvlName, lvlDescri, OSystem;
    public int mapX, mapY;
    public static Console DEBUGCONSOLE;
    public static TextAttributes TA;
    public DefResolutions[] resolsDisp;
    public LevelsList[] listaLevels;
    // </op>
    public static boolean DEBUGMODE = false;
    public boolean noSave = true;
    public boolean menuEsquecido = true;
    public static boolean noAnimacao = false;
    public boolean ovisivel = false;
    public boolean menuStart = false, menuSair = false;
    public int menuStartOp = 0, menuSairOp = 0;
    public boolean InstrucoesView, antiAliasing = true;
    public Random geradorRdm = new Random();
    // <Players>(reprodutores de audio)
    // </Players>

    public OpcoesEmbut op;
    public SFXSoundboard sfx;

    public BufferedImage backBuffer, fundoPause; // <-BUFFER
    public BufferedImage pintura;
    public int janelaW = 250; // <-LARGURA DA TELA
    public int janelaH = 200; // <-ALTURA DA TELA
    public static double MODRESOL;
    public Cars carro;
    public Image fundoIm;
    public Image comemoracao;
    public Image icone;
    public LoadingInicial li;
    public Thread lit;

    public void SaveGamem(String SName) {
        try {
            int[] botx = new int[1000];
            int[] boty = new int[1000];
            int[] oldbotx = new int[1000];
            int[] oldboty = new int[1000];
            int[] botvelo = new int[1000];
            /*
             * int[] wx = new int[wall.length]; int[] wy = new int[wall.length]; int[] wox =
             * new int[wall.length]; int[] woy = new int[wall.length]; int[] px = new
             * int[portal.length]; int[] py = new int[portal.length]; int[] dpx = new
             * int[portal.length]; int[] dpy = new int[portal.length];
             */
            boolean[] pa = new boolean[portal.length];
            /*
             * int[] cx = new int[chegadas.length]; int[] cy = new int[chegadas.length];
             * int[] ocx = new int[chegadas.length]; int[] ocy = new int[chegadas.length];
             */
            boolean[] ca = new boolean[chegadas.length];
            //int[] cxcX = new int[caixo.length];
            //int[] cxcY = new int[caixo.length];
            //int[] cxcOldX = new int[caixo.length];
            //int[] cxcOldY = new int[caixo.length];
            double[] cxcDur = new double[caixo.length];
            boolean[] cxcDest = new boolean[caixo.length];
            //int[] dcarX = new int[destruidoscar.length];
            //int[] dcarY = new int[destruidoscar.length];
            //int[] oldDcarX = new int[destruidoscar.length];
            //int[] oldDcarY = new int[destruidoscar.length];
            double[] dcarDur = new double[destruidoscar.length];
            boolean[] dcarExpl = new boolean[destruidoscar.length];

            int[] tipoIJG2 = new int[itensJg2.length];
            boolean[] ativoJG2 = new boolean[itensJg2.length];

            int itTipo = 0;
            boolean itAtivo = true;
            /*
             * int[] cnx = new int[cenas.length]; int[] cny = new int[cenas.length]; int[]
             * cnox = new int[cenas.length]; int[] cnoy = new int[cenas.length];
             */
            for (int i = 0; i < inimigos.length; i++) {
                botx[i] = inimigos[i].x;
                boty[i] = inimigos[i].y;
                oldbotx[i] = inimigos[i].oldX;
                oldboty[i] = inimigos[i].oldY;
                botvelo[i] = inimigos[i].velocidade;
            }
            /*
             * for (int j = 0; j < wall.length; j++) { wx[j] = wall[j].x; wy[j] = wall[j].y;
             * wox[j] = wall[j].oldX; woy[j] = wall[j].oldY; }
             */
            for (int p = 0; p < portal.length; p++) {
                /*
                 * px[p] = portal[p].x; py[p] = portal[p].y; dpx[p] = portal[p].destX; dpy[p] =
                 * portal[p].destY;
                 */
                pa[p] = portal[p].ativo;
            }
            for (int c = 0; c < chegadas.length; c++) {
                /*
                 * cx[c] = chegadas[c].x; cy[c] = chegadas[c].y; ocx[c] = chegadas[c].oldX;
                 * ocy[c] = chegadas[c].oldY;
                 */
                ca[c] = chegadas[c].ativo;
            }
            for (int cxi = 0; cxi < caixo.length; cxi++) {
                //cxcX[cxi] = caixo[cxi].x;
                //cxcY[cxi] = caixo[cxi].y;
                //cxcOldX[cxi] = caixo[cxi].oldX;
                //cxcOldY[cxi] = caixo[cxi].oldY;
                cxcDur[cxi] = caixo[cxi].durabilidade;
                cxcDest[cxi] = caixo[cxi].destroyed;
            }
            for (int dcari = 0; dcari < destruidoscar.length; dcari++) {
                //dcarX[dcari] = destruidoscar[dcari].x;
                //dcarY[dcari] = destruidoscar[dcari].y;
                //oldDcarX[dcari] = destruidoscar[dcari].oldX;
                //oldDcarY[dcari] = destruidoscar[dcari].oldY;
                dcarDur[dcari] = destruidoscar[dcari].durabilidade;
                dcarExpl[dcari] = destruidoscar[dcari].exploded;
            }

            for (int itensJG2i = 0; itensJG2i < itensJg2.length; itensJG2i++) {
                tipoIJG2[itensJG2i] = itensJg2[itensJG2i].tipo;
                ativoJG2[itensJG2i] = itensJg2[itensJG2i].ativo;
            }

            itAtivo = it.ativo;
            itTipo = it.tipo;
            /*
             * for (int cni = 0; cni < cenas.length; cni++) { cnx[cni] = cenas[cni].x;
             * cny[cni] = cenas[cni].y; cnox[cni] = cenas[cni].oldX; cnoy[cni] =
             * cenas[cni].oldY; }
             */
            textPauseInfo = iMsg.Save[0].texto;
            SaveGame save = new SaveGame();
            save.carro = new Cars(0);
            save.botveloc = botvelo;
            save.botx = botx;
            save.boty = boty;
            save.oldbotx = oldbotx;
            save.oldboty = oldboty;
            /*
             * save.Wallx = wx; save.Wally = wy; save.oldwx = wox; save.oldwy = woy;
             * save.portalx = px; save.portaly = py; save.destportalx = dpx;
             * save.destportaly = dpy;
             */
            save.portalativo = pa;
            /*
             * save.chegadax = cx; save.chegaday = cy; save.oldchegadax = ocx;
             * save.oldchegaday = ocy;
             */
            save.chegadaativa = ca;
            save.janelah = this.janelaH;
            save.janelaw = this.janelaW;
            save.TelaCheia = this.telacheia;
            save.GameBuild = this.verint;
            save.nivel = this.nivel;
            save.GameTipo = this.gameTipo;
            save.LvlAtual = this.lvlAtual;
            save.itensJG2Tipo = tipoIJG2;
            save.itensJG2Ativo = ativoJG2;
            save.MapX = this.mapX;
            save.MapY = this.mapY;
            save.carro.x = this.carro.x;
            save.carro.y = this.carro.y;
            save.carro.corId = this.carro.corId;
            save.carro.idCar = this.carro.idCar;
            save.ModResol = Game.MODRESOL;
            save.PropCar = this.propcar;
            save.exibindoinfom = this.exibindoinfom;
            save.exibindoitem = this.exibindoitem;
            save.ativado = this.ativado;
            save.invisivel = this.invisivel;
            save.tempoinfom = this.tempoinfom;
            save.tempospawn = this.tempospawn;
            save.loopitem = this.loopitem;
            save.tempoativacao = this.tempoativacao;
            save.tempoinvisible = this.tempoinvisible;
            save.frame = this.frame;
            save.itTipo = itTipo;
            save.itAtivo = itAtivo;
            save.quantidade = this.quantidade;
            save.nitroexibido = this.nitroexibido;
            save.exibindovida = this.exibindovida;
            save.cardamage = this.cardamage;
            save.nitro = this.nitro;
            save.tempodano = this.tempodano;
            save.tempovida = this.tempovida;
            save.loopnivel = this.loopnivel;
            save.loopnivelcar = this.loopnivelcar;
            save.nivelcar = this.nivelcar;
            //save.cxcX = cxcX;
            //save.cxcY = cxcY;
            //save.oldCxcX = cxcOldX;
            //save.oldCxcY = cxcOldY;
            save.cxcDestroy = cxcDest;
            save.cxcDurab = cxcDur;
            //save.dcarX = dcarX;
            //save.dcarY = dcarY;
            //save.oldDcarX = oldDcarX;
            //save.oldDcarY = oldDcarY;
            save.dcarDurab = dcarDur;
            save.dcarExplodido = dcarExpl;
            /*
             * save.cenaX = cnx; save.cenaY = cny; save.cenaOldX = cnox; save.cenaOldY =
             * cnoy;
             */

            File pasta = null;
            if (OSystem.contains("linux")) {
                pasta = new File(System.getProperty("user.home") + "/.config/CTG/saves");
            } else if (OSystem.contains("windows")) {
                pasta = new File(System.getProperty("user.home") + "/CTG/saves");
            }
            if (!pasta.exists()) {
                pasta.mkdir();
            }
            File arq = null;
            if (OSystem.contains("linux")) {
                arq = new File(System.getProperty("user.home") + "/.config/CTG/saves/" + SName + ".xsav");
            } else if (OSystem.contains("windows")) {
                arq = new File(System.getProperty("user.home") + "/CTG/saves/" + SName + ".xsav");
            }
            if (arq.exists()) {
                arq.delete();
            }

            arq.createNewFile();
            FileWriter escritor = new FileWriter(arq);
            PrintWriter pw = new PrintWriter(escritor);
            XStream xStream = new XStream();
            xStream.alias("SaveCTG", SaveGame.class);
            String escrita = xStream.toXML(save);
            pw.append(escrita);
            escritor.close();
            pw.close();
            textPauseInfo = iMsg.Save[1].texto;
            noSave = false;

        } catch (IOException ex) {
            if (DEBUGMODE) {
                TA = new TextAttributes(Color.RED);
                DEBUGCONSOLE.setTextAttributes(TA);
                System.out.println(String.format("ERRO | %s", ex.getMessage()));
            }
        }

    }

    public void LoadGamem() {
        pintura = new BufferedImage(janelaW, janelaH, BufferedImage.TYPE_INT_RGB);
        li.setTipoLoad(3, this);
        li.setSaveName("save");
        lit = new Thread(li);
        int fl = 0, rue = 0;
        lit.start();
        do {
            bbg.drawImage(fundoIm, 0, 0, janelaW, janelaH, this);
            bbg.setColor(
                    new Color(Color.LIGHT_GRAY.getRed(), Color.LIGHT_GRAY.getGreen(), Color.LIGHT_GRAY.getBlue(), 127));
            bbg.fillRect(0, 0, janelaW, janelaH);
            ImageIcon iili = new ImageIcon("arquivos/Images/ld/" + fl + ".cli");
            bbg.setColor(new Color(255, 255, 255));
            bbg.fillRoundRect(-5, 35, janelaW + 5, 32, 5, 5);
            bbg.drawImage(iili.getImage(), 5, 35, this);
            bbg.setFont(Fontes.LIBEL.deriveFont(Font.PLAIN, 20));
            bbg.setColor(Color.DARK_GRAY);
            bbg.drawString(iMsg.carregando.texto, iMsg.carregando.posX, 60);
            g.drawImage(backBuffer, 0, 0, this.getWidth(), this.getHeight(), this);
            try {
                Thread.sleep(1000 / FrPS._30);
            } catch (InterruptedException ex) {
                if (DEBUGMODE) {
                    TA = new TextAttributes(Color.RED);
                    DEBUGCONSOLE.setTextAttributes(TA);
                    System.out.println(String.format("ERRO | LDGM | ERRO AO CARREGAR JOGO SALVO %s", ex.toString()));
                }
                System.exit(0);
            }
            fl++;
            rue++;
            if (fl >= 8) {
                fl = 0;
            }
        } while (li.loadCompleto() == false || rue < 30);
        boolean loadsaved = li.loadsaved;
        //bgm.stop();
        lit.stop();
        if (DEBUGMODE) {
            TA = new TextAttributes(Color.CYAN);
            DEBUGCONSOLE.setTextAttributes(TA);
            System.out.println("NCPT | APERTE <T> PARA TROCAR A MÚSICA");
        }
        yep = 1;
        if (!noAnimacao) {
            framesApresen = 0;
            if (loadsaved) {
                showLvlInfo(iMsg.lvlApresenta[1].texto);
            } else {
                showLvlInfo(iMsg.lvlApresenta[0].texto);
            }
        }
        fadeOut();
        sortMus(1);
        game();
    }

    public void SalvarOpcoes() {
        try {
            File arq = null;
            if (OSystem.contains("linux")) {
                arq = new File(System.getProperty("user.home") + "/.config/CTG/ops.ops");
            } else if (OSystem.contains("windows")) {
                arq = new File(System.getProperty("user.home") + "/CTG/ops.ops");
            }
            if (arq.exists()) {
                arq.delete();
            }
            arq.createNewFile();
            FileWriter escritor = new FileWriter(arq);
            PrintWriter pw = new PrintWriter(escritor);
            XStream xstream = new XStream();
            xstream.alias("opcoesCTG", OpcoesVar.class);
            OpcoesVar ops = new OpcoesVar();
            if (FPS == FrPS._90) {
                ops.FPS = 90;
            } else if (FPS == FrPS._60) {
                ops.FPS = 60;
            } else if (FPS == FrPS._75) {
                ops.FPS = 75;
            } else if (FPS == FrPS._45) {
                ops.FPS = 45;
            } else if (FPS == FrPS._30) {
                ops.FPS = 30;
            } else {
                ops.FPS = (short) FPS;
            }
            ops.GameId = gameID + OpcoesEmbut.VER;
            ops.idIdioma = this.iMsg.id;
            ops.resolucaoX = (short) janelaW;
            ops.resolucaoY = (short) janelaH;
            ops.telaCheia = this.telacheia;
            ops.InstrucaoView = this.InstrucoesView;
            ops.antiAliasing = this.antiAliasing;
            ops.masterVolume = (int) (this.masterVolume * 100);
            String escrita = xstream.toXML(ops);
            pw.append(escrita);
            escritor.close();
            pw.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar configuracoes " + ex, "ERRO",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    public void inicializar() {
        InstrucoesView = false;
        String sistemaOper = System.getProperty("os.name");
        String arch = System.getProperty("os.arch");
        OSystem = sistemaOper.toLowerCase();
        if (DEBUGMODE) {
            TA = new TextAttributes(Color.BLUE);
            DEBUGCONSOLE.setTextAttributes(TA);
            if (OSystem.contains("windows")) {
                if (arch.toUpperCase().equals("AMD64")) {
                    arch = "64bits)(amd64";
                }
            }
            System.out.println(String.format("INFO | %s(%s)", OSystem.toUpperCase(), arch));
        }
        tk = Toolkit.getDefaultToolkit();
        tela = tk.getScreenSize();
        if (DEBUGMODE) {
            TA = new TextAttributes(Color.GREEN);
            DEBUGCONSOLE.setTextAttributes(TA);
            System.out.println(String.format("INFO | TELA | TELA W %f     TELA H %f", tela.getWidth(), tela.getHeight()));
        }
        Fontes.loadFonts();
        addMouseListener(this);
        addKeyListener(this);
        addFocusListener(this);
        setTitle("Car The Game");// TITULO DA JANELA
        setSize(janelaW, janelaH);// DIMENSÃ•ES DA JANELA
        setResizable(false);// IMPEDINDO DE REDIMENSIONAR A JANELA
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setVisible(true);
        noAnimacao = yep == 0;
        backBuffer = new BufferedImage(janelaW, janelaH, BufferedImage.TYPE_INT_RGB);// CRIANDO O BUFFER DE IMAGEM
        fundoPause = new BufferedImage(janelaW, janelaH, BufferedImage.TYPE_INT_RGB);
        if (DEBUGMODE) {
            TA = new TextAttributes(Color.GREEN);
            DEBUGCONSOLE.setTextAttributes(TA);
            System.out.println("INFO | FEITO!...");
            System.out.println("INFO | INICIANDO ANIMAÇÃO");
        }
        g = getGraphics();
        bbg = (Graphics2D) backBuffer.getGraphics();
        if (antiAliasing) {
            bbg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (DEBUGMODE) {
                TA = new TextAttributes(Color.ORANGE);
                DEBUGCONSOLE.setTextAttributes(TA);
                System.out.println("INFO | CFG | ANTIALIASING ATIVADO");
            }
        }
        li = new LoadingInicial();
        li.setTipoLoad(0, this);
        lit = new Thread(li);
        lit.start();
        int fl = 0;
        int jw = janelaW, jh = janelaH;
        do {
            bbg.setColor(Color.BLACK);
            bbg.fillRect(0, 0, janelaW, janelaH);
            ImageIcon iili = null;
            iili = new ImageIcon("arquivos/Images/ld/" + fl + ".cli");
            bbg.setColor(new Color(230, 230, 230, 200));
            bbg.fillRect(-5, jh - 45, jw + 5, 32);
            bbg.drawImage(iili.getImage(), jw - 37, jh - 45, this);
            String title1 = "CAR";
            String title2 = "THE GAME";
            String madeBy = "by NCetp";
            String madeBy2 = "Nonaginta Septem Softwares 2014-2020";
            bbg.setColor(Color.BLUE);
            bbg.setFont(Fontes.RUFA.deriveFont(Font.PLAIN, 53));
            int titleW = bbg.getFontMetrics().stringWidth(title1);
            bbg.drawString(title1, (jw / 2) - (titleW / 2), 77);
            bbg.setColor(Color.RED);
            bbg.setFont(Fontes.TIMESBD.deriveFont(Font.BOLD, 13));
            bbg.drawString(title2, ((jw / 2) - (bbg.getFontMetrics().stringWidth(title2) / 2)), 100);
            bbg.setColor(Color.WHITE);
            bbg.setFont(Fontes.LIBEL.deriveFont(Font.BOLD, 11));
            bbg.drawString(madeBy, jw - (bbg.getFontMetrics().stringWidth(madeBy) + 10), jh / 2);
            bbg.setFont(Fontes.LIBEL.deriveFont(Font.PLAIN, 10));
            bbg.drawString(madeBy2, (jw / 2) - (bbg.getFontMetrics().stringWidth(madeBy2) / 2), jh - 3);
            g.drawImage(backBuffer, 0, 0, this.getWidth(), this.getHeight(), this);
            try {
                Thread.sleep(1000 / FrPS._30);
            } catch (InterruptedException ex) {
                if (DEBUGMODE) {
                    TA = new TextAttributes(Color.RED);
                    DEBUGCONSOLE.setTextAttributes(TA);
                    System.out.println(String.format("ERRO | ERRO NA INICIALIZAÇÃO %s", ex.toString()));
                }
                System.exit(0);
            }
            fl++;
            if (fl > 8) {
                fl = 0;
            }
        } while (li.loadCompleto() == false);
        lit.stop();

        if (janelaW < 690 || janelaH < 480) {
            janelaW = 690;
            janelaH = 480;
            Thread lit2 = new Thread(li);
            li.mostrarJOtionPane(true, 0);
            lit2.start();
            do {
                bbg.setColor(Color.BLUE);
                bbg.fillRect(0, 0, janelaW, janelaH);
                ImageIcon iili = null;
                iili = new ImageIcon("arquivos/Images/Loading/" + fl + ".cli");
                bbg.setColor(new Color(255, 255, 255));
                bbg.fillRoundRect(-5, 20, janelaW + 5, 32, 5, 5);
                bbg.drawImage(iili.getImage(), 5, 20, this);
                g.drawImage(backBuffer, 0, 0, this.getWidth(), this.getHeight(), this);
                try {
                    Thread.sleep(1000 / FrPS._30);
                } catch (InterruptedException ex) {
                    TA = new TextAttributes(Color.RED);
                    DEBUGCONSOLE.setTextAttributes(TA);
                    System.out.println(String.format("ERRO | ERRO NA INICIALIZAÇÃO %s", ex.toString()));
                    System.exit(0);
                }
                fl++;
                if (fl >= 8) {
                    fl = 0;
                }
            } while (li.mostrouJOP() == false);

        }
        if (janelaW > tela.getWidth() || janelaH > tela.getHeight()) {
            Thread lit2 = new Thread(li);
            li.mostrarJOtionPane(true, 1);
            lit2.start();
            do {
                bbg.setColor(Color.BLUE);
                bbg.fillRect(0, 0, janelaW, janelaH);
                ImageIcon iili = new ImageIcon("arquivos/Images/Loading/" + fl + ".cli");
                bbg.setColor(new Color(255, 255, 255));
                bbg.fillRoundRect(-5, 20, janelaW + 5, 32, 5, 5);
                bbg.drawImage(iili.getImage(), 5, 20, this);
                bbg.setFont(Fontes.LIBEL.deriveFont(Font.PLAIN, 20));
                bbg.setColor(Color.DARK_GRAY);
                bbg.drawString(iMsg.carregando.texto, iMsg.carregando.posX, 45);
                g.drawImage(backBuffer, 0, 0, this.getWidth(), this.getHeight(), this);
                try {
                    Thread.sleep(1000 / FrPS._30);
                } catch (InterruptedException ex) {
                    TA = new TextAttributes(Color.RED);
                    DEBUGCONSOLE.setTextAttributes(TA);
                    System.out.println(String.format("ERRO | ERRO NA INICIALIZAÇÃO %s", ex.toString()));
                    System.exit(1);
                }
                fl++;
                if (fl >= 8) {
                    fl = 0;
                }
            } while (li.mostrouJOP() == false);
            janelaW = tela.width;
            janelaH = tela.height;
        }
        if (telacheia) {
            // dispose();
            // setUndecorated(true);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice screen = ge.getDefaultScreenDevice();
            if (screen.isFullScreenSupported()) {
                screen.setFullScreenWindow(this);
                this.requestFocus();
                if (DEBUGMODE) {
                    TA = new TextAttributes(Color.GREEN);
                    DEBUGCONSOLE.setTextAttributes(TA);
                    System.out.println("INFO| CFG | O SISTEMA SUPORTA TELA CHEIA NATIVA");
                }
            } else {
                this.setSize(janelaW, janelaH);
                setAlwaysOnTop(true);
                if (DEBUGMODE) {
                    TA = new TextAttributes(Color.YELLOW);
                    DEBUGCONSOLE.setTextAttributes(TA);
                    System.out.println("ALRT | CFG | O SISTEMA NÃO SUPORTA TELA CHEIA NATIVA");
                    System.out.println("ALRT | CFG | USANDO MÉTODO DE TELA CHEIA ALTERNATIVO");
                }
            }
            // setVisible(true);
        } else {
            dispose();
            setUndecorated(false);
            this.setSize(janelaW, janelaH);
            setLocationRelativeTo(null);
            setVisible(true);
            setAlwaysOnTop(false);
        }
        setIconImage(icone);
        backBuffer = new BufferedImage(janelaW, janelaH, BufferedImage.TYPE_INT_RGB);
        this.setLocationRelativeTo(null);
        MODRESOL = janelaH / 720.0;
        com.nonacept.Properties.MODRESOL = MODRESOL;
        if (DEBUGMODE) {
            TA = new TextAttributes(Color.ORANGE);
            DEBUGCONSOLE.setTextAttributes(TA);
            System.out.println(String.format("\nINFO | CFG | RESOLUTION %dL(W)   (H)A%d", janelaW, janelaH));
            System.out.println(String.format("INFO | CFG | MODRESOL %f\n", MODRESOL));
        }

        FrPS.calcTicks(MODRESOL);
        switch (FPS) {
            case 90:
                this.FPS = FrPS._90;
                break;
            case 75:
                this.FPS = FrPS._75;
                break;
            case 60:
                this.FPS = FrPS._60;
                break;
            case 45:
                this.FPS = FrPS._45;
                break;
            case 30:
                this.FPS = FrPS._30;
                break;
        }

        SIZES.calcular(janelaW, janelaH);
        g = getGraphics();
        bbg = (Graphics2D) backBuffer.getGraphics();
        if (antiAliasing) {
            bbg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (DEBUGMODE) {
                TA = new TextAttributes(Color.ORANGE);
                DEBUGCONSOLE.setTextAttributes(TA);
                System.out.println("INFO | CFG | ANTIALIASING ATIVADO!");
            }
        }
        masterVolume = masterVolume / 100.0;
        player = new Player();
        masterVolFloat = (float) (Math.log(masterVolume) / Math.log(10) * 20);
        player.setVol(masterVolFloat);
        /**
         * Pula direto ao jogo
         */
        requestFocus();
        if (directGame) {
            yep = 1;
            antgame();
        } else if (noAnimacao) {
            player.play(sSDiretory.getPath() + "/menu/menu1.ctgm");
            menuEsquecido = true;
            mainMenu();
        } else {
            antanim();
        }
    }

    public String telaatual = "null";

    public int yep = -1, nxtyep = 0;
    public ImageIcon[] rastroSpriteIc = new ImageIcon[10];
    public Image[] rastroSprite = new Image[10];
    public int[] rastrox = new int[10], rastroy = new int[10];
    public int[] rastromx = new int[10], rastromy = new int[10];
    //@todo exclui isso aqui, por enquanto padrão é -16

    private void antanim() {

        new AnimacaoIntro().start(this);
        fadeOut();
        if (DEBUGMODE) {
            TA = new TextAttributes(Color.CYAN);
            DEBUGCONSOLE.setTextAttributes(TA);
            System.out.println("NCPT | PRESSIONE <ENTER> PARA IR DIRETO AO MENU");
        }
        telaatual = "anim";
        player.play(sSDiretory.getPath() + "/menu/menu1.ctgm");

        menuEsquecido = true;
        double cois = 0.0;
        int corA = 255;
        int corV = 255 - corA;
        int valor = 300 / FPS;
        int valor2 = (int) (FPS / 10);
        boolean azulando = false;
        int i = 0;
        //
        long tmpInicial = System.currentTimeMillis();

        do {
            bbg.setColor(Color.black);
            bbg.fillRect(0, 0, janelaW, janelaH);
            bbg.setColor(new Color(corV, 0, corA));
            bbg.setFont(Fontes.RUFA.deriveFont(Font.PLAIN, SIZES._60));
            int gameTitleWidth = bbg.getFontMetrics().stringWidth("CAR");
            bbg.drawString("CAR", ((janelaW / 2) - (gameTitleWidth / 2)), SIZES._100);
            bbg.setColor(new Color(corA, 0, corV));
            bbg.setFont(Fontes.TIMESBD.deriveFont(Font.BOLD, SIZES._15));
            bbg.drawString("THE GAME", ((janelaW / 2) - (bbg.getFontMetrics().stringWidth("THE GAME") / 2)), SIZES._124);
            bbg.setColor(Color.black);
            bbg.fillRect(((janelaW / 2) - SIZES._95), (int) (SIZES._50 + cois), SIZES._200, SIZES._200);
            bbg.setColor(Color.white);
            bbg.setFont(Fontes.LIBEL.deriveFont(Font.BOLD, SIZES._18));
            bbg.drawString(ver, 10, ((janelaH) - 13));
            bbg.drawString("NonaCept", ((janelaW) - (bbg.getFontMetrics().stringWidth("NonaCept") + 10)), ((janelaH) - 13));
            g.drawImage(backBuffer, 0, 0, this.getWidth(), this.getHeight(), this);
            try {
                Thread.sleep(1000 / FPS);
            } catch (InterruptedException ex) {
            }
            if ((System.currentTimeMillis() - tmpInicial) > 13700) {
                azulando = true;
            }
            // cois += 30.0/FPS;
            if (i % valor2 == 0) {
                cois += SIZES._1;
            }
            if (azulando) {
                corA += valor;
            } else {
                corA -= valor;
            }
            if (corA > 255) {
                corA = 255;
                azulando = false;
            } else if (corA < 0) {
                corA = 0;
                azulando = true;
            }
            corV = 255 - corA;
            i++;
        } while ((System.currentTimeMillis() - tmpInicial) < 14561 && yep != 0);
        yep = 0;
        menuEsquecido = true;
        mainMenu();
    }
    //public OldPlayer bgm = new OldPlayer();
    public int opmenu = 1;

    public int seg1 = 1, seg2 = 0;
    public boolean infovoltando = false;
    public boolean albumArtVoltando = false;
    public int timeInfo = 0;

    private void mainMenu() {
        opmenu = 1;
        int reducao = 0;
        boolean reduzindo = true;
        if (telaatual.equals("pause") || telaatual.equals("gameover") || telaatual.equals("preta")) {
            if (DEBUGMODE == true) {
                TA = new TextAttributes(Color.CYAN);
                DEBUGCONSOLE.setTextAttributes(TA);
                System.out.println("NCPT | VAMOS TOCAR UM SOM");
            }
            sortMus(0);
        }
        informacoesmusica(player.getFileName());
        for (int o = 0; o <= 5; o++) {
            if (o % 2 == 0) {
                bbg.setColor(Color.WHITE);
            } else {
                bbg.setColor(Color.BLACK);
            }
            if (o == 4) {
                bbg.drawImage(fundoIm, 0, 0, janelaW, janelaH, Color.black, this);
            }
            bbg.fillRect(0, 0, janelaW, janelaH);
            g.drawImage(backBuffer, 0, 0, this.getWidth(), this.getHeight(), this);
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
            }
        }
        if (DEBUGMODE == true) {
            TA = new TextAttributes(Color.CYAN);
            DEBUGCONSOLE.setTextAttributes(TA);
            System.out.println("NCPT | ESTAMOS NO MENU");
        }
        telaatual = "menu";
        menuSair = false;
        menuStart = false;
        boolean azulando = true;
        boolean azul = false;
        int ctgBlue = 220;
        int ctgRed = 35;
        int incremento = 255 / (FPS);
        do {
            bbg.setColor(Color.BLACK);
            bbg.fillRect(0, 0, janelaW, janelaH);
            bbg.drawImage(fundoIm, 0, 0, janelaW, janelaH, this);
            if (alinhandotextos) {
                bbg.setColor(Color.WHITE);
                bbg.drawLine(janelaW / 2, 0, janelaW / 2, janelaH);
            }
            bbg.setColor(new Color(ctgRed, 0, ctgBlue));// AZUL
            bbg.setFont(Fontes.RUFA.deriveFont(Font.PLAIN, SIZES._60));
            int gameTitleWidth = bbg.getFontMetrics().stringWidth("CAR");
            bbg.drawString("CAR", ((janelaW / 2) - (gameTitleWidth / 2)), SIZES._100);
            bbg.setColor(new Color(ctgBlue, 0, ctgRed));// VERMELHO
            bbg.setFont(Fontes.TIMESBD.deriveFont(Font.BOLD, SIZES._15));
            bbg.drawString("THE GAME", ((janelaW / 2) - (bbg.getFontMetrics().stringWidth("THE GAME") / 2)), SIZES._124);
            bbg.setColor(Color.RED);// VERMELHO
            bbg.setFont(Fontes.ARIAL.deriveFont(Font.BOLD, SIZES._16));
            if (opmenu == 0) { // credits
                bbg.drawString(iMsg.creditos.texto,
                        ((janelaW / 2) - (iMsg.creditos.getLargura(bbg.getFontMetrics()) / 2)), SIZES._170);
                bbg.setColor(new Color(255, 0, 0, 127));
                bbg.setFont(Fontes.ARIAL.deriveFont(Font.BOLD, SIZES._15));
                bbg.drawString(iMsg.iniciar.texto,
                        ((janelaW / 2) - (iMsg.iniciar.getLargura(bbg.getFontMetrics()) / 2) + SIZES._75), SIZES._172);
                bbg.setColor(new Color(255, 0, 0, 100));
                bbg.setFont(Fontes.ARIAL.deriveFont(Font.BOLD, SIZES._14));
                bbg.drawString(iMsg.opcoes.texto,
                        ((janelaW / 2) - (iMsg.opcoes.getLargura(bbg.getFontMetrics()) / 2) + SIZES._150), SIZES._174);
                bbg.setColor(new Color(255, 0, 0, 50));
                bbg.setFont(Fontes.ARIAL.deriveFont(Font.BOLD, SIZES._13));
                bbg.drawString(iMsg.sair.texto,
                        ((janelaW / 2) - (iMsg.sair.getLargura(bbg.getFontMetrics()) / 2) + SIZES._225), SIZES._176);
            }
            if (opmenu == 1) { // Start
                bbg.drawString(iMsg.iniciar.texto,
                        ((janelaW / 2) - (iMsg.iniciar.getLargura(bbg.getFontMetrics()) / 2)), SIZES._170);
                bbg.setColor(new Color(255, 0, 0, 127));
                bbg.setFont(Fontes.ARIAL.deriveFont(Font.BOLD, SIZES._15));
                bbg.drawString(iMsg.creditos.texto,
                        ((janelaW / 2) - (iMsg.creditos.getLargura(bbg.getFontMetrics()) / 2) - SIZES._75), SIZES._172);
                bbg.drawString(iMsg.opcoes.texto,
                        ((janelaW / 2) - (iMsg.opcoes.getLargura(bbg.getFontMetrics()) / 2) + SIZES._75), SIZES._172);
                bbg.setColor(new Color(255, 0, 0, 100));
                bbg.setFont(Fontes.ARIAL.deriveFont(Font.BOLD, SIZES._14));
                bbg.drawString(iMsg.sair.texto,
                        ((janelaW / 2) - (iMsg.sair.getLargura(bbg.getFontMetrics()) / 2) + SIZES._150), SIZES._174);
                if (menuStart) {
                    switch (menuStartOp) {
                        case 0:
                            bbg.setFont(Fontes.ARIAL.deriveFont(Font.BOLD, SIZES._16));
                            bbg.setColor(new Color(255, 0, 0));
                            bbg.drawString(iMsg.novoJogo.texto,
                                    (janelaW / 2) - (iMsg.novoJogo.getLargura(bbg.getFontMetrics()) / 2), SIZES._202);
                            bbg.setFont(Fontes.ARIAL.deriveFont(Font.BOLD, SIZES._14));
                            bbg.setColor(new Color(255, 0, 0, 75));
                            bbg.drawString(iMsg.loadJogo.texto,
                                    (janelaW / 2) - (iMsg.loadJogo.getLargura(bbg.getFontMetrics()) / 2), SIZES._223);
                            break;
                        case 1:
                            bbg.setFont(Fontes.ARIAL.deriveFont(Font.BOLD, SIZES._14));
                            bbg.setColor(new Color(255, 0, 0, 75));
                            bbg.drawString(iMsg.novoJogo.texto,
                                    (janelaW / 2) - (iMsg.novoJogo.getLargura(bbg.getFontMetrics()) / 2), SIZES._202);
                            bbg.setFont(Fontes.ARIAL.deriveFont(Font.BOLD, SIZES._16));
                            if (noSave) {
                                bbg.setColor(new Color(255, 0, 0, 127));
                            } else {
                                bbg.setColor(new Color(255, 0, 0));
                            }
                            bbg.drawString(iMsg.loadJogo.texto,
                                    (janelaW / 2) - (iMsg.loadJogo.getLargura(bbg.getFontMetrics()) / 2), SIZES._223);
                            break;
                    }
                }

            }
            if (opmenu == 2) { // options
                bbg.drawString(iMsg.opcoes.texto, ((janelaW / 2) - (iMsg.opcoes.getLargura(bbg.getFontMetrics()) / 2)),
                        SIZES._170);
                bbg.setColor(new Color(255, 0, 0, 127));
                bbg.setFont(Fontes.ARIAL.deriveFont(Font.BOLD, SIZES._15));
                bbg.drawString(iMsg.iniciar.texto,
                        ((janelaW / 2) - (iMsg.iniciar.getLargura(bbg.getFontMetrics()) / 2) - SIZES._75), SIZES._172);
                bbg.drawString(iMsg.sair.texto, ((janelaW / 2) - (iMsg.sair.getLargura(bbg.getFontMetrics()) / 2) + SIZES._75),
                        SIZES._172);
                bbg.setColor(new Color(255, 0, 0, 100));
                bbg.setFont(Fontes.ARIAL.deriveFont(Font.BOLD, SIZES._14));
                bbg.drawString(iMsg.creditos.texto,
                        ((janelaW / 2) - (iMsg.creditos.getLargura(bbg.getFontMetrics()) / 2) - SIZES._150), SIZES._174);
            }
            if (opmenu == 3) { // exit
                bbg.drawString(iMsg.sair.texto, ((janelaW / 2) - (iMsg.sair.getLargura(bbg.getFontMetrics()) / 2)),
                        SIZES._170);
                bbg.setColor(new Color(255, 0, 0, 127));
                bbg.setFont(Fontes.ARIAL.deriveFont(Font.BOLD, SIZES._15));
                bbg.drawString(iMsg.opcoes.texto,
                        ((janelaW / 2) - (iMsg.opcoes.getLargura(bbg.getFontMetrics()) / 2) - SIZES._75), SIZES._172);
                bbg.setColor(new Color(255, 0, 0, 100));
                bbg.setFont(Fontes.ARIAL.deriveFont(Font.BOLD, SIZES._14));
                bbg.drawString(iMsg.iniciar.texto,
                        ((janelaW / 2) - (iMsg.iniciar.getLargura(bbg.getFontMetrics()) / 2) - SIZES._150), SIZES._174);
                bbg.setColor(new Color(255, 0, 0, 50));
                bbg.setFont(Fontes.ARIAL.deriveFont(Font.BOLD, SIZES._13));
                bbg.drawString(iMsg.creditos.texto,
                        ((janelaW / 2) - (iMsg.creditos.getLargura(bbg.getFontMetrics()) / 2) - SIZES._225), SIZES._176);
                if (menuSair) {
                    switch (menuSairOp) {
                        case 0:
                            bbg.setFont(Fontes.ARIAL.deriveFont(Font.BOLD, SIZES._16));
                            bbg.setColor(new Color(255, 0, 0));
                            bbg.drawString(iMsg.nao.texto, (janelaW / 2) - (iMsg.nao.getLargura(bbg.getFontMetrics()) / 2),
                                    SIZES._202);
                            bbg.setFont(Fontes.ARIAL.deriveFont(Font.BOLD, SIZES._14));
                            bbg.setColor(new Color(255, 0, 0, 75));
                            bbg.drawString(iMsg.sim.texto, (janelaW / 2) - (iMsg.sim.getLargura(bbg.getFontMetrics()) / 2),
                                    SIZES._223);
                            break;
                        case 1:
                            bbg.setFont(Fontes.ARIAL.deriveFont(Font.BOLD, SIZES._14));
                            bbg.setColor(new Color(255, 0, 0, 75));
                            bbg.drawString(iMsg.nao.texto, (janelaW / 2) - (iMsg.nao.getLargura(bbg.getFontMetrics()) / 2),
                                    SIZES._202);
                            bbg.setFont(Fontes.ARIAL.deriveFont(Font.BOLD, SIZES._16));
                            bbg.setColor(new Color(255, 0, 0));
                            bbg.drawString(iMsg.sim.texto, (janelaW / 2) - (iMsg.sim.getLargura(bbg.getFontMetrics()) / 2),
                                    SIZES._223);
                            break;

                    }
                }
            }
            if (azulando) {
                if (ctgBlue < 255 && ctgRed > 0) {
                    ctgBlue += incremento;
                    ctgRed -= incremento;
                }
                if (ctgBlue >= 255) {
                    ctgBlue = 255;
                    ctgRed = 0;
                    azulando = false;
                    azul = true;
                }
            } else {
                if (ctgBlue > 0 && ctgRed < 255 && (!azul)) {
                    ctgBlue -= incremento;
                    ctgRed += incremento;
                }
                if (ctgBlue <= 0) {
                    ctgBlue = 0;
                    ctgRed = 255;
                    azulando = true;
                }
            }
            if (((framesmenu % (FPS * 5.5)) == 0) && azul) {
                azul = false;
            }
            bbg.setColor(Color.white);
            bbg.setFont(Fontes.LIBEL.deriveFont(Font.BOLD, SIZES._18));
            bbg.drawString(ver, 11, ((janelaH) - 13));
            int rd = reducao / 2;
            bbg.drawImage(comemoracao, ((janelaW / 2) - 150) + rd, ((janelaH / 2) - 50) + rd, 300 - reducao,
                    300 - reducao, this);
            if (exibindoinfom) {
                bbg.setColor(new Color(33, 33, 33, 133));
                bbg.fillRoundRect(((janelaW) - 276 + (300 - seg1)), ((janelaH - 82)), 265, 70, 3, 3);
                bbg.drawImage(AlbumCover, ((janelaW) - 271) + (30 - (seg2 / 2)), (janelaH - 77) + (30 - (seg2 / 2)),
                        60 - (60 - seg2), 60 - (60 - seg2), this);
                bbg.setColor(new Color(200, 200, 200, 127));
                bbg.setFont(Fontes.TIMES.deriveFont(Font.PLAIN, 10));
                bbg.drawString(iMsg.trilhaSom.texto, (janelaW - 173 + (300 - seg1)), (janelaH - 14));
                bbg.setColor(fonte);
                bbg.setFont(Fontes.UBUNTU.deriveFont(Font.BOLD, 17));
                bbg.drawString(nomemus, ((janelaW) - 206 + (300 - seg1)), ((janelaH - 67)));
                bbg.setFont(Fontes.UBUNTU.deriveFont(Font.PLAIN, 15));
                bbg.drawString(artimus, ((janelaW) - 206 + (300 - seg1)), ((janelaH - 47)));
                bbg.drawString(albumus, ((janelaW) - 206 + (300 - seg1)), ((janelaH - 27)));
                timeInfo++;
            }
            g.drawImage(backBuffer, 0, 0, this.getWidth(), this.getHeight(), this);
            if (player.isComplete()) {
                sortMus(0);
            }
            try {
                Thread.sleep(1000 / FPS);
            } catch (InterruptedException ex) {
            }
            framesmenu++;
            if (!infovoltando) {
                seg1 += 6;
            } else {
                if (!albumArtVoltando) {
                    seg2 += 4;
                }
            }
            if (seg1 >= 310) {
                infovoltando = true;
            }
            if (seg2 >= 65) {
                albumArtVoltando = true;
            }
            if (albumArtVoltando && seg2 > 60) {
                seg2--;
            }
            if (infovoltando && seg1 > 300) {
                seg1 -= 2;
            }
            if ((framesmenu % 3) == 0) {
                if (reduzindo) {
                    reducao -= 2;
                } else {
                    reducao += 2;
                }
            }
            if (reducao <= (-20)) {
                reduzindo = false;
            }
            if (reducao >= 5) {
                reduzindo = true;
            }
            if (timeInfo > 600) {
                if (seg2 > 0) {
                    seg2 -= 4;
                }
                if (seg2 <= 0) {
                    seg1 -= 6;
                }
                if (seg1 <= 0) {
                    exibindoinfom = false;
                }
            }
            if (player.isComplete() && menuEsquecido) {
                yep = -1;
            }
        } while (yep == 0);
        if (yep != -100) {
            fadeOut();
        }

        if (yep == -1) {
            player.stop();
            antanim();
        }
        if (yep == 1) {
            antgame();
        }
        if (yep == 3) {
            creditos();
        }
        if (yep == -5) {
            telaOpcoes();
        }
        if (yep == 5) {
        }
        if (yep == 7) {
            LoadGamem();
        }
        if (yep == -100) {
            fecharGame();
        }
        if (yep == 8) {
            viewTutorial();
        }

    }

    Thread thOp;
    int currentFPS;

    public void telaOpcoes() {
        telaatual = "opcoes";
        SalvarOpcoes();
        pintura = new BufferedImage(janelaW, janelaH, BufferedImage.TYPE_INT_RGB);
        bbg2 = (Graphics2D) pintura.getGraphics();
        op = new OpcoesEmbut(this);
        thOp = new Thread(op);
        thOp.start();
        do {
            g.drawImage(pintura, 0, 0, this.getWidth(), this.getHeight(), this);
            try {
                Thread.sleep(1000 / FPS);
            } catch (InterruptedException ex) {
            }
        } while (yep == -5);
        thOp.stop();
        menuEsquecido = true;
        mainMenu();

    }

    public int framesmenu = 0;
    public String nomemus = "", artimus = "", albumus = "";
    public Image AlbumCover;

    private void informacoesmusica(String name) {
        try {
            String nome = name.replace(".ctgm", "");
            File info = new File(Infosm + "/" + nome + ".ctgmi");
            InputStream opa = new FileInputStream(info);
            Charset cs = Charset.forName("UTF-8"); // a codificaÃ§Ã£o do texto Ã© UTF8
            InputStreamReader entrada = new InputStreamReader(opa, cs);
            BufferedReader buffer = new BufferedReader(entrada);
            String linha = buffer.readLine();
            StringTokenizer entrar = new StringTokenizer(linha, ";");
            nomemus = entrar.nextToken();
            artimus = entrar.nextToken();
            albumus = entrar.nextToken();

            ImageIcon CoverAl = new ImageIcon(Infosm + "/" + nome + ".cii");
            AlbumCover = CoverAl.getImage();

            if (DEBUGMODE) {
                TA = new TextAttributes(Color.GREEN);
                DEBUGCONSOLE.setTextAttributes(TA);
                System.out.println(String.format("INFO | EXIBIR AGORA: %s (%s)", info.getName(), nomemus));
            }

        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
        if (yep == 0) {
            seg1 = 1;
            seg2 = 0;
            infovoltando = false;
            albumArtVoltando = false;
            timeInfo = 0;
            if (DEBUGMODE) {
                TA = new TextAttributes(Color.GREEN);
                DEBUGCONSOLE.setTextAttributes(TA);
                System.out.println("INFO | RESETANDO TELA DE INFORMAÇÕES DO MENU");
            }
        }
        exibindoinfom = true;
        tempoinfom = 0;
    }

    public Resolutions resol;

    private void antgame() {
        try {
            pintura = new BufferedImage(janelaW, janelaH, BufferedImage.TYPE_INT_RGB);
            li.setTipoLoad(1, this);
            li.setLvl(0);
            lit = new Thread(li);
            int fl = 0, rue = 0;
            lit.start();
            do {
                bbg.drawImage(fundoIm, 0, 0, janelaW, janelaH, this);
                bbg.setColor(new Color(Color.LIGHT_GRAY.getRed(), Color.LIGHT_GRAY.getGreen(),
                        Color.LIGHT_GRAY.getBlue(), 127));
                bbg.fillRect(0, 0, janelaW, janelaH);
                ImageIcon iili = new ImageIcon("arquivos/Images/ld/" + fl + ".cli");
                bbg.setColor(new Color(255, 255, 255));
                bbg.fillRoundRect(-5, 35, janelaW + 5, 32, 5, 5);
                bbg.drawImage(iili.getImage(), 5, 35, this);
                bbg.setFont(Fontes.LIBEL.deriveFont(Font.PLAIN, 20));
                bbg.setColor(Color.DARK_GRAY);
                bbg.drawString(iMsg.carregando.texto, iMsg.carregando.posX, 60);
                g.drawImage(backBuffer, 0, 0, this.getWidth(), this.getHeight(), this);
                try {
                    Thread.sleep(1000 / FrPS._30);
                } catch (InterruptedException ex) {
                    if (DEBUGMODE) {
                        TA = new TextAttributes(Color.RED);
                        DEBUGCONSOLE.setTextAttributes(TA);
                        System.out.println("Deu Ruim!");
                    }
                    System.exit(0);
                }
                fl++;
                rue++;
                if (fl >= 8) {
                    fl = 0;
                }
            } while (li.loadCompleto() == false || rue < 30);
            lit.stop();
            //if (bgm.isPlaying()) {
            //    bgm.stop();
            //}
            //eita.stop();
            if (DEBUGMODE) {
                TA = new TextAttributes(Color.CYAN);
                DEBUGCONSOLE.setTextAttributes(TA);
                System.out.println("NCPT | PRESSIONE <T> PARA TROCAR A MÚSICA");
            }
        } catch (Exception ex) {
            if (DEBUGMODE) {
                TA = new TextAttributes(Color.RED);
                DEBUGCONSOLE.setTextAttributes(TA);
                System.out.println("ERRO | **" + ex.getLocalizedMessage() + "\n" + ex.getMessage() + "**");
            }
        }
        for (int i = 0; i < inimigos.length; i++) {
            inimigos[i].fx = inimigos[i].x;
            inimigos[i].fy = inimigos[i].y;
        }
        if (!noAnimacao) {
            framesApresen = 0;
            showLvlInfo(iMsg.lvlApresenta[0].texto);
        }
        fadeOut();
        sortMus(1);
        game();
    }

    public List musics, musicsMenu;
    int musicaanterior, musicaanteriorMenu;

    public void sortMus(int orgm) {
        jseg1 = 0;
        jseg2 = 0;
        jalbumArtVoltando = false;
        jinfovoltando = false;
        jtimeInfo = 0;
        /*if (eita != null) {
            eita.stop();
        }*/
        //bgm.stop();
        player.stop();
        if (orgm == 1) {// JOGO
            if (musicFiles.length > 0) {
                int musica;
                do {
                    Collections.shuffle(musics);
                    musica = (Integer) musics.get(0);
                } while (musica == musicaanterior && musicFiles.length > 1);
                player.play(musicFiles[musica]);
                //at.setUrl(musicasd[musica].getPath(), orgm);
                //eita = new Thread(at);
                //eita.start();
                /*bgm.setUrl(musicasd[musica].getPath(), orgm);
            bgm.play();*/
                informacoesmusica(musicFiles[musica].getName());
                musics.remove(0);
                if (musics.isEmpty()) {
                    for (int i = 0; i < countmus; i++) {
                        musics.add(i);
                    }
                }
                musicaanterior = musica;
            }
        }
        if (orgm == 0) {// MENU
            int musica;
            do {
                Collections.shuffle(musicsMenu);
                musica = (Integer) musicsMenu.get(0);
            } while (musica == musicaanteriorMenu);
            player.play(musicasMD[musica]);
            //at.setUrl(musicasMD[musica].getPath(), orgm);
            //eita = new Thread(at);
            //eita.start();
            /*bgm.setUrl(musicasMD[musica].getPath(), orgm);
            bgm.play();*/
            informacoesmusica(musicasMD[musica].getName());
            musicsMenu.remove(0);
            if (musicsMenu.isEmpty()) {
                for (int i = 0; i < countmenumus; i++) {
                    musicsMenu.add(i);
                }
            }
            musicaanteriorMenu = musica;
        }
    }

    public String informacoes;

    public int loopnivel = 1, loopnivelcar = 1, nivelcar = 0;

    public Color fonte = Color.white;
    public Color fundo = Color.black;
    public int nivel = 0;
    public boolean exibindoinfom, exibindoitem, ativado, invisivel;
    public int tempoinfom, tempospawn, loopitem, tempoativacao;
    public Bots inimigos[] = new Bots[1000];
    public Itens it = new Itens();
    public double propcar = 0;
    public int tempoinvisible;

    public void verificarPegarItem2() {
        for (int i = 0; i < itensJg2.length; i++) {
            if (itensJg2[i].ativo) {
                boolean pegou = false;
                if (carro.x <= (itensJg2[i].Xi + SIZES._16) && carro.x >= (itensJg2[i].Xi - SIZES._16)
                        && carro.y <= (itensJg2[i].Yi + SIZES._16) && carro.y >= (itensJg2[i].Yi - SIZES._16)) {
                    pegou = true;
                }
                if (pegou) {
                    switch (itensJg2[i].tipo) {
                        case 1:
                            nitro += 13.5;
                            itensJg2[i].ativo = false;
                            if (nitro > 100) {
                                nitro = 100;
                            }
                            break;
                        case 2:
                            nitro += 35;
                            itensJg2[i].ativo = false;
                            if (nitro > 100) {
                                nitro = 100;
                            }
                            break;
                        case 3:
                            tempovida = 500;
                            cardamage -= 17.5;
                            itensJg2[i].ativo = false;
                            if (cardamage < 0) {
                                cardamage = 0;
                            }
                            break;
                        case 4:
                            tempoinvisible = 250;
                            cardamage -= 4.5;
                            invisivel = true;
                            itensJg2[i].ativo = false;
                            if (cardamage < 0) {
                                cardamage = 0;
                            }
                    }
                }
            }
        }
    }

    public void verificapegaritem() {
        if (exibindoitem) {
            boolean pegou = false;
            if (it.Xi >= (carro.x - SIZES._16) && it.Xi <= (carro.x + SIZES._16)) {
                if (it.Yi >= (carro.y - SIZES._16) && it.Yi <= (carro.y + SIZES._16)) {
                    pegou = true;
                }
            }
            if (pegou) {
                loopitem = -1;
                if (it.tipo == 1) {
                    nitro += 13.5;
                    exibindoitem = false;
                    if (nitro >= 100) {
                        nitro = 100;
                    }
                }
                if (it.tipo == 2) {
                    nitro += 35;
                    exibindoitem = false;
                    if (nitro >= 100) {
                        nitro = 100;
                    }
                }
                if (it.tipo == 3) {
                    tempovida = 500;
                    cardamage -= 17.5;
                    exibindoitem = false;
                    if (cardamage < 0) {
                        cardamage = 0;
                    }
                }
                if (it.tipo == 4) {
                    if (tempoinvisible < 250) {
                        tempoinvisible = 250;
                    }
                    exibindoitem = false;
                    cardamage -= 4.5;
                    invisivel = true;
                    if (cardamage < 0) {
                        cardamage = 0;
                    }
                }
            }
        }
    }

    public int FPS;
    public Graphics g;
    public Graphics2D bbg, bbg2;// <- COM bbg DESENHARÃ� NO BUFFER

    private void game() {
        GameThread gmT = new GameThread(this);
        Thread runGame = new Thread(gmT);
        if (DEBUGMODE) {
            TA = new TextAttributes(Color.CYAN);
            DEBUGCONSOLE.setTextAttributes(TA);
            System.out.println("NCPT | LET\'S PLAY");
        }
        bbg2 = (Graphics2D) pintura.getGraphics();
        if (antiAliasing) {
            bbg2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        telaatual = "game";
        irdireita = false;
        irdireita2 = false;
        iresquerda = false;
        iresquerda2 = false;
        ircima = false;
        ircima2 = false;
        irbaixo = false;
        irbaixo2 = false;
        ativado = false;
        gmT.setTipo(gameTipo);
        runGame.start();

        do {
            g.drawImage(pintura, 0, 0, this.getWidth(), this.getHeight(), this);
            try {
                Thread.sleep(1000 / FPS);
            } catch (InterruptedException ex) {
                System.exit(1);
            }
        } while (yep == 1);
        runGame.stop();
        if (yep == 0) {
            menuEsquecido = true;
            mainMenu();
        }
        if (yep == 2) {
            fundoPause = pintura;
            menupause();
        }
        if (yep == 4) {
            gameOver(GOMotivo);
        }
        if (yep == 6) {
            nextLvl();
        }
    }

    public int reqInimigos, reqNivel;

    public void verificaRequisitos() {
        if (gameTipo == 1) {
            if (quantidade >= reqInimigos && nivel >= reqNivel) {
                for (int i = 0; i < chegadas.length; i++) {
                    chegadas[i].ativo = true;
                }
            }
        }
    }

    private int contar;

    private void gameOver(int motivo) {
        if (DEBUGMODE) {
            TA = new TextAttributes(Color.CYAN);
            DEBUGCONSOLE.setTextAttributes(TA);
            System.out.println("NCPT | PARECE QUE VOCÊ PERDEU");
        }

        player.stop();
        //bgm.stop();

        telaatual = "esperando";
        bbg.setColor(fundo);
        bbg.fillRect(0, 0, janelaW, janelaH);
        contar = 0;
        telaatual = "gameover";
        do {
            bbg.setColor(new Color(255, 0, 0, 10));
            bbg.setFont(Fontes.LIBEL.deriveFont(Font.BOLD, 36));
            bbg.drawString("GAME OVER", ((janelaW / 2) - (bbg.getFontMetrics().stringWidth("GAME OVER") / 2)), 170);
            if (contar >= 225) {
                if (motivo == 100) {
                    bbg.setColor(new Color(255, 255, 255, 5));
                    bbg.setFont(Fontes.LIBEL.deriveFont(Font.PLAIN, 14));
                    bbg.drawString(iMsg.danoTotal.texto,
                            ((janelaW / 2) - (bbg.getFontMetrics().stringWidth(iMsg.danoTotal.texto) / 2)), 230);
                }
                if (motivo == 200) {
                    bbg.setColor(new Color(255, 255, 255, 5));
                    bbg.setFont(Fontes.LIBEL.deriveFont(Font.PLAIN, 14));
                    bbg.drawString(iMsg.atraveParede.texto,
                            ((janelaW / 2) - (bbg.getFontMetrics().stringWidth(iMsg.atraveParede.texto) / 2)), 230);
                }
            }
            if (contar >= 370) {
                bbg.setFont(Fontes.ARIAL.deriveFont(Font.BOLD, 12));
                bbg.drawString(iMsg.inimigos.texto + quantidade + iMsg.nivel2.texto + nivel,
                        (janelaW / 2) - (bbg.getFontMetrics()
                                .stringWidth(iMsg.inimigos.texto + quantidade + iMsg.nivel2.texto + nivel) / 2),
                        300);
            }
            contar++;

            try {
                Thread.sleep(1000 / FrPS._90);
            } catch (InterruptedException ex) {

            }
            g.drawImage(backBuffer, 0, 0, this.getWidth(), this.getHeight(), this);
        } while (yep == 4);
        fadeOut();
        if (yep == 0) {
            menuEsquecido = true;
            mainMenu();
        }
    }

    public int quantidade, nitroexibido, exibindovida;
    public double cardamage = 0, nitro = 0;
    public int tempodano = 0;
    public int tempovida = 0;

    public void addBots() {
    }

    public int frame = 0;

    public void andaBot() {
        if (invisivel) {
        } else {

            double modVelo = nivel * 0.10;
            for (int i = 0; i < quantidade; i++) {
                inimigos[i].oldX = inimigos[i].x;
                inimigos[i].oldY = inimigos[i].y;

                if (inimigos[i].getX() < carro.x) {
                    inimigos[i].x += (1.0 + modVelo);
                }
                if (inimigos[i].getX() > carro.x) {
                    inimigos[i].x -= (1.0 + modVelo);
                }
                if (inimigos[i].getY() < carro.y) {
                    inimigos[i].y += (1.0 + modVelo);
                }
                if (inimigos[i].getY() > carro.y) {
                    inimigos[i].y -= (1.0 + modVelo);
                }

                colisaobots();
                // colisão objetos
                if (gameTipo == 1) {
                    if (inimigos[i].x <= 0) {
                        inimigos[i].x = 0;
                    }
                    if (inimigos[i].y <= 0) {
                        inimigos[i].y = 0;
                    }
                    if (inimigos[i].y >= resol.diferenca2) {
                        inimigos[i].y = (short) resol.diferenca2;
                    }
                    if (inimigos[i].x >= resol.tamX2) {
                        inimigos[i].x = (short) resol.tamX2;
                    }
                    for (int j = 0; j < wall.length; j++) {
                        int difx, dify, difposx, difposy;
                        difx = inimigos[i].x - wall[j].x;
                        dify = inimigos[i].y - wall[j].y;
                        if (dify < 0) {
                            difposy = dify * -1;
                        } else {
                            difposy = dify;
                        }
                        if (difx < 0) {
                            difposx = difx * -1;
                        } else {
                            difposx = difx;
                        }
                        if ((difx > -SIZES._16) && (difx < SIZES._16) && (dify > -SIZES._16) && (dify < SIZES._16)) {
                            if (difposx > difposy) {
                                if (difx < 0 && (inimigos[i].getX() < carro.x)) {
                                    inimigos[i].x = (short) (wall[j].x - SIZES._16);
                                }
                                if (difx > 0 && (inimigos[i].getX() > carro.x)) {
                                    inimigos[i].x = (short) (wall[j].x + SIZES._16);
                                }
                            } else if (difposx < difposy) {
                                if (dify < 0 && (inimigos[i].getY() < carro.y)) {
                                    inimigos[i].y = (short) (wall[j].y - SIZES._16);
                                }
                                if (dify > 0 && (inimigos[i].getY() > carro.y)) {
                                    inimigos[i].y = (short) (wall[j].y + SIZES._16);
                                }
                            }

                        }

                    }
                    for (int j = 0; j < caixo.length; j++) {
                        if (!caixo[j].destroyed) {
                            int difx, dify, difposx, difposy;
                            difx = inimigos[i].x - caixo[j].x;
                            dify = inimigos[i].y - caixo[j].y;
                            if (dify < 0) {
                                difposy = dify * -1;
                            } else {
                                difposy = dify;
                            }
                            if (difx < 0) {
                                difposx = difx * -1;
                            } else {
                                difposx = difx;
                            }
                            if ((difx > -SIZES._16) && (difx < SIZES._16) && (dify > -SIZES._16)
                                    && (dify < SIZES._16)) {
                                if (difposx > difposy) {
                                    if (difx < 0 && (inimigos[i].getX() < carro.x)) {
                                        inimigos[i].x = (short) (caixo[j].x - SIZES._16);
                                        caixo[j].durabilidade -= 1.5;
                                    }
                                    if (difx > 0 && (inimigos[i].getX() > carro.x)) {
                                        inimigos[i].x = (short) (caixo[j].x + SIZES._16);
                                        caixo[j].durabilidade -= 1.5;
                                    }
                                } else if (difposx < difposy) {
                                    if (dify < 0 && (inimigos[i].getY() < carro.y)) {
                                        inimigos[i].y = (short) (caixo[j].y - SIZES._16);
                                        caixo[j].durabilidade -= 1.5;
                                    }
                                    if (dify > 0 && (inimigos[i].getY() > carro.y)) {
                                        inimigos[i].y = (short) (caixo[j].y + SIZES._16);
                                        caixo[j].durabilidade -= 1.5;
                                    }
                                }

                            }
                            if (caixo[j].durabilidade <= 0) {
                                caixo[j].destroyed = true;
                            }

                        }
                    }

                    for (int j = 0; j < destruidoscar.length; j++) {
                        if (!destruidoscar[j].exploded) {
                            int difx, dify, difposx, difposy;
                            difx = inimigos[i].x - destruidoscar[j].x;
                            dify = inimigos[i].y - destruidoscar[j].y;
                            if (dify < 0) {
                                difposy = dify * -1;
                            } else {
                                difposy = dify;
                            }
                            if (difx < 0) {
                                difposx = difx * -1;
                            } else {
                                difposx = difx;
                            }
                            if ((difx > -SIZES._16) && (difx < SIZES._16) && (dify > -SIZES._16)
                                    && (dify < SIZES._16)) {
                                if (difposx > difposy) {
                                    if (difx < 0 && (inimigos[i].getX() < carro.x)) {
                                        inimigos[i].x = (short) (destruidoscar[j].x - SIZES._16);
                                        destruidoscar[j].durabilidade -= 1.5;
                                    }
                                    if (difx > 0 && (inimigos[i].getX() > carro.x)) {
                                        inimigos[i].x = (short) (destruidoscar[j].x + SIZES._16);
                                        destruidoscar[j].durabilidade -= 1.5;
                                    }
                                } else if (difposx < difposy) {
                                    if (dify < 0 && (inimigos[i].getY() < carro.y)) {
                                        inimigos[i].y = (short) (destruidoscar[j].y - SIZES._16);
                                        destruidoscar[j].durabilidade -= 1.5;
                                    }
                                    if (dify > 0 && (inimigos[i].getY() > carro.y)) {
                                        inimigos[i].y = (short) (destruidoscar[j].y + SIZES._16);
                                        destruidoscar[j].durabilidade -= 1.5;
                                    }
                                }

                            }
                            if (destruidoscar[j].durabilidade <= 0) {
                                destruidoscar[j].exploded = true;
                            }

                        }
                    }

                } else if (gameTipo == 2) {
                    for (int j = 0; j < wall.length; j++) {
                        if (inimigos[i].x > (wall[j].x - SIZES._16) && inimigos[i].x < (wall[j].x + SIZES._16)
                                && inimigos[i].y > (wall[j].y - SIZES._16) && inimigos[i].y < (wall[j].y + SIZES._16)) {
                            int diferencax, diferencay;
                            diferencax = inimigos[i].x - wall[j].x;
                            diferencay = inimigos[i].y - wall[j].y;
                            int difx, dify;
                            if (diferencax < 0) {
                                difx = diferencax * (-1);
                            } else {
                                difx = diferencax;
                            }
                            if (diferencay < 0) {
                                dify = diferencay * (-1);
                            } else {
                                dify = diferencay;
                            }
                            if (difx > dify) {
                                if (diferencax < 0) {
                                    int difposi = diferencax * (-1);
                                    int num = SIZES._16 - difposi;
                                    inimigos[i].x -= (num - 1);
                                }
                                if (diferencax > 0) {
                                    int num = SIZES._16 - diferencax;
                                    inimigos[i].x += (num - 1);
                                }
                            } else if (difx < dify) {
                                if (diferencay < 0) {
                                    int difposi = diferencay * (-1);
                                    int num = SIZES._16 - difposi;
                                    inimigos[i].y -= (num - 1);
                                }
                                if (diferencay > 0) {
                                    int num = SIZES._16 - diferencay;
                                    inimigos[i].y += (num - 1);
                                }
                            } else if (difx == dify) {
                                diferencax = inimigos[i].oldX - wall[j].x;
                                diferencay = inimigos[i].oldY - wall[j].y;
                                if (diferencay < 0) {
                                    int difposi = diferencay * (-1);
                                    int num = SIZES._16 - difposi;
                                    inimigos[i].y -= (num - 1);
                                }
                                if (diferencay > 0) {
                                    int num = SIZES._16 - diferencay;
                                    inimigos[i].y += (num - 1);
                                }
                                if (diferencax < 0) {
                                    int difposi = diferencax * (-1);
                                    int num = SIZES._16 - difposi;
                                    inimigos[i].x -= (num - 1);
                                }
                                if (diferencax > 0) {
                                    int num = SIZES._16 - diferencax;
                                    inimigos[i].x += (num - 1);
                                }
                            }
                        }
                    }
                    for (int j = 0; j < caixo.length; j++) {
                        if (!caixo[j].destroyed) {
                            if (inimigos[i].x > (caixo[j].x - SIZES._16) && inimigos[i].x < (caixo[j].x + SIZES._16)
                                    && inimigos[i].y > (caixo[j].y - SIZES._16)
                                    && inimigos[i].y < (caixo[j].y + SIZES._16)) {
                                int diferencax, diferencay;
                                diferencax = inimigos[i].x - caixo[j].x;
                                diferencay = inimigos[i].y - caixo[j].y;
                                int difx, dify;
                                if (diferencax < 0) {
                                    difx = diferencax * (-1);
                                } else {
                                    difx = diferencax;
                                }
                                if (diferencay < 0) {
                                    dify = diferencay * (-1);
                                } else {
                                    dify = diferencay;
                                }
                                if (difx > dify) {
                                    if (diferencax < 0) {
                                        int difposi = diferencax * (-1);
                                        int num = SIZES._16 - difposi;
                                        inimigos[i].x -= (num - 1);
                                        caixo[j].durabilidade -= 1.0;
                                    }
                                    if (diferencax > 0) {
                                        int num = SIZES._16 - diferencax;
                                        inimigos[i].x += (num - 1);
                                        caixo[j].durabilidade -= 1.0;
                                    }
                                } else if (difx < dify) {
                                    if (diferencay < 0) {
                                        int difposi = diferencay * (-1);
                                        int num = SIZES._16 - difposi;
                                        inimigos[i].y -= (num - 1);
                                        caixo[j].durabilidade -= 1.0;
                                    }
                                    if (diferencay > 0) {
                                        int num = SIZES._16 - diferencay;
                                        inimigos[i].y += (num - 1);
                                        caixo[j].durabilidade -= 1.0;
                                    }
                                } else if (difx == dify) {
                                    diferencax = inimigos[i].oldX - caixo[j].x;
                                    diferencay = inimigos[i].oldY - caixo[j].y;
                                    if (diferencay < 0) {
                                        int difposi = diferencay * (-1);
                                        int num = SIZES._16 - difposi;
                                        inimigos[i].y -= (num - 1);
                                        caixo[j].durabilidade -= 1.0;
                                    }
                                    if (diferencay > 0) {
                                        int num = SIZES._16 - diferencay;
                                        inimigos[i].y += (num - 1);
                                        caixo[j].durabilidade -= 1.0;
                                    }
                                    if (diferencax < 0) {
                                        int difposi = diferencax * (-1);
                                        int num = SIZES._16 - difposi;
                                        inimigos[i].x -= (num - 1);
                                        caixo[j].durabilidade -= 1.0;
                                    }
                                    if (diferencax > 0) {
                                        int num = SIZES._16 - diferencax;
                                        inimigos[i].x += (num - 1);
                                        caixo[j].durabilidade -= 1.0;
                                    }
                                }
                            }
                            if (caixo[j].durabilidade <= 0) {
                                caixo[j].destroyed = true;
                            }
                        }
                    }

                    for (int j = 0; j < destruidoscar.length; j++) {
                        if (!destruidoscar[j].exploded) {
                            if (inimigos[i].x > (destruidoscar[j].x - SIZES._16)
                                    && inimigos[i].x < (destruidoscar[j].x + SIZES._16)
                                    && inimigos[i].y > (destruidoscar[j].y - SIZES._16)
                                    && inimigos[i].y < (destruidoscar[j].y + SIZES._16)) {
                                int diferencax, diferencay;
                                diferencax = inimigos[i].x - destruidoscar[j].x;
                                diferencay = inimigos[i].y - destruidoscar[j].y;
                                int difx, dify;
                                if (diferencax < 0) {
                                    difx = diferencax * (-1);
                                } else {
                                    difx = diferencax;
                                }
                                if (diferencay < 0) {
                                    dify = diferencay * (-1);
                                } else {
                                    dify = diferencay;
                                }
                                if (difx > dify) {
                                    if (diferencax < 0) {
                                        int difposi = diferencax * (-1);
                                        int num = SIZES._16 - difposi;
                                        inimigos[i].x -= (num - 1);
                                        destruidoscar[j].durabilidade -= 1.0;
                                    }
                                    if (diferencax > 0) {
                                        int num = SIZES._16 - diferencax;
                                        inimigos[i].x += (num - 1);
                                        destruidoscar[j].durabilidade -= 1.0;
                                    }
                                } else if (difx < dify) {
                                    if (diferencay < 0) {
                                        int difposi = diferencay * (-1);
                                        int num = SIZES._16 - difposi;
                                        inimigos[i].y -= (num - 1);
                                        destruidoscar[j].durabilidade -= 1.0;
                                    }
                                    if (diferencay > 0) {
                                        int num = SIZES._16 - diferencay;
                                        inimigos[i].y += (num - 1);
                                        destruidoscar[j].durabilidade -= 1.0;
                                    }
                                } else if (difx == dify) {
                                    diferencax = inimigos[i].oldX - destruidoscar[j].x;
                                    diferencay = inimigos[i].oldY - destruidoscar[j].y;
                                    if (diferencay < 0) {
                                        int difposi = diferencay * (-1);
                                        int num = SIZES._16 - difposi;
                                        inimigos[i].y -= (num - 1);
                                        destruidoscar[j].durabilidade -= 1.0;
                                    }
                                    if (diferencay > 0) {
                                        int num = SIZES._16 - diferencay;
                                        inimigos[i].y += (num - 1);
                                        destruidoscar[j].durabilidade -= 1.0;
                                    }
                                    if (diferencax < 0) {
                                        int difposi = diferencax * (-1);
                                        int num = SIZES._16 - difposi;
                                        inimigos[i].x -= (num - 1);
                                        destruidoscar[j].durabilidade -= 1.0;
                                    }
                                    if (diferencax > 0) {
                                        int num = SIZES._16 - diferencax;
                                        inimigos[i].x += (num - 1);
                                        destruidoscar[j].durabilidade -= 1.0;
                                    }
                                }
                            }
                            if (destruidoscar[j].durabilidade <= 0) {
                                destruidoscar[j].exploded = true;
                            }
                        }
                    }

                }
            }
        }
        for (int i = 0; i < quantidade; i++) {
            if (frame >= 15) {
                boolean tomou = false;
                if (inimigos[i].getX() >= (carro.x - SIZES._16) && inimigos[i].getX() <= (carro.x + SIZES._16)) {
                    if (inimigos[i].getY() >= (carro.y - SIZES._16) && inimigos[i].getY() <= (carro.y + SIZES._16)) {
                        cardamage += 0.1;
                        tomou = true;
                    }
                }
                if (inimigos[i].getX() >= (carro.x - SIZES._10) && inimigos[i].getX() <= (carro.x + SIZES._10)) {
                    if (inimigos[i].getY() >= (carro.y - SIZES._10) && inimigos[i].getY() <= (carro.y + SIZES._10)) {
                        cardamage += 0.5;
                        tomou = true;
                    }
                }
                if (inimigos[i].getX() >= (carro.x - SIZES._5) && inimigos[i].getX() <= (carro.x + SIZES._5)) {
                    if (inimigos[i].getY() >= (carro.y - SIZES._5) && inimigos[i].getY() <= (carro.y + SIZES._5)) {
                        cardamage += 1;
                        tomou = true;
                    }
                }
                if (inimigos[i].getX() == carro.x) {
                    if (inimigos[i].getY() == carro.y) {
                        cardamage += 1.5;
                        tomou = true;
                    }
                }
                if (tomou) {
                    tempoinvisible = 0;
                    if (tempodano <= 0) {
                        tempodano = 500;
                    }
                }

            }
        }
    }

    private void colisaobots() {
        for (int i = 0; i < quantidade; i++) {
            for (int j = 0; j < quantidade; j++) {
                if (i < j) {
                    if (inimigos[i].x < (inimigos[j].x + SIZES._16) && inimigos[i].x > (inimigos[j].x - SIZES._16)
                            && inimigos[i].y < (inimigos[j].y + SIZES._16)
                            && inimigos[i].y > (inimigos[j].y - SIZES._16)) {
                        int diferencax, diferencay;
                        diferencax = inimigos[i].x - inimigos[j].x;
                        diferencay = inimigos[i].y - inimigos[j].y;
                        int difx, dify;
                        if (diferencax < 0) {
                            difx = diferencax * (-1);
                        } else {
                            difx = diferencax;
                        }
                        if (diferencay < 0) {
                            dify = diferencay * (-1);
                        } else {
                            dify = diferencay;
                        }
                        if (difx > dify) {
                            if (diferencax < 0) {
                                int difposi = diferencax * (-1);
                                int num = SIZES._16 - difposi;
                                inimigos[i].x -= (num - 1);
                            }
                            if (diferencax > 0) {
                                int num = SIZES._16 - diferencax;
                                inimigos[i].x += (num - 1);
                            }
                        } else if (difx < dify) {
                            if (diferencay < 0) {
                                int difposi = diferencay * (-1);
                                int num = SIZES._16 - difposi;
                                inimigos[i].y -= (num - 1);
                            }
                            if (diferencay > 0) {
                                int num = SIZES._16 - diferencay;
                                inimigos[i].y += (num - 1);
                            }
                        } else if (difx == dify) {
                            if (diferencay < 0) {
                                int difposi = diferencay * (-1);
                                int num = SIZES._16 - difposi;
                                inimigos[i].y -= (num - 1);
                            }
                            if (diferencay > 0) {
                                int num = SIZES._16 - diferencay;
                                inimigos[i].y += (num - 1);
                            }
                            if (diferencax < 0) {
                                int difposi = diferencax * (-1);
                                int num = SIZES._16 - difposi;
                                inimigos[i].x -= (num - 1);
                            }
                            if (diferencax > 0) {
                                int num = SIZES._16 - diferencax;
                                inimigos[i].x += (num - 1);
                            }
                        }
                    }
                }
            }
        }
    }

    int menuPauseOp;
    boolean alinhandotextos = false;
    String textPauseInfo = "";

    private void menupause() {
        textPauseInfo = "";
        menuPauseOp = 1;
        irbaixo = false;
        irbaixo2 = false;
        ircima = false;
        ircima2 = false;
        irdireita = false;
        irdireita2 = false;
        iresquerda = false;
        iresquerda2 = false;
        if (DEBUGMODE) {
            TA = new TextAttributes(Color.GREEN);
            DEBUGCONSOLE.setTextAttributes(TA);
            System.out.println("INFO | O GAME FOI PAUSADO");
        }
        telaatual = "pause";
        int alphcor = 0, corMenuTitle = 0, alphcor2 = 0, wait = 0;
        boolean bluring = true;
        do {
            bbg.drawImage(fundoPause, 0, 0, this);
            bbg.setColor(new Color(0, 0, 0, alphcor));
            bbg.fillRect(0, 0, janelaW, janelaH);
            if (alinhandotextos) {
                bbg.setColor(Color.WHITE);
                bbg.drawLine(janelaW / 2, 0, janelaW / 2, janelaH);
            }
            bbg.setColor(new Color(255, 255 - corMenuTitle, 255 - corMenuTitle, 255 - alphcor2));
            bbg.setFont(Fontes.LIBEL.deriveFont(Font.BOLD, 32));
            bbg.drawString(iMsg.pause.texto, (janelaW / 2) - (bbg.getFontMetrics().stringWidth(iMsg.pause.texto) / 2), 55);
            bbg.setColor(new Color(33, 33, 33, alphcor));
            bbg.fill3DRect(0, 90, janelaW, janelaH - 180, true);
            switch (menuPauseOp) {
                case 1:
                    bbg.setFont(Fontes.LIBEL.deriveFont(Font.BOLD, 25));
                    bbg.setColor(new Color(255, 10, 10));
                    bbg.drawString(iMsg.continuar.texto,
                            (janelaW / 2) - (bbg.getFontMetrics().stringWidth(iMsg.continuar.texto) / 2), 120);
                    bbg.setFont(Fontes.LIBEL.deriveFont(Font.BOLD, 19));
                    bbg.setColor(new Color(255, 50, 50, 127));
                    bbg.drawString(iMsg.salvarJogo.texto,
                            (janelaW / 2) - (bbg.getFontMetrics().stringWidth(iMsg.salvarJogo.texto) / 2), 145);
                    bbg.drawString(iMsg.jogoOp.texto,
                            (janelaW / 2) - (bbg.getFontMetrics().stringWidth(iMsg.jogoOp.texto) / 2), 170);
                    bbg.drawString(iMsg.voltarMenu.texto,
                            (janelaW / 2) - (bbg.getFontMetrics().stringWidth(iMsg.voltarMenu.texto) / 2), 195);
                    break;
                case 2:
                    bbg.setFont(Fontes.LIBEL.deriveFont(Font.BOLD, 25));
                    bbg.setColor(new Color(255, 10, 10));
                    bbg.drawString(iMsg.salvarJogo.texto,
                            (janelaW / 2) - (bbg.getFontMetrics().stringWidth(iMsg.salvarJogo.texto) / 2), 145);
                    bbg.setFont(Fontes.LIBEL.deriveFont(Font.BOLD, 19));
                    bbg.setColor(new Color(255, 50, 50, 127));
                    bbg.drawString(iMsg.continuar.texto,
                            (janelaW / 2) - (bbg.getFontMetrics().stringWidth(iMsg.continuar.texto) / 2), 120);
                    bbg.drawString(iMsg.jogoOp.texto,
                            (janelaW / 2) - (bbg.getFontMetrics().stringWidth(iMsg.jogoOp.texto) / 2), 170);
                    bbg.drawString(iMsg.voltarMenu.texto,
                            (janelaW / 2) - (bbg.getFontMetrics().stringWidth(iMsg.voltarMenu.texto) / 2), 195);
                    break;
                case 3:
                    bbg.setFont(Fontes.LIBEL.deriveFont(Font.BOLD, 25));
                    bbg.setColor(new Color(255, 127, 127, 150));
                    bbg.drawString(iMsg.jogoOp.texto,
                            (janelaW / 2) - (bbg.getFontMetrics().stringWidth(iMsg.jogoOp.texto) / 2), 170);
                    bbg.setFont(Fontes.LIBEL.deriveFont(Font.BOLD, 19));
                    bbg.setColor(new Color(255, 50, 50, 127));
                    bbg.drawString(iMsg.salvarJogo.texto,
                            (janelaW / 2) - (bbg.getFontMetrics().stringWidth(iMsg.salvarJogo.texto) / 2), 145);
                    bbg.drawString(iMsg.continuar.texto,
                            (janelaW / 2) - (bbg.getFontMetrics().stringWidth(iMsg.continuar.texto) / 2), 120);
                    bbg.drawString(iMsg.voltarMenu.texto,
                            (janelaW / 2) - (bbg.getFontMetrics().stringWidth(iMsg.voltarMenu.texto) / 2), 195);
                    break;
                case 4:
                    bbg.setFont(Fontes.LIBEL.deriveFont(Font.BOLD, 25));
                    bbg.setColor(new Color(255, 10, 10));
                    bbg.drawString(iMsg.voltarMenu.texto,
                            (janelaW / 2) - (bbg.getFontMetrics().stringWidth(iMsg.voltarMenu.texto) / 2), 195);
                    bbg.setFont(Fontes.LIBEL.deriveFont(Font.BOLD, 19));
                    bbg.setColor(new Color(255, 50, 50, 127));
                    bbg.drawString(iMsg.salvarJogo.texto,
                            (janelaW / 2) - (bbg.getFontMetrics().stringWidth(iMsg.salvarJogo.texto) / 2), 145);
                    bbg.drawString(iMsg.jogoOp.texto,
                            (janelaW / 2) - (bbg.getFontMetrics().stringWidth(iMsg.jogoOp.texto) / 2), 170);
                    bbg.drawString(iMsg.continuar.texto,
                            (janelaW / 2) - (bbg.getFontMetrics().stringWidth(iMsg.continuar.texto) / 2), 120);
                    break;
            }
            bbg.setColor(fonte);
            bbg.drawString(textPauseInfo, (int) (janelaW - (80 * MODRESOL)), janelaH - 110);

            g.drawImage(backBuffer, 0, 0, this.getWidth(), this.getHeight(), this);
            alphcor += 9;
            if (wait > 0) {
                wait--;
            }
            if (alphcor > 190) {
                alphcor = 190;
            }
            if (bluring && wait <= 0) {
                corMenuTitle += 4;
            } else if (wait <= 0) {
                corMenuTitle -= 4;
            }
            if (corMenuTitle > 255) {
                corMenuTitle = 255;
                bluring = false;
                wait = 45;
            } else if (corMenuTitle < 0) {
                corMenuTitle = 0;
                bluring = true;
                wait = 20;
            }
            alphcor2 = corMenuTitle / 4;
            try {
                Thread.sleep(1000 / FrPS._60);
            } catch (InterruptedException ex) {
            }
        } while (yep == 2);
        if (yep == 1) {
            game();
        } else if (yep == 0) {
            menuEsquecido = true;
            mainMenu();
        }
    }

    static int getNumber() {
        return 5;
    }

    // MÃ‰TODO RUN() NELE TEM O GAME LOOP (UM LOOP INFINITO)
    public void run() {
        if (DEBUGMODE) {
            DEBUGCONSOLE = Enigma.getConsole();
            DEBUGCONSOLE.setTitle("CAR THE GAME v" + ver + " - debug console");
            TA = new TextAttributes(Color.BLUE);
            DEBUGCONSOLE.setTextAttributes(TA);
            System.out.println("               ___________  ________");
            System.out.println("              /  _____/   |/  __   / ");
            System.out.println("             /  /    / /| |  /_/  /");
            System.out.println("            /  /    / /_| |    __/");
            System.out.println("           /  /____/ ____ |__  \\");
            System.out.println("          /_______/_/  /|_| /__/");
            TA = new TextAttributes(Color.RED);
            DEBUGCONSOLE.setTextAttributes(TA);
            System.out.println("     _____      ____   ____ ___  ___  ____");
            System.out.println("       |  |___||___   | ___|___|| | ||___");
            System.out.print("       |  |   ||____  |___||   ||   ||____ ");
            TA = new TextAttributes(Color.BLUE);
            DEBUGCONSOLE.setTextAttributes(TA);
            System.out.println(ver);
            TA = new TextAttributes(Color.WHITE);
            DEBUGCONSOLE.setTextAttributes(TA);
            System.out.println("\nVersão do carregador de niveis (Build Level): " + verint);
            System.out.println("Versão da NonaCept Game Library: " + com.nonacept.Properties.VERSION);
            TA = new TextAttributes(Color.GREEN);
            DEBUGCONSOLE.setTextAttributes(TA);
            System.out.println("\nINFO | INICIALIZANDO RECURSOS...");
        } else {
            // debugConsole=Enigma.getConsole();
            // debugConsole.setTitle("eu não sei pq, mas o jogo roda melhor com isso
            // aberto");
        }
        inicializar();

    }

    public static void main(String[] args) {
        try {
            /*
             * for (javax.swing.UIManager.LookAndFeelInfo info :
             * javax.swing.UIManager.getInstalledLookAndFeels()) { if
             * ("Nimbus".equals(info.getName())) {
             * javax.swing.UIManager.setLookAndFeel(info.getClassName()); break; } }
             */
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            System.out.println("com.nonacept.carthegame.Game.main() Look And Feel do sistema não pode ser carregado");
        }
        boolean ng = false;
        Game game = new Game(); // CRIAÃ‡ÃƒO DE UM OBJETO Ã€ PARTIR DESSA PRÃ“PRIA CLASSE
        if (args.length > 0) {
            if ("-d".equals(args[0])) {
                game.setDebugMode(true);
            }
            if ("-c".equals(args[0])) {
                Construcao c = new Construcao();
                ng = true;
                c.run();
            }
            if ("-noanim".equals(args[0])) {
                game.yep = 0;
            }
            if ("-ver".equals(args[0]) || "-v".equals(args[0])) {
                Console s_console = Enigma.getConsole();
                TextAttributes corzinha = new TextAttributes(Color.RED);
                s_console.setTextAttributes(corzinha);
                System.out.println("*=========================================*");
                System.out.print("||              ");
                corzinha = new TextAttributes(Color.BLUE);
                s_console.setTextAttributes(corzinha);
                System.out.print("CAR THE GAME             ");
                corzinha = new TextAttributes(Color.RED);
                s_console.setTextAttributes(corzinha);
                System.out.println("||");
                System.out.println("*=========================================*");
                corzinha = new TextAttributes(Color.GREEN);
                s_console.setTextAttributes(corzinha);
                System.out.println("    VERSÃO: " + game.ver);
                System.out.println("    BUILD LEVEL**: " + game.verint);
                //System.out.println("    NONACEPT LIB: " + com.nonacept.Properties.VERSION);
                corzinha = new TextAttributes(Color.WHITE);
                s_console.setTextAttributes(corzinha);
                System.out.println("*=========================================*");
                System.out.println("\n\n**O QUE É ISSO?\n");
                corzinha = new TextAttributes(Color.ORANGE);
                s_console.setTextAttributes(corzinha);
                System.out.print("O Build Level nada mais é do que a versão do interpretador do jogo, infelizmente");
                System.out.print("alguns recursos podem apresentar problemas quando salvos em uma versão diferente");
                System.out.print("da qual está sendo executada. É por isso que toda atualização em que há mudanças");
                System.out.println("no interpretador você perde seu jogo salvo, mas não se preocupe isso será");
                System.out.print("melhorado.");
                ng = true;
            }
            if ("-changelog".equals(args[0])) {
                ng = true;
                int count = 0;
                try {
                    Console s_console = Enigma.getConsole();
                    TextAttributes corzinha = new TextAttributes(Color.ORANGE);
                    s_console.setTextAttributes(corzinha);
                    System.out.println("Pressione <ENTER> para avançar");
                    System.out.println("Pressione <Q> e depois <ENTER> para fechar");
                    File changelog = new File("after.txt");
                    InputStream leito = new FileInputStream(changelog);
                    Charset cs = Charset.forName("UTF-8");
                    InputStreamReader isrchange = new InputStreamReader(leito, cs);
                    BufferedReader entrada = new BufferedReader(isrchange);
                    String linha;
                    while ((linha = entrada.readLine()) != null) {
                        corzinha = new TextAttributes(Color.LIGHT_GRAY);
                        s_console.setTextAttributes(corzinha);
                        if (linha.startsWith("*")) {
                            count++;
                            if (count > 1) {
                                corzinha = new TextAttributes(Color.BLACK);
                                s_console.setTextAttributes(corzinha);
                                String lida = s_console.readLine();
                                lida = lida.toLowerCase();
                                if ("q".equals(lida)) {
                                    System.exit(0);
                                }
                            }
                            corzinha = new TextAttributes(Color.CYAN);
                            s_console.setTextAttributes(corzinha);
                        }
                        System.out.println(linha);
                    }
                    corzinha = new TextAttributes(Color.RED);
                    s_console.setTextAttributes(corzinha);
                    System.out.println("\nPressione <ENTER> para fechar.");
                    s_console.readLine();
                    System.exit(0);
                } catch (FileNotFoundException ex) {
                    TA = new TextAttributes(Color.RED);
                    DEBUGCONSOLE.setTextAttributes(TA);
                    System.out.println("O arquivo de mudanças não pode ser encontrado.");
                } catch (IOException ex) {
                }
            }
            if ("-dg".equals(args[0])) {
                if (devVer) {
                    game.yep = 0;
                    directGame = true;
                }
            }
        }
        if (!ng) {
            game.setUndecorated(true);
            game.run();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (telaatual.equals("anim")) { // ANIMAÃ‡ÃƒO
            if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                if (DEBUGMODE) {
                    TA = new TextAttributes(Color.BLUE);
                    DEBUGCONSOLE.setTextAttributes(TA);
                    System.out.println("O vídeo foi pulado...");
                }
                yep = 0;
            }
        }
        if (telaatual.equals("gameover")) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_ESCAPE
                    || e.getKeyCode() == KeyEvent.VK_SPACE) {
                if (contar < 225) {
                    contar = 225;
                } else if (contar > 225 && contar < 370) {
                    contar = 370;
                } else if (contar > 370) {
                    if (DEBUGMODE) {
                        TA = new TextAttributes(Color.ORANGE);
                        DEBUGCONSOLE.setTextAttributes(TA);
                        System.out.println("Você perdeu! :(\nVamos ao menu!");
                    }
                    yep = 0;
                }
            }
        }

        if (telaatual.equals("menu")) { // MENU
            menuEsquecido = false;
            sfx.unloadInicio();
            boolean bugSair = false;
            if (e.getKeyCode() == KeyEvent.VK_T) {
                sortMus(0);
            }
            if (menuStart) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (menuStartOp < 1) {
                        menuStartOp++;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (menuStartOp > 0) {
                        menuStartOp--;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    switch (menuStartOp) {
                        case 0:
                            if (InstrucoesView) {
                                yep = 1;
                            } else {
                                yep = 8;
                                nxtyep = 1;
                            }
                            break;
                        case 1:
                            if (!noSave) {
                                yep = 7;
                            }
                            break;
                    }
                }
            }
            if (menuSair) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (menuSairOp < 1) {
                        menuSairOp++;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (menuSairOp > 0) {
                        menuSairOp--;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (menuSairOp == 1) {
                        if (DEBUGMODE) {
                            TA = new TextAttributes(Color.ORANGE);
                            DEBUGCONSOLE.setTextAttributes(TA);
                            System.out.println("ALRT| SAINDO...");
                        }
                        yep = -100;
                    }
                    if (menuSairOp == 0) {
                        menuSair = false;
                        bugSair = true;
                    }
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (opmenu == 1) {
                    if (!menuStart) {
                        menuStart = true;
                        menuSair = false;
                        menuStartOp = 0;
                    }
                }
                if (opmenu == 3) {
                    if (!menuSair && !bugSair) {
                        menuSair = true;
                        menuStart = false;
                        menuSairOp = 0;
                        bugSair = false;
                    }

                }
                if (opmenu == 0) {
                    yep = 3;
                    menuSair = false;
                    menuStart = false;
                }
                if (opmenu == 2) {
                    yep = -5;

                }

            }
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                if (menuSair || menuStart) {
                    menuSair = false;
                    menuStart = false;
                } else {
                    opmenu = 3;
                    menuSair = true;
                    menuSairOp = 0;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if (opmenu < 3) {
                    opmenu++;
                    menuSair = false;
                    menuStart = false;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                if (opmenu > 0) {
                    opmenu--;
                    menuSair = false;
                    menuStart = false;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_H) {
                if (e.isShiftDown()) {
                    playableTutor = false;
                    yep = 8;
                    nxtyep = 0;
                }
                InstrucoesView = false;
            }

        }

        if (telaatual.equals("game")) { // JOGO
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                yep = 2;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                irbaixo = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                irbaixo2 = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                ircima = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_W) {
                ircima2 = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                irdireita = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_D) {
                irdireita2 = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                iresquerda = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_A) {
                iresquerda2 = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_T) {
                sortMus(1);
            }
            if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                if (nitro > 0 && (ircima || irbaixo || irdireita || iresquerda || ircima2 || irbaixo2 || irdireita2
                        || iresquerda2)) {
                    ativado = true;
                    propcar = 2.8;
                }
            }
        }

        if (telaatual.equals("pause")) { // PAUSE
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                if (DEBUGMODE == true) {
                    TA = new TextAttributes(Color.BLUE);
                    DEBUGCONSOLE.setTextAttributes(TA);
                    System.out.println("Vamos voltar Ã  aÃ§Ã£o... ;)");
                }
                yep = 1;
            }
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                switch (menuPauseOp) {
                    case 1:
                        if (DEBUGMODE) {
                            TA = new TextAttributes(Color.BLUE);
                            DEBUGCONSOLE.setTextAttributes(TA);
                            System.out.println("Hora do show! (de novo)");
                        }
                        yep = 1;
                        break;
                    case 2:
                        SaveGamem("save");
                        break;
                    case 3:
                        break;
                    case 4:
                        if (DEBUGMODE) {
                            TA = new TextAttributes(Color.YELLOW);
                            DEBUGCONSOLE.setTextAttributes(TA);
                            System.out.println("Menu?!");
                        }
                        //eita.stop();
                        //bgm.stop();
                        yep = 0;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_T) {
                sortMus(1);
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                menuPauseOp++;
                if (menuPauseOp > 4) {
                    menuPauseOp = 1;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                menuPauseOp--;
                if (menuPauseOp < 1) {
                    menuPauseOp = 4;
                }
            }
        }

        if (telaatual.equals("creditos")) { // CREDITOS
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                yep = 0;
            }
        }

        if (telaatual.equals("multiplayer")) { // MULTIPLAYER
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                irbaixo = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                ircima = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                irdireita = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                iresquerda = true;
            }
        }
        if (telaatual.equals("tutorial")) {
            if (e.getKeyCode() == KeyEvent.VK_S) {
                if (skipableTutor) {
                    InstrucoesView = true;
                    yep = 1;
                } else {
                    skipableTutor = true;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                tutorialPage++;
                if (tutorialPage > 4) {
                    tutorialPage = 4;
                    if (!playableTutor) {
                        InstrucoesView = true;
                        yep = 0;
                    }
                    if (skipableTutor) {
                        if (playableTutor) {
                            InstrucoesView = true;
                            yep = 1;
                        }
                    } else {
                        skipableTutor = true;
                    }
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                tutorialPage--;
                if (tutorialPage < 0) {
                    tutorialPage = 0;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                yep = 0;
            }
        }
        if (telaatual.equals("apresentacao")) { // APRESENTAÃ‡ÃƒO
            if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_ESCAPE
                    || e.getKeyCode() == KeyEvent.VK_SPACE) {
                framesApresen = 410;
            }
        }
        if (telaatual.equals("opcoes")) { // OPÃ‡Ã•ES
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (op.menuY == 0) {
                    op.menuY++;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                op.menuY = 0;
            }
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                if (op.menuY == 0) {
                    yep = 0;
                } else {
                    op.menuY = 0;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if (op.menuY == 0) {
                    op.menuX++;
                    if (op.menuX >= op.menu.getMenus().length) {
                        op.menuX = op.menu.getMenus().length - 1;
                    }
                } else {
                    switch (op.menuX) {
                        case 0:
                            switch (op.menuY) {
                                case 1:
                                    op.idResol++;
                                    op.nSalvo = true;
                                    if (op.idResol >= resolsDisp.length) {
                                        op.idResol = resolsDisp.length - 1;
                                    }
                                    break;
                                case 2:
                                    op.idFullscr++;
                                    op.nSalvo = true;
                                    if (op.idFPS > 2) {
                                        op.idFPS = 2;
                                    }
                                    if (op.idFullscr > 1) {
                                        op.idFullscr = 1;
                                    }
                                    break;
                                case 3:
                                    op.idFPS++;
                                    op.nSalvo = true;
                                    if (op.idFPS >= op.FPSdisp.length) {
                                        op.idFPS = op.FPSdisp.length - 1;
                                    }
                                    if (op.idFullscr == 1 && op.idFPS > 2) {
                                        op.idFPS = 2;
                                    }
                                    break;
                                case 4:
                                    op.idAA++;
                                    op.nSalvo = true;
                                    if (op.idAA > 1) {
                                        op.idAA = 1;
                                    }
                                    break;
                            }
                            break;
                        case 1:
                            switch (op.menuY) {
                                case 1:
                                    op.idLang++;
                                    op.nSalvo = true;
                                    if (op.idLang >= op.nomesLang.length) {
                                        op.idLang = op.nomesLang.length - 1;
                                    }
                                    break;
                            }
                            break;
                        case 2:
                            switch (op.menuY) {
                                case 1:
                                    if (!(op.idVolume + 1 >= op.volumes.length)) {
                                        op.idVolume++;
                                        op.nSalvo = true;
                                    } else {
                                        op.idVolume = op.volumes.length - 1;
                                    }

                                    break;
                                default:
                                    if (op.menuY > 2 && op.menuY < op.musicas_list.size() + 3) {
                                        op.nSalvo = true;
                                        op.musicas_list.get(op.menuY - 3).ativo = !op.musicas_list.get(op.menuY - 3).ativo;
                                    }
                                    break;
                            }
                            break;
                    }
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                if (op.menuY == 0) {
                    op.menuX--;
                    if (op.menuX < 0) {
                        op.menuX = 0;
                    }
                } else {
                    switch (op.menuX) {
                        case 0:
                            switch (op.menuY) {
                                case 1:
                                    op.idResol--;
                                    op.nSalvo = true;
                                    if (op.idResol < 0) {
                                        op.idResol = 0;
                                    }
                                    break;
                                case 2:
                                    op.idFullscr--;
                                    op.nSalvo = true;
                                    if (op.idFullscr < 0) {
                                        op.idFullscr = 0;
                                    }
                                    break;
                                case 3:
                                    op.idFPS--;
                                    op.nSalvo = true;
                                    if (op.idFPS < 0) {
                                        op.idFPS = 0;
                                    }
                                    break;
                                case 4:
                                    op.idAA--;
                                    op.nSalvo = true;
                                    if (op.idAA < 0) {
                                        op.idAA = 0;
                                    }
                                    break;
                            }
                            break;
                        case 1:
                            switch (op.menuY) {
                                case 1:
                                    op.idLang--;
                                    op.nSalvo = true;
                                    if (op.idLang < 0) {
                                        op.idLang = 0;
                                    }
                                    break;
                            }
                            break;
                        case 2:
                            switch (op.menuY) {
                                case 1:
                                    if (!(op.idVolume - 1 < 0)) {
                                        op.idVolume--;
                                        op.nSalvo = true;
                                    } else {
                                        op.idVolume = 0;
                                    }
                                    break;
                                default:
                                    if (op.menuY > 2 && op.menuY < op.musicas_list.size() + 3) {
                                        op.nSalvo = true;
                                        op.musicas_list.get(op.menuY - 3).ativo = !op.musicas_list.get(op.menuY - 3).ativo;
                                    }
                                    break;
                            }
                            break;
                    }
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                op.menuY++;
                if (op.menuY >= op.paineis.get(op.menuX).getItens().length) {
                    op.menuY = op.paineis.get(op.menuX).getItens().length;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                op.menuY--;
                if (op.menuY < 0) {
                    op.menuY = 0;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                if (op.nSalvo) {
                    op.yep = 1;
                }
            }
        }
    }

    public int GOMotivo;

    public void posicaoParedeGame2() {
        for (Paredes wall1 : wall) {
            wall1.x = (short) ((wall1.oldX * MODRESOL) - mapX + carro.x);
            wall1.y = (short) ((wall1.oldY * MODRESOL) - mapY + carro.y);
        }
        for (Itens itensJg21 : itensJg2) {
            itensJg21.Xi = (short) ((itensJg21.oldXi * MODRESOL) - mapX + carro.x);
            itensJg21.Yi = (short) ((itensJg21.oldYi * MODRESOL) - mapY + carro.y);
        }
        for (Chegada chegada : chegadas) {
            chegada.x = (short) ((chegada.oldX * MODRESOL) - mapX + carro.x);
            chegada.y = (short) ((chegada.oldY * MODRESOL) - mapY + carro.y);
        }
        for (Caixote caixo1 : caixo) {
            caixo1.x = (short) ((caixo1.oldX * MODRESOL) - mapX + carro.x);
            caixo1.y = (short) ((caixo1.oldY * MODRESOL) - mapY + carro.y);
        }
        for (DestroyedCar destruidoscar1 : destruidoscar) {
            destruidoscar1.x = (short) ((destruidoscar1.oldX * MODRESOL) - mapX + carro.x);
            destruidoscar1.y = (short) ((destruidoscar1.oldY * MODRESOL) - mapY + carro.y);
        }
        for (Cenario cena : cenas) {
            cena.x = (short) ((cena.oldX * MODRESOL) - mapX + carro.x);
            cena.y = (short) ((cena.oldY * MODRESOL) - mapY + carro.y);
        }
    }

    public void verificaposicao() {
        if (gameTipo == 1) {
            if (carro.x <= 0) {
                carro.x = 0;
            }
            if (carro.y <= 0) {
                carro.y = 0;
            }
            if (carro.y >= resol.diferenca2) {
                carro.y = resol.diferenca2;
            }
            if (carro.x >= resol.tamX2) {
                carro.x = resol.tamX2;
            }
        }
        for (int i = 0; i < mapLink.length; i++) {
            int difx, dify;
            difx = carro.x - mapLink[i].x;
            dify = carro.y - mapLink[i].y;
            if ((difx > -SIZES._16) && (difx < mapLink[i].tamX) && (dify > -SIZES._16) && (dify < mapLink[i].tamY)) {
                System.out.println("Link ativo");
            }
        }
        for (int i = 0; i < chegadas.length; i++) {
            int difx, dify;
            difx = carro.x - chegadas[i].x;
            dify = carro.y - chegadas[i].y;
            if ((difx > -SIZES._16) && (difx < SIZES._16) && (dify > -SIZES._16) && (dify < SIZES._16)) {
                if (chegadas[i].ativo) {
                    yep = 6;
                }
            }
        }
        if (gameTipo == 1) {
            for (int i = 0; i < wall.length; i++) {
                int difx, dify, difposx, difposy;
                difx = carro.x - wall[i].x;
                dify = carro.y - wall[i].y;
                if (dify < 0) {
                    difposy = dify * -1;
                } else {
                    difposy = dify;
                }
                if (difx < 0) {
                    difposx = difx * -1;
                } else {
                    difposx = difx;
                }
                if ((difx > -SIZES._16) && (difx < SIZES._16) && (dify > -SIZES._16) && (dify < SIZES._16)) {
                    if (difposx > difposy) {
                        if (difx < 0 && (irdireita || irdireita2)) {
                            carro.x = wall[i].x - SIZES._16;
                        }
                        if (difx > 0 && (iresquerda || iresquerda2)) {
                            carro.x = wall[i].x + SIZES._16;
                        }
                    } else {
                        if (dify < 0 && (irbaixo || irbaixo2)) {
                            carro.y = wall[i].y - SIZES._16;
                        }
                        if (dify > 0 && (ircima || ircima2)) {
                            carro.y = wall[i].y + SIZES._16;
                        }
                    }
                    if (difx == 0 && dify == 0) {
                        yep = 4;
                        GOMotivo = 200;
                    }
                }

            }
            for (int i = 0; i < caixo.length; i++) {
                if (!caixo[i].destroyed) {
                    int difx, dify, difposx, difposy;
                    difx = carro.x - caixo[i].x;
                    dify = carro.y - caixo[i].y;
                    if (dify < 0) {
                        difposy = dify * -1;
                    } else {
                        difposy = dify;
                    }
                    if (difx < 0) {
                        difposx = difx * -1;
                    } else {
                        difposx = difx;
                    }
                    if ((difx > -SIZES._16) && (difx < SIZES._16) && (dify > -SIZES._16) && (dify < SIZES._16)) {
                        if (difposx > difposy) {
                            if (difx < 0 && (irdireita || irdireita2)) {
                                carro.x = caixo[i].x - SIZES._16;
                                caixo[i].durabilidade -= 2.0;
                                cardamage += 0.1;
                            }
                            if (difx > 0 && (iresquerda || iresquerda2)) {
                                carro.x = caixo[i].x + SIZES._16;
                                caixo[i].durabilidade -= 2.0;
                                cardamage += 0.1;
                            }
                        } else {
                            if (dify < 0 && (irbaixo || irbaixo2)) {
                                carro.y = caixo[i].y - SIZES._16;
                                caixo[i].durabilidade -= 2.0;
                                cardamage += 0.1;
                            }
                            if (dify > 0 && (ircima || ircima2)) {
                                carro.y = caixo[i].y + SIZES._16;
                                caixo[i].durabilidade -= 2.0;
                                cardamage += 0.1;
                            }
                        }
                        if (difx == 0 && dify == 0) {
                            yep = 4;
                            GOMotivo = 200;
                        }
                    }
                    if (caixo[i].durabilidade <= 0) {
                        caixo[i].destroyed = true;
                    }
                }

            }

            for (int i = 0; i < destruidoscar.length; i++) {
                if (!destruidoscar[i].exploded) {
                    int difx, dify, difposx, difposy;
                    difx = carro.x - destruidoscar[i].x;
                    dify = carro.y - destruidoscar[i].y;
                    if (dify < 0) {
                        difposy = dify * -1;
                    } else {
                        difposy = dify;
                    }
                    if (difx < 0) {
                        difposx = difx * -1;
                    } else {
                        difposx = difx;
                    }
                    if ((difx > -SIZES._16) && (difx < SIZES._16) && (dify > -SIZES._16) && (dify < SIZES._16)) {
                        if (difposx > difposy) {
                            if (difx < 0 && (irdireita || irdireita2)) {
                                carro.x = destruidoscar[i].x - SIZES._16;
                                destruidoscar[i].durabilidade -= 2.0;
                                cardamage += 0.2;
                            }
                            if (difx > 0 && (iresquerda || iresquerda2)) {
                                carro.x = destruidoscar[i].x + SIZES._16;
                                destruidoscar[i].durabilidade -= 2.0;
                                cardamage += 0.2;
                            }
                        } else {
                            if (dify < 0 && (irbaixo || irbaixo2)) {
                                carro.y = destruidoscar[i].y - SIZES._16;
                                destruidoscar[i].durabilidade -= 2.0;
                                cardamage += 0.2;
                            }
                            if (dify > 0 && (ircima || ircima2)) {
                                carro.y = destruidoscar[i].y + SIZES._16;
                                destruidoscar[i].durabilidade -= 2.0;
                                cardamage += 0.2;
                            }
                        }
                        if (difx == 0 && dify == 0) {
                            yep = 4;
                            GOMotivo = 200;
                        }
                    }
                    if (destruidoscar[i].durabilidade <= 0) {
                        destruidoscar[i].exploded = true;
                        cardamage += 7.5;
                    }
                }

            }

            for (int i = 0; i < portal.length; i++) {
                int difx, dify;
                difx = carro.x - portal[i].x;
                dify = carro.y - portal[i].y;
                if ((difx > -SIZES._16) && (difx < SIZES._16) && (dify > -SIZES._16) && (dify < SIZES._16)) {
                    if (portal[i].ativo) {
                        carro.x = portal[i].destX;
                        carro.y = portal[i].destY;
                        portal[i].ativo = false;
                    }
                }
            }
        }
        if (gameTipo == 2) {
            for (int j = 0; j < wall.length; j++) {
                int difx, dify;
                int diferencax, diferencay;
                diferencax = carro.x - wall[j].x;
                diferencay = carro.y - wall[j].y;
                if ((diferencax > -SIZES._16) && (diferencax < SIZES._16) && (diferencay > -SIZES._16)
                        && (diferencay < SIZES._16)) {
                    if (diferencax < 0) {
                        difx = diferencax * (-1);
                    } else {
                        difx = diferencax;
                    }
                    if (diferencay < 0) {
                        dify = diferencay * (-1);
                    } else {
                        dify = diferencay;
                    }
                    if (difx > dify) {
                        if (diferencax < 0) {
                            int difposi = diferencax * (-1);
                            int num = SIZES._16 - difposi;
                            mapX -= (num - 1);
                            for (int a = 0; a < quantidade; a++) {
                                inimigos[a].x += (num - 1);
                            }
                        }
                        if (diferencax > 0) {
                            int num = SIZES._16 - diferencax;
                            mapX += (num - 1);
                            for (int a = 0; a < quantidade; a++) {
                                inimigos[a].x -= (num - 1);
                            }
                        }
                    } else if (difx < dify) {
                        if (diferencay < 0) {
                            int difposi = diferencay * (-1);
                            int num = SIZES._16 - difposi;
                            mapY -= (num - 1);
                            for (int a = 0; a < quantidade; a++) {
                                inimigos[a].y += (num - 1);
                            }
                        }
                        if (diferencay > 0) {
                            int num = SIZES._16 - diferencay;
                            mapY += (num - 1);
                            for (int a = 0; a < quantidade; a++) {
                                inimigos[a].y -= (num - 1);
                            }
                        }
                    } else if (difx == dify) {
                        if (diferencay < 0) {
                            int difposi = diferencay * (-1);
                            int num = SIZES._16 - difposi;
                            mapY -= (num - 1);
                            for (int a = 0; a < quantidade; a++) {
                                inimigos[a].y += (num - 1);
                            }
                        }
                        if (diferencay > 0) {
                            int num = SIZES._16 - diferencay;
                            mapY += (num - 1);
                            for (int a = 0; a < quantidade; a++) {
                                inimigos[a].y -= (num - 1);
                            }
                        }
                        if (diferencax < 0) {
                            int difposi = diferencax * (-1);
                            int num = SIZES._16 - difposi;
                            mapX -= (num - 1);
                            for (int a = 0; a < quantidade; a++) {
                                inimigos[a].x += (num - 1);
                            }
                        }
                        if (diferencax > 0) {
                            int num = SIZES._16 - diferencax;
                            mapX += (num - 1);
                            for (int a = 0; a < quantidade; a++) {
                                inimigos[a].x -= (num - 1);
                            }
                        }
                    }
                }
            }

            for (int j = 0; j < caixo.length; j++) {
                if (!caixo[j].destroyed) {
                    int difx, dify;
                    int diferencax, diferencay;
                    diferencax = carro.x - caixo[j].x;
                    diferencay = carro.y - caixo[j].y;
                    if ((diferencax > -SIZES._16) && (diferencax < SIZES._16) && (diferencay > -SIZES._16)
                            && (diferencay < SIZES._16)) {
                        if (diferencax < 0) {
                            difx = diferencax * (-1);
                        } else {
                            difx = diferencax;
                        }
                        if (diferencay < 0) {
                            dify = diferencay * (-1);
                        } else {
                            dify = diferencay;
                        }
                        if (difx > dify) {
                            if (diferencax < 0) {
                                int difposi = diferencax * (-1);
                                int num = SIZES._16 - difposi;
                                mapX -= (num - 1);
                                caixo[j].durabilidade -= 2.0;
                                cardamage += 0.1;
                                for (int a = 0; a < quantidade; a++) {
                                    inimigos[a].x += (num - 1);
                                }
                            }
                            if (diferencax > 0) {
                                int num = SIZES._16 - diferencax;
                                mapX += (num - 1);
                                caixo[j].durabilidade -= 2.0;
                                cardamage += 0.1;
                                for (int a = 0; a < quantidade; a++) {
                                    inimigos[a].x -= (num - 1);
                                }
                            }
                        } else if (difx < dify) {
                            if (diferencay < 0) {
                                int difposi = diferencay * (-1);
                                int num = SIZES._16 - difposi;
                                mapY -= (num - 1);
                                caixo[j].durabilidade -= 2.0;
                                cardamage += 0.1;
                                for (int a = 0; a < quantidade; a++) {
                                    inimigos[a].y += (num - 1);
                                }
                            }
                            if (diferencay > 0) {
                                int num = SIZES._16 - diferencay;
                                mapY += (num - 1);
                                caixo[j].durabilidade -= 2.0;
                                cardamage += 0.1;
                                for (int a = 0; a < quantidade; a++) {
                                    inimigos[a].y -= (num - 1);
                                }
                            }
                        } else if (difx == dify) {
                            if (diferencay < 0) {
                                int difposi = diferencay * (-1);
                                int num = SIZES._16 - difposi;
                                mapY -= (num - 1);
                                caixo[j].durabilidade -= 2.0;
                                cardamage += 0.1;
                                for (int a = 0; a < quantidade; a++) {
                                    inimigos[a].y += (num - 1);
                                }
                            }
                            if (diferencay > 0) {
                                int num = SIZES._16 - diferencay;
                                mapY += (num - 1);
                                caixo[j].durabilidade -= 2.0;
                                cardamage += 0.1;
                                for (int a = 0; a < quantidade; a++) {
                                    inimigos[a].y -= (num - 1);
                                }
                            }
                            if (diferencax < 0) {
                                int difposi = diferencax * (-1);
                                int num = SIZES._16 - difposi;
                                mapX -= (num - 1);
                                caixo[j].durabilidade -= 2.0;
                                cardamage += 0.1;
                                for (int a = 0; a < quantidade; a++) {
                                    inimigos[a].x += (num - 1);
                                }
                            }
                            if (diferencax > 0) {
                                int num = SIZES._16 - diferencax;
                                mapX += (num - 1);
                                caixo[j].durabilidade -= 2.0;
                                cardamage += 0.1;
                                for (int a = 0; a < quantidade; a++) {
                                    inimigos[a].x -= (num - 1);
                                }
                            }
                        }
                    }
                    if (caixo[j].durabilidade <= 0) {
                        caixo[j].destroyed = true;
                    }
                }
            }

            for (int j = 0; j < destruidoscar.length; j++) {
                if (!destruidoscar[j].exploded) {
                    int difx, dify;
                    int diferencax, diferencay;
                    diferencax = carro.x - destruidoscar[j].x;
                    diferencay = carro.y - destruidoscar[j].y;
                    if ((diferencax > -SIZES._16) && (diferencax < SIZES._16) && (diferencay > -SIZES._16)
                            && (diferencay < SIZES._16)) {
                        if (diferencax < 0) {
                            difx = diferencax * (-1);
                        } else {
                            difx = diferencax;
                        }
                        if (diferencay < 0) {
                            dify = diferencay * (-1);
                        } else {
                            dify = diferencay;
                        }
                        if (difx > dify) {
                            if (diferencax < 0) {
                                int difposi = diferencax * (-1);
                                int num = SIZES._16 - difposi;
                                mapX -= (num - 1);
                                destruidoscar[j].durabilidade -= 2.0;
                                cardamage += 0.2;
                                for (int a = 0; a < quantidade; a++) {
                                    inimigos[a].x += (num - 1);
                                }
                            }
                            if (diferencax > 0) {
                                int num = SIZES._16 - diferencax;
                                mapX += (num - 1);
                                destruidoscar[j].durabilidade -= 2.0;
                                cardamage += 0.2;
                                for (int a = 0; a < quantidade; a++) {
                                    inimigos[a].x -= (num - 1);
                                }
                            }
                        } else if (difx < dify) {
                            if (diferencay < 0) {
                                int difposi = diferencay * (-1);
                                int num = SIZES._16 - difposi;
                                mapY -= (num - 1);
                                destruidoscar[j].durabilidade -= 2.0;
                                cardamage += 0.2;
                                for (int a = 0; a < quantidade; a++) {
                                    inimigos[a].y += (num - 1);
                                }
                            }
                            if (diferencay > 0) {
                                int num = SIZES._16 - diferencay;
                                mapY += (num - 1);
                                destruidoscar[j].durabilidade -= 2.0;
                                cardamage += 0.2;
                                for (int a = 0; a < quantidade; a++) {
                                    inimigos[a].y -= (num - 1);
                                }
                            }
                        } else if (difx == dify) {
                            if (diferencay < 0) {
                                int difposi = diferencay * (-1);
                                int num = SIZES._16 - difposi;
                                mapY -= (num - 1);
                                destruidoscar[j].durabilidade -= 2.0;
                                cardamage += 0.2;
                                for (int a = 0; a < quantidade; a++) {
                                    inimigos[a].y += (num - 1);
                                }
                            }
                            if (diferencay > 0) {
                                int num = SIZES._16 - diferencay;
                                mapY += (num - 1);
                                destruidoscar[j].durabilidade -= 2.0;
                                cardamage += 0.2;
                                for (int a = 0; a < quantidade; a++) {
                                    inimigos[a].y -= (num - 1);
                                }
                            }
                            if (diferencax < 0) {
                                int difposi = diferencax * (-1);
                                int num = SIZES._16 - difposi;
                                mapX -= (num - 1);
                                destruidoscar[j].durabilidade -= 2.0;
                                cardamage += 0.2;
                                for (int a = 0; a < quantidade; a++) {
                                    inimigos[a].x += (num - 1);
                                }
                            }
                            if (diferencax > 0) {
                                int num = SIZES._16 - diferencax;
                                mapX += (num - 1);
                                destruidoscar[j].durabilidade -= 2.0;
                                cardamage += 0.2;
                                for (int a = 0; a < quantidade; a++) {
                                    inimigos[a].x -= (num - 1);
                                }
                            }
                        }
                    }
                    if (destruidoscar[j].durabilidade <= 0) {
                        destruidoscar[j].exploded = true;
                        cardamage += 7.5;
                    }
                }
            }

        }
    }

    public boolean irdireita = false, iresquerda = false, irbaixo = false, ircima = false;
    public boolean irdireita2 = false, iresquerda2 = false, irbaixo2 = false, ircima2 = false;

    @Override
    public void keyReleased(KeyEvent e) {
        if (telaatual.equals("game") || telaatual.equals("multiplayer")) {
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                irbaixo = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                irbaixo2 = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                ircima = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_W) {
                ircima2 = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                irdireita = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_D) {
                irdireita2 = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                iresquerda = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_A) {
                iresquerda2 = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                ativado = false;
                propcar = 0;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (telaatual.equals("game")) {

            }
            if (telaatual.equals("pause")) {
                if ((e.getY() >= 110 && e.getY() <= 110 + 29) && (e.getX() >= 320 && e.getY() <= 320 + 160)) {
                    if (DEBUGMODE) {
                        TA = new TextAttributes(Color.BLUE);
                        DEBUGCONSOLE.setTextAttributes(TA);
                        System.out.println("Voltando ao Jogo... ;)");
                    }
                    yep = 1;
                }

                if ((e.getY() >= 180 && e.getX() <= 180 + 29) && (e.getX() >= 320 && e.getX() <= 320 + 160)) {
                    if (DEBUGMODE) {
                        TA = new TextAttributes(Color.YELLOW);
                        DEBUGCONSOLE.setTextAttributes(TA);
                        System.out.println("Voltando ao Menu");
                    }
                    yep = 0;
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public void setDebugMode(boolean ops) {
        DEBUGMODE = ops;
    }

    public boolean getDebugMode() {
        return DEBUGMODE;
    }

    private void creditos() {
        //@todo remake dos créditos usando arquivos
        telaatual = "creditos";
        int variacao = 255 / (FPS / 3);
        int alpha = 255;
        while (alpha > 0) {
            bbg.drawImage(fundoIm, 0, 0, janelaW, janelaH, this);
            bbg.setColor(new Color(0, 0, 0, 240));
            bbg.fillRect(SIZES._100, 0, janelaW - SIZES._200, janelaH);
            bbg.setColor(new Color(127, 180, 127, 215));
            bbg.drawRect(SIZES._100, 1, janelaW - SIZES._200, janelaH - 2);
            bbg.setColor(new Color(0, 0, 0, alpha));
            bbg.fillRect(0, 0, janelaW, janelaH);
            g.drawImage(backBuffer, 0, 0, this.getWidth(), this.getHeight(), this);
            try {
                Thread.sleep(1000 / FPS);
            } catch (InterruptedException ex) {
            }
            alpha -= variacao;
        }
        alpha = 255;
        boolean sobe_alpha = false;
        int tamanho_creditos = prepare_creditos(SIZES._182) + SIZES._36;
        for (int i = -66; i <= (janelaH + tamanho_creditos); i += SIZES._1) {
            if (yep == 0) {
                menuEsquecido = true;
                mainMenu();
            }
            bbg.drawImage(fundoIm, 0, 0, janelaW, janelaH, this);
            bbg.setColor(new Color(0, 0, 0, 240));
            bbg.fillRect(SIZES._100, 0, janelaW - SIZES._200, janelaH);
            //bbg.setColor(new Color(127, 180, 127, 215));
            //bbg.drawRect(SIZES._100, 1, janelaW - SIZES._200, janelaH - 2);
            if (alinhandotextos) {
                bbg.setColor(Color.WHITE);
                bbg.drawLine(janelaW / 2, 0, janelaW / 2, janelaH);
            }
            bbg.setColor(Color.blue);
            bbg.setFont(Fontes.RUFA.deriveFont(Font.PLAIN, SIZES._60));
            int gameTitleWidth = bbg.getFontMetrics().stringWidth("CAR");
            bbg.drawString("CAR", ((janelaW / 2) - (gameTitleWidth / 2)), (janelaH) - i);
            bbg.setColor(Color.red);
            bbg.setFont(Fontes.TIMESBD.deriveFont(Font.BOLD, SIZES._15));
            bbg.drawString("THE GAME", ((janelaW / 2) - (bbg.getFontMetrics().stringWidth("THE GAME") / 2)), ((janelaH) + SIZES._24) - i);

            /**
             * SOMBRA DO LOGO DOS CRÉDITOS
             */
            bbg.setColor(new Color(255, 255, 255, alpha / 5));// BLUE
            bbg.setFont(Fontes.LIBEL.deriveFont(Font.BOLD, (int) (SIZES._68 * 1.1)));
            bbg.drawString("NONACEPT", ((janelaW / 2) - (bbg.getFontMetrics().stringWidth("NONACEPT") / 2)), ((janelaH) + SIZES._130) - i - SIZES._8);
            bbg.setColor(new Color(255, 255, 255, alpha));// BLUE
            bbg.setFont(Fontes.LIBEL.deriveFont(Font.BOLD, SIZES._68));
            bbg.drawString("NONACEPT", ((janelaW / 2) - (bbg.getFontMetrics().stringWidth("NONACEPT") / 2)), ((janelaH) + SIZES._130) - i - SIZES._10);
            //blocos de crédito
            //bbg.drawImage(draw_creditos(janelaH + SIZES._182, i), 0, 0, this);
            draw_creditos(janelaH + SIZES._182, i, bbg);
            //fim dos créditos
            bbg.setColor(Color.WHITE);
            bbg.setFont(Fontes.ARIAL.deriveFont(Font.BOLD, SIZES._14));
            bbg.drawString("Car The Game " + ver + ", Nonaginta Septem Softwares 2014-2020.", ((janelaW / 2) - (bbg.getFontMetrics().
                    stringWidth("Car The Game " + ver + ", Nonaginta Septem Softwares 2014-2020.") / 2)), ((janelaH) + tamanho_creditos + SIZES._30) - i);// 43

            g.drawImage(backBuffer, 0, 0, this.getWidth(), this.getHeight(), this);
            try {
                Thread.sleep(1000 / FPS);
            } catch (InterruptedException ex) {
            }
            if ((i % 2 == 0) && ((janelaH + SIZES._130 - i) > -10)) {
                if (sobe_alpha) {
                    alpha += 10;
                } else {
                    alpha -= 10;
                }
                if (alpha >= 250) {
                    alpha = 250;
                    sobe_alpha = false;
                }
                if (alpha <= 100) {
                    alpha = 100;
                    sobe_alpha = true;
                }
                if ((janelaH + SIZES._130 - i) < 0) {
                    sobe_alpha = true;
                }
            }
        }
        yep = 0;
        menuEsquecido = true;
        mainMenu();
    }

    private int prepare_creditos(int base) {
        int valor = 0;
        for (int i = 0; i < credMsg.blocos.length; i++) {
            valor += SIZES._48;
            for (int j = 0; j < credMsg.blocos[i].nomes.length; j++) {
                valor += SIZES._22;
            }
        }
        return valor + base;
    }

    private void draw_creditos(int basey, int mody, Graphics2D g2d) {
        //BufferedImage creditos_img = new BufferedImage(janelaW, janelaH, BufferedImage.TYPE_INT_ARGB);
        int space_titulo = SIZES._48, space_nomes = SIZES._22;
        //Graphics2D gc = (Graphics2D) creditos_img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, bbg.getRenderingHint(RenderingHints.KEY_ANTIALIASING));
        g2d.setColor(fonte);
        int lasty = basey;
        int string_width = 0;
        for (int i = 0; i < credMsg.blocos.length; i++) {
            g2d.setFont(Fontes.DIALOG.deriveFont(Font.BOLD, SIZES._20));
            string_width = g2d.getFontMetrics().stringWidth(credMsg.blocos[i].titulo);
            g2d.drawString(credMsg.blocos[i].titulo, (janelaW / 2) - (string_width / 2), (lasty + space_titulo) - mody);
            lasty += space_titulo;
            for (int j = 0; j < credMsg.blocos[i].nomes.length; j++) {
                g2d.setFont(Fontes.ARIAL.deriveFont(Font.PLAIN, SIZES._18));
                string_width = g2d.getFontMetrics().stringWidth(credMsg.blocos[i].nomes[j]);
                g2d.drawString(credMsg.blocos[i].nomes[j], (janelaW / 2) - (string_width / 2), (lasty + space_nomes) - mody);
                lasty += space_nomes;
            }
        }
        //return creditos_img;
    }

    private void fecharGame() {

        double passo = masterVolume / 60;
        int alpha = 25;
        for (int i = 0; i <= 90; i++) {
            masterVolume -= passo;
            masterVolFloat = (float) (Math.log(masterVolume) / Math.log(10) * 20);
            player.setVol(masterVolFloat, true);
            if (i > 60 && alpha < 255) {
                alpha += 5;
            }
            if (i >= 30) {
                bbg.setColor(new Color(10, 10, 10, alpha));
                bbg.fillRect(0, 0, janelaW, janelaH);
                g.drawImage(backBuffer, 0, 0, this.getWidth(), this.getHeight(), this);
            }
            try {
                Thread.sleep(1000 / FrPS._90);
            } catch (InterruptedException ex) {
            }
        }
        System.exit(0);
    }

    void nextLvl() {
        lvlAtual++;
        if (DEBUGMODE) {
            TA = new TextAttributes(Color.GREEN);
            DEBUGCONSOLE.setTextAttributes(TA);
            System.out.println("CarTheGame.Main.nextLvl() " + lvlAtual);
        }
        try {
            player.stop();
            //bgm.stop();

            pintura = new BufferedImage(janelaW, janelaH, BufferedImage.TYPE_INT_RGB);
            li.setTipoLoad(2, this);
            li.setLvl(lvlAtual);
            lit = new Thread(li);
            int fl = 0, rue = 0;
            lit.start();
            do {
                bbg.drawImage(fundoIm, 0, 0, janelaW, janelaH, Color.black, this);
                bbg.setColor(new Color(Color.LIGHT_GRAY.getRed(), Color.LIGHT_GRAY.getGreen(),
                        Color.LIGHT_GRAY.getBlue(), 127));
                bbg.fillRect(0, 0, janelaW, janelaH);
                ImageIcon iili = new ImageIcon("arquivos/Images/ld/" + fl + ".cli");
                bbg.setColor(new Color(255, 255, 255));
                bbg.fillRoundRect(-5, 35, janelaW + 5, 32, 5, 5);
                bbg.drawImage(iili.getImage(), 5, 35, this);
                bbg.setFont(Fontes.LIBEL.deriveFont(Font.PLAIN, 20));
                bbg.setColor(Color.DARK_GRAY);
                bbg.drawString(iMsg.carregando.texto, iMsg.carregando.posX, 60);
                g.drawImage(backBuffer, 0, 0, this.getWidth(), this.getHeight(), this);
                try {
                    Thread.sleep(1000 / FrPS._30);
                } catch (InterruptedException ex) {
                    if (DEBUGMODE) {
                        TA = new TextAttributes(Color.RED);
                        DEBUGCONSOLE.setTextAttributes(TA);
                        System.out.println("Deu Ruim!");
                    }
                    System.exit(0);
                }
                fl++;
                rue++;
                if (fl >= 8) {
                    fl = 0;
                }
            } while (li.loadCompleto() == false || rue < 30);
            lit.stop();
            if (DEBUGMODE) {
                TA = new TextAttributes(Color.BLUE);
                DEBUGCONSOLE.setTextAttributes(TA);
                System.out.println("Vamos tocar uma outra musica... Se vocÃª nÃ£o gostar aperte T para tentar outra");
            }
            if (!noAnimacao) {
                framesApresen = 240;
                showLvlInfo(iMsg.lvlApresenta[0].texto);
            }
            fadeOut();
            sortMus(1);
            if (yep == 6) {
                yep = 1;
                game();
            } else {
                if (yep == 0) {
                    menuEsquecido = true;
                    mainMenu();
                }
            }
        } catch (Exception ex) {
            if (DEBUGMODE) {
                TA = new TextAttributes(Color.RED);
                DEBUGCONSOLE.setTextAttributes(TA);
                System.out.println("**" + ex.getLocalizedMessage() + "\n" + ex.getMessage() + "**");
            }
        }
    }

    private int framesApresen;

    private void showLvlInfo(String msg) {
        telaatual = "apresentacao";
        if (DEBUGMODE) {
            TA = new TextAttributes(Color.GREEN);
            DEBUGCONSOLE.setTextAttributes(TA);
            System.out.println("CarTheGame.Main.showLvlInfo()");
        }
        int timeRestante;
        int alpha1 = 1, alpha2 = 1, alpha3 = 10, deslize = 150;
        do {
            bbg.drawImage(fundoIm, 0, 0, janelaW, janelaH, this);
            bbg.setColor(new Color(10, 10, 10, alpha3));
            bbg.fillRect(0, 0, janelaW, janelaH);
            bbg.setFont(Fontes.LIBEL.deriveFont(Font.BOLD, 25));
            bbg.setColor(new Color(255, 255, 255, alpha1));
            bbg.drawString(msg, (int) ((35 - deslize) * MODRESOL), (int) (90 * MODRESOL));
            bbg.setFont(Fontes.LIBEL.deriveFont(Font.PLAIN, 22));
            bbg.drawString(iMsg.nivel.texto + lvlAtual, (int) (50 * MODRESOL), (int) (150 * MODRESOL));
            bbg.setColor(new Color(220, 220, 220, alpha2));
            bbg.drawString(lvlName, (int) (50 * MODRESOL), (int) (180 * MODRESOL));
            // bbg.setFont(Fontes.ARIAL.deriveFont( Font.PLAIN, 18));
            // bbg.drawString(lvlDescri, (int) (35 * modResol), (int) (230 * modResol));
            bbg.setFont(Fontes.RUFA.deriveFont(Font.PLAIN, 52));
            bbg.setColor(new Color(alpha1, 11, 11, alpha2));

            timeRestante = (480 - framesApresen) / 80;
            if (timeRestante == 0) {
                bbg.drawString(iMsg.ja.texto, (int) (janelaW - (100 * MODRESOL)), (int) (janelaH - (100 * MODRESOL)));
            } else {
                bbg.drawString("0" + String.valueOf(timeRestante), (int) (janelaW - (100 * MODRESOL)),
                        (int) (janelaH - (100 * MODRESOL)));
            }

            alpha1 += 3;
            g.drawImage(backBuffer, 0, 0, this.getWidth(), this.getHeight(), this);
            if (alpha1 > 130) {
                alpha2 += 2;
            }
            if (alpha1 > 255) {
                alpha1 = 255;
            }
            if (alpha2 > 255) {
                alpha2 = 255;
            }
            if (alpha3 < 245) {
                alpha3 += 10;
            }
            if (alpha3 > 245) {
                alpha3 = 245;
            }
            if (deslize > 0) {
                deslize -= 13;
            } else if (deslize < 0) {
                deslize = 0;
            }
            try {
                Thread.sleep(1000 / 80);
            } catch (InterruptedException ex) {
            }
            framesApresen++;
        } while (framesApresen < 480);
    }

    @Override
    public void focusGained(FocusEvent e) {
    }

    @Override
    public void focusLost(FocusEvent e) {

        if (telaatual.equals("game")) {
            yep = 2;
        }
    }

    private int tutorialPage;
    private boolean skipableTutor = false, playableTutor = true;

    private void viewTutorial() {
        telaatual = "tutorial";
        tutorialPage = 0;
        int fadeinicio = (int) ((janelaH / MODRESOL));
        int fadedesejado = fadeinicio;
        double modResolTuto = janelaH / 720.0;
        BufferedImage buff2 = new BufferedImage((int) (700 * MODRESOL), janelaH, BufferedImage.TYPE_INT_RGB);
        int tammenus = buff2.getWidth() / 5;
        Graphics2D bbg22 = (Graphics2D) buff2.getGraphics();
        if (antiAliasing) {
            bbg22.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        do {
            int alpha = 0;
            bbg.drawImage(fundoIm, 0, 0, janelaW, janelaH, this);
            bbg.setColor(new Color(10, 10, 10, 150));
            bbg.fillRect(0, 0, janelaW, janelaH);

            for (int i = (int) ((janelaH / 2) - (100 * MODRESOL)); i < janelaH; i += 2) {
                alpha++;
                if (alpha > 220 && i < (janelaH - (100 * MODRESOL))) {
                    alpha = 220;
                } else if (alpha > 250) {
                    alpha = 250;
                }
                bbg.setColor(new Color(0, 0, 0, alpha));
                bbg.fillRect(0, i, janelaW, 2);
            }

            // TUTORIAL
            bbg22.drawImage(fundoIm, -64, -64, buff2.getWidth() + 128, buff2.getHeight() + 128, this);
            bbg22.setColor(new Color(10, 10, 10, 200));
            bbg22.fillRect(0, 0, buff2.getWidth(), buff2.getHeight());
            bbg22.setColor(new Color(200, 200, 200, 50));
            bbg22.fillRect(0, (int) (38 * modResolTuto), buff2.getWidth(), (int) (70 * modResolTuto));
            bbg22.setFont(Fontes.LIBEL.deriveFont(Font.BOLD, (int) (22 * modResolTuto)));
            bbg22.setColor(Color.WHITE);
            bbg22.drawString(tutoMsg.welcome.texto, (int) (10 * modResolTuto), (int) (65 * modResolTuto));
            bbg22.setFont(Fontes.LIBEL.deriveFont(Font.PLAIN, (int) (18 * modResolTuto)));
            bbg22.setColor(new Color(200, 200, 200, 90));
            bbg22.fillRoundRect(tammenus * tutorialPage, (int) (75 * modResolTuto), tammenus, (int) (33 * modResolTuto),
                    (int) (5 * modResolTuto), (int) (5 * modResolTuto));

            switch (tutorialPage) {
                case 0:// BÃ�SICOS
                    fadedesejado = 331;

                    bbg22.setColor(new Color(250, 250, 250));
                    bbg22.drawString(tutoMsg.basico.texto, (int) (((tammenus) / 2) - (tutoMsg.basico.posX * modResolTuto)),
                            (int) (100 * modResolTuto));
                    bbg22.setColor(new Color(150, 150, 150));
                    bbg22.drawString(tutoMsg.controles.texto,
                            (int) (((tammenus * 1) + (tammenus / 2)) - (tutoMsg.controles.posX * modResolTuto)),
                            (int) (100 * modResolTuto));
                    bbg22.drawString(tutoMsg.itensAjuda.texto,
                            (int) (((tammenus * 2) + (tammenus / 2)) - (tutoMsg.itensAjuda.posX * modResolTuto)),
                            (int) (100 * modResolTuto));
                    bbg22.drawString(tutoMsg.estruturas.texto,
                            (int) (((tammenus * 3) + (tammenus / 2)) - (tutoMsg.estruturas.posX * modResolTuto)),
                            (int) (100 * modResolTuto));
                    bbg22.drawString(tutoMsg.objetos.texto,
                            (int) (((tammenus * 4) + (tammenus / 2)) - (tutoMsg.objetos.posX * modResolTuto)),
                            (int) (100 * modResolTuto));
                    bbg22.setColor(new Color(0, 0, 0, 45));
                    bbg22.fillRect(0, (int) (108 * modResolTuto), buff2.getWidth(),
                            (int) (buff2.getHeight() - (108 * modResolTuto)));

                    // VOCÊ
                    bbg22.setColor(new Color(75, 75, 75, 75));
                    bbg22.fillRoundRect((int) (10 * modResolTuto), (int) (118 * modResolTuto),
                            (int) ((buff2.getWidth()) - (20 * modResolTuto)), (int) (90 * modResolTuto),
                            (int) (7 * modResolTuto), (int) (7 * modResolTuto));
                    bbg22.setColor(new Color(240, 240, 240));
                    bbg22.drawImage(new ImageIcon("arquivos/cars/micro/texturas/0.cpn").getImage(),
                            (int) (25 * modResolTuto), (int) (147 * modResolTuto), (int) ((20 * modResolTuto) * 1.6),
                            (int) ((20 * modResolTuto) * 1.6), this);
                    bbg22.drawString(tutoMsg.voce[0].texto,
                            (int) ((buff2.getWidth() / 2) - (tutoMsg.voce[0].posX * modResolTuto)),
                            (int) (172 * modResolTuto));
                    // INIMIGO
                    bbg22.setColor(new Color(75, 75, 75, 75));
                    bbg22.fillRoundRect((int) (10 * modResolTuto), (int) ((216 * modResolTuto)),
                            (int) ((buff2.getWidth()) - (20 * modResolTuto)), (int) (90 * modResolTuto),
                            (int) (7 * modResolTuto), (int) (7 * modResolTuto));
                    bbg22.setColor(new Color(240, 240, 240));
                    bbg22.drawImage(new ImageIcon("arquivos/Images/ini.cpn").getImage(), (int) (25 * modResolTuto),
                            (int) (245 * modResolTuto), (int) ((20 * modResolTuto) * 1.6),
                            (int) ((20 * modResolTuto) * 1.6), this);
                    bbg22.drawString(tutoMsg.inimigo[0].texto,
                            (int) ((buff2.getWidth() / 2) - (tutoMsg.inimigo[0].posX * modResolTuto)),
                            (int) (260 * modResolTuto));
                    bbg22.drawString(tutoMsg.inimigo[1].texto,
                            (int) ((buff2.getWidth() / 2) - (tutoMsg.inimigo[1].posX * modResolTuto)),
                            (int) (288 * modResolTuto));

                    break;
                case 1:// CONTROLES
                    fadedesejado = 429;

                    bbg22.setColor(new Color(150, 150, 150));
                    bbg22.drawString(tutoMsg.basico.texto, (int) (((tammenus) / 2) - (tutoMsg.basico.posX * modResolTuto)),
                            (int) (100 * modResolTuto));
                    bbg22.setColor(new Color(250, 250, 250));
                    bbg22.drawString(tutoMsg.controles.texto,
                            (int) (((tammenus * 1) + (tammenus / 2)) - (tutoMsg.controles.posX * modResolTuto)),
                            (int) (100 * modResolTuto));
                    bbg22.setColor(new Color(150, 150, 150));
                    bbg22.drawString(tutoMsg.itensAjuda.texto,
                            (int) (((tammenus * 2) + (tammenus / 2)) - (tutoMsg.itensAjuda.posX * modResolTuto)),
                            (int) (100 * modResolTuto));
                    bbg22.drawString(tutoMsg.estruturas.texto,
                            (int) (((tammenus * 3) + (tammenus / 2)) - (tutoMsg.estruturas.posX * modResolTuto)),
                            (int) (100 * modResolTuto));
                    bbg22.drawString(tutoMsg.objetos.texto,
                            (int) (((tammenus * 4) + (tammenus / 2)) - (tutoMsg.objetos.posX * modResolTuto)),
                            (int) (100 * modResolTuto));
                    bbg22.setColor(new Color(0, 0, 0, 45));
                    bbg22.fillRect(0, (int) (108 * modResolTuto), buff2.getWidth(),
                            (int) (buff2.getHeight() - (108 * modResolTuto)));

                    // MOVIMENTOS
                    bbg22.setColor(new Color(75, 75, 75, 75));
                    bbg22.fillRoundRect((int) (10 * modResolTuto), (int) (118 * modResolTuto),
                            (int) ((buff2.getWidth()) - (20 * modResolTuto)), (int) (90 * modResolTuto),
                            (int) (7 * modResolTuto), (int) (7 * modResolTuto));
                    bbg22.setColor(new Color(240, 240, 240));
                    bbg22.drawImage(new ImageIcon("arquivos/Images/tutor/sc.cpn").getImage(), (int) (52 * modResolTuto),
                            (int) (129 * modResolTuto), (int) ((20 * modResolTuto) * 1.6),
                            (int) ((20 * modResolTuto) * 1.6), this);
                    bbg22.drawImage(new ImageIcon("arquivos/Images/tutor/se.cpn").getImage(), (int) (17 * modResolTuto),
                            (int) (164 * modResolTuto), (int) ((20 * modResolTuto) * 1.6),
                            (int) ((20 * modResolTuto) * 1.6), this);
                    bbg22.drawImage(new ImageIcon("arquivos/Images/tutor/sb.cpn").getImage(), (int) (52 * modResolTuto),
                            (int) (164 * modResolTuto), (int) ((20 * modResolTuto) * 1.6),
                            (int) ((20 * modResolTuto) * 1.6), this);
                    bbg22.drawImage(new ImageIcon("arquivos/Images/tutor/sd.cpn").getImage(), (int) (87 * modResolTuto),
                            (int) (164 * modResolTuto), (int) ((20 * modResolTuto) * 1.6),
                            (int) ((20 * modResolTuto) * 1.6), this);
                    bbg22.drawImage(new ImageIcon("arquivos/Images/tutor/w.cpn").getImage(),
                            (int) (buff2.getWidth() - (52 * modResolTuto) - ((20 * modResolTuto) * 1.6)),
                            (int) (129 * modResolTuto), (int) ((20 * modResolTuto) * 1.6),
                            (int) ((20 * modResolTuto) * 1.6), this);
                    bbg22.drawImage(new ImageIcon("arquivos/Images/tutor/d.cpn").getImage(),
                            (int) (buff2.getWidth() - (17 * modResolTuto) - ((20 * modResolTuto) * 1.6)),
                            (int) (164 * modResolTuto), (int) ((20 * modResolTuto) * 1.6),
                            (int) ((20 * modResolTuto) * 1.6), this);
                    bbg22.drawImage(new ImageIcon("arquivos/Images/tutor/s.cpn").getImage(),
                            (int) (buff2.getWidth() - (52 * modResolTuto) - ((20 * modResolTuto) * 1.6)),
                            (int) (164 * modResolTuto), (int) ((20 * modResolTuto) * 1.6),
                            (int) ((20 * modResolTuto) * 1.6), this);
                    bbg22.drawImage(new ImageIcon("arquivos/Images/tutor/a.cpn").getImage(),
                            (int) (buff2.getWidth() - (87 * modResolTuto) - ((20 * modResolTuto) * 1.6)),
                            (int) (164 * modResolTuto), (int) ((20 * modResolTuto) * 1.6),
                            (int) ((20 * modResolTuto) * 1.6), this);
                    bbg22.drawString(tutoMsg.controlsWASD[0].texto,
                            (int) ((buff2.getWidth() / 2) - (tutoMsg.controlsWASD[0].posX * modResolTuto)),
                            (int) (172 * modResolTuto));
                    // CONTROLE NITRO
                    bbg22.setColor(new Color(75, 75, 75, 75));
                    bbg22.fillRoundRect((int) (10 * modResolTuto), (int) ((216 * modResolTuto)),
                            (int) ((buff2.getWidth()) - (20 * modResolTuto)), (int) (90 * modResolTuto),
                            (int) (7 * modResolTuto), (int) (7 * modResolTuto));
                    bbg22.setColor(new Color(240, 240, 240));
                    bbg22.drawImage(new ImageIcon("arquivos/Images/tutor/sh.cpn").getImage(), (int) (32 * modResolTuto),
                            (int) (241 * modResolTuto), (int) ((39 * modResolTuto) * 1.6),
                            (int) ((26 * modResolTuto) * 1.6), this);
                    bbg22.drawString(tutoMsg.controlsShift[0].texto,
                            (int) ((buff2.getWidth() / 2) - (tutoMsg.controlsShift[0].posX * modResolTuto)),
                            (int) (265 * modResolTuto));
                    // T
                    bbg22.setColor(new Color(75, 75, 75, 75));
                    bbg22.fillRoundRect((int) (10 * modResolTuto), (int) (314 * modResolTuto),
                            (int) ((buff2.getWidth()) - (20 * modResolTuto)), (int) (90 * modResolTuto),
                            (int) (7 * modResolTuto), (int) (7 * modResolTuto));
                    bbg22.setColor(new Color(240, 240, 240));
                    bbg22.drawImage(new ImageIcon("arquivos/Images/tutor/t.cpn").getImage(), (int) (38 * modResolTuto),
                            (int) (343 * modResolTuto), (int) ((20 * modResolTuto) * 1.6),
                            (int) ((20 * modResolTuto) * 1.6), this);
                    bbg22.drawString(tutoMsg.controlsT[0].texto,
                            (int) ((buff2.getWidth() / 2) - (tutoMsg.controlsT[0].posX * modResolTuto)),
                            (int) (367 * modResolTuto));

                    break;
                case 2:// ITENS
                    fadedesejado = 510;

                    bbg22.setColor(new Color(150, 150, 150));
                    bbg22.drawString(tutoMsg.basico.texto, (int) (((tammenus) / 2) - (tutoMsg.basico.posX * modResolTuto)),
                            (int) (100 * modResolTuto));
                    bbg22.drawString(tutoMsg.controles.texto,
                            (int) (((tammenus * 1) + (tammenus / 2)) - (tutoMsg.controles.posX * modResolTuto)),
                            (int) (100 * modResolTuto));
                    bbg22.setColor(new Color(250, 250, 250));
                    bbg22.drawString(tutoMsg.itensAjuda.texto,
                            (int) (((tammenus * 2) + (tammenus / 2)) - (tutoMsg.itensAjuda.posX * modResolTuto)),
                            (int) (100 * modResolTuto));
                    bbg22.setColor(new Color(150, 150, 150));
                    bbg22.drawString(tutoMsg.estruturas.texto,
                            (int) (((tammenus * 3) + (tammenus / 2)) - (tutoMsg.estruturas.posX * modResolTuto)),
                            (int) (100 * modResolTuto));
                    bbg22.drawString(tutoMsg.objetos.texto,
                            (int) (((tammenus * 4) + (tammenus / 2)) - (tutoMsg.objetos.posX * modResolTuto)),
                            (int) (100 * modResolTuto));
                    bbg22.setColor(new Color(0, 0, 0, 45));
                    bbg22.fillRect(0, (int) (108 * modResolTuto), buff2.getWidth(),
                            (int) (buff2.getHeight() - (108 * modResolTuto)));

                    // VIDA
                    bbg22.setColor(new Color(75, 75, 75, 75));
                    bbg22.fillRoundRect((int) (10 * modResolTuto), (int) (118 * modResolTuto),
                            (int) ((buff2.getWidth()) - (20 * modResolTuto)), (int) (90 * modResolTuto),
                            (int) (7 * modResolTuto), (int) (7 * modResolTuto));
                    bbg22.setColor(new Color(240, 240, 240));
                    bbg22.drawImage(new ImageIcon("arquivos/Images/life.cpn").getImage(), (int) (25 * modResolTuto),
                            (int) (147 * modResolTuto), (int) ((20 * modResolTuto) * 1.6),
                            (int) ((20 * modResolTuto) * 1.6), this);
                    bbg22.drawString(tutoMsg.vida[0].texto,
                            (int) ((buff2.getWidth() / 2) - (tutoMsg.vida[0].posX * modResolTuto)),
                            (int) (172 * modResolTuto));
                    // NITRO
                    bbg22.setColor(new Color(75, 75, 75, 75));
                    bbg22.fillRoundRect((int) (10 * modResolTuto), (int) ((216 * modResolTuto)),
                            (int) ((buff2.getWidth()) - (20 * modResolTuto)), (int) (90 * modResolTuto),
                            (int) (7 * modResolTuto), (int) (7 * modResolTuto));
                    bbg22.setColor(new Color(240, 240, 240));
                    bbg22.drawImage(new ImageIcon("arquivos/Images/nitro.cpn").getImage(), (int) (25 * modResolTuto),
                            (int) (245 * modResolTuto), (int) ((20 * modResolTuto) * 1.6),
                            (int) ((20 * modResolTuto) * 1.6), this);
                    bbg22.drawString(tutoMsg.nitro[0].texto,
                            (int) ((buff2.getWidth() / 2) - (tutoMsg.nitro[0].posX * modResolTuto)),
                            (int) (270 * modResolTuto));
                    // SNITRO
                    bbg22.setColor(new Color(75, 75, 75, 75));
                    bbg22.fillRoundRect((int) (10 * modResolTuto), (int) (306 * modResolTuto),
                            (int) ((buff2.getWidth()) - (20 * modResolTuto)), (int) (90 * modResolTuto),
                            (int) (7 * modResolTuto), (int) (7 * modResolTuto));
                    bbg22.setColor(new Color(240, 240, 240));
                    bbg22.drawImage(new ImageIcon("arquivos/Images/nitro+.cpn").getImage(), (int) (25 * modResolTuto),
                            (int) (335 * modResolTuto), (int) ((20 * modResolTuto) * 1.6),
                            (int) ((20 * modResolTuto) * 1.6), this);
                    bbg22.drawString(tutoMsg.sNitro[0].texto,
                            (int) ((buff2.getWidth() / 2) - (tutoMsg.sNitro[0].posX * modResolTuto)),
                            (int) (356 * modResolTuto));
                    bbg22.drawString(tutoMsg.sNitro[1].texto,
                            (int) ((buff2.getWidth() / 2) - (tutoMsg.sNitro[1].posX * modResolTuto)),
                            (int) (384 * modResolTuto));
                    // INVISIBILIDADE
                    bbg22.setColor(new Color(75, 75, 75, 75));
                    bbg22.fillRoundRect((int) (10 * modResolTuto), (int) (404 * modResolTuto),
                            (int) ((buff2.getWidth()) - (20 * modResolTuto)), (int) (90 * modResolTuto),
                            (int) (7 * modResolTuto), (int) (7 * modResolTuto));
                    bbg22.setColor(new Color(240, 240, 240));
                    bbg22.drawImage(new ImageIcon("arquivos/Images/invi.cpn").getImage(), (int) (25 * modResolTuto),
                            (int) (433 * modResolTuto), (int) ((20 * modResolTuto) * 1.6),
                            (int) ((20 * modResolTuto) * 1.6), this);
                    bbg22.drawString(tutoMsg.invisibilidade[0].texto,
                            (int) ((buff2.getWidth() / 2) - (tutoMsg.invisibilidade[0].posX * modResolTuto)),
                            (int) (458 * modResolTuto));
                    break;
                case 3:// ESTRUTURAS
                    fadedesejado = 459;

                    bbg22.setColor(new Color(150, 150, 150));
                    bbg22.drawString(tutoMsg.basico.texto, (int) (((tammenus) / 2) - (tutoMsg.basico.posX * modResolTuto)),
                            (int) (100 * modResolTuto));
                    bbg22.drawString(tutoMsg.controles.texto,
                            (int) (((tammenus * 1) + (tammenus / 2)) - (tutoMsg.controles.posX * modResolTuto)),
                            (int) (100 * modResolTuto));
                    bbg22.drawString(tutoMsg.itensAjuda.texto,
                            (int) (((tammenus * 2) + (tammenus / 2)) - (tutoMsg.itensAjuda.posX * modResolTuto)),
                            (int) (100 * modResolTuto));
                    bbg22.setColor(new Color(250, 250, 250));
                    bbg22.drawString(tutoMsg.estruturas.texto,
                            (int) (((tammenus * 3) + (tammenus / 2)) - (tutoMsg.estruturas.posX * modResolTuto)),
                            (int) (100 * modResolTuto));
                    bbg22.setColor(new Color(150, 150, 150));
                    bbg22.drawString(tutoMsg.objetos.texto,
                            (int) (((tammenus * 4) + (tammenus / 2)) - (tutoMsg.objetos.posX * modResolTuto)),
                            (int) (100 * modResolTuto));
                    bbg22.setColor(new Color(0, 0, 0, 45));
                    bbg22.fillRect(0, (int) (108 * modResolTuto), buff2.getWidth(),
                            (int) (buff2.getHeight() - (108 * modResolTuto)));

                    // PORTAIS
                    bbg22.setColor(new Color(75, 75, 75, 75));
                    bbg22.fillRoundRect((int) (10 * modResolTuto), (int) (118 * modResolTuto),
                            (int) ((buff2.getWidth()) - (20 * modResolTuto)), (int) (120 * modResolTuto),
                            (int) (7 * modResolTuto), (int) (7 * modResolTuto));
                    bbg22.setColor(new Color(240, 240, 240));
                    bbg22.drawImage(new ImageIcon("arquivos/Images/portal0.cpn").getImage(), (int) (15 * modResolTuto),
                            (int) (131 * modResolTuto), (int) ((20 * modResolTuto) * 1.4),
                            (int) ((20 * modResolTuto) * 1.4), this);
                    bbg22.drawImage(new ImageIcon("arquivos/Images/portal4.cpn").getImage(), (int) (15 * modResolTuto),
                            (int) (165 * modResolTuto), (int) ((20 * modResolTuto) * 1.4),
                            (int) ((20 * modResolTuto) * 1.4), this);
                    bbg22.drawImage(new ImageIcon("arquivos/Images/portal1.cpn").getImage(), (int) (49 * modResolTuto),
                            (int) (131 * modResolTuto), (int) ((20 * modResolTuto) * 1.4),
                            (int) ((20 * modResolTuto) * 1.4), this);
                    bbg22.drawImage(new ImageIcon("arquivos/Images/portal3.cpn").getImage(), (int) (49 * modResolTuto),
                            (int) (165 * modResolTuto), (int) ((20 * modResolTuto) * 1.4),
                            (int) ((20 * modResolTuto) * 1.4), this);
                    bbg22.drawImage(new ImageIcon("arquivos/Images/portal2.cpn").getImage(), (int) (32 * modResolTuto),
                            (int) (148 * modResolTuto), (int) ((20 * modResolTuto) * 1.4),
                            (int) ((20 * modResolTuto) * 1.4), this);
                    bbg22.drawString(tutoMsg.portal[0].texto,
                            (int) ((buff2.getWidth() / 2) - (tutoMsg.portal[0].posX * modResolTuto)),
                            (int) (166 * modResolTuto));
                    bbg22.drawString(tutoMsg.portal[1].texto,
                            (int) ((buff2.getWidth() / 2) - (tutoMsg.portal[1].posX * modResolTuto)),
                            (int) (184 * modResolTuto));
                    bbg22.drawImage(new ImageIcon("arquivos/Images/portale.cpn").getImage(), (int) (32 * modResolTuto),
                            (int) (205 * modResolTuto), (int) ((20 * modResolTuto) * 1.4),
                            (int) ((20 * modResolTuto) * 1.4), this);
                    bbg22.drawString(tutoMsg.portal[2].texto,
                            (int) ((buff2.getWidth() / 2) - (tutoMsg.portal[2].posX * modResolTuto)),
                            (int) (226 * modResolTuto));
                    // PAREDES
                    bbg22.setColor(new Color(75, 75, 75, 75));
                    bbg22.fillRoundRect((int) (10 * modResolTuto), (int) (246 * modResolTuto),
                            (int) ((buff2.getWidth()) - (20 * modResolTuto)), (int) (90 * modResolTuto),
                            (int) (7 * modResolTuto), (int) (7 * modResolTuto));
                    bbg22.setColor(new Color(240, 240, 240));
                    bbg22.drawImage(new ImageIcon("arquivos/Images/wall.cpn").getImage(), (int) (25 * modResolTuto),
                            (int) (275 * modResolTuto), (int) ((20 * modResolTuto) * 1.6),
                            (int) ((20 * modResolTuto) * 1.6), this);
                    bbg22.drawString(tutoMsg.parede[0].texto,
                            (int) ((buff2.getWidth() / 2) - (tutoMsg.parede[0].posX * modResolTuto)),
                            (int) (291 * modResolTuto));
                    bbg22.drawString(tutoMsg.parede[1].texto,
                            (int) ((buff2.getWidth() / 2) - (tutoMsg.parede[1].posX * modResolTuto)),
                            (int) (309 * modResolTuto));
                    // CHEGADA
                    bbg22.setColor(new Color(75, 75, 75, 75));
                    bbg22.fillRoundRect((int) (10 * modResolTuto), (int) (344 * modResolTuto),
                            (int) ((buff2.getWidth()) - (20 * modResolTuto)), (int) (90 * modResolTuto),
                            (int) (7 * modResolTuto), (int) (7 * modResolTuto));
                    bbg22.setColor(new Color(240, 240, 240));
                    bbg22.drawImage(new ImageIcon("arquivos/Images/chegada.cpn").getImage(), (int) (25 * modResolTuto),
                            (int) (373 * modResolTuto), (int) ((20 * modResolTuto) * 1.6),
                            (int) ((20 * modResolTuto) * 1.6), this);
                    bbg22.drawString(tutoMsg.chegada[0].texto,
                            (int) ((buff2.getWidth() / 2) - (tutoMsg.chegada[0].posX * modResolTuto)),
                            (int) (389 * modResolTuto));
                    bbg22.drawString(tutoMsg.chegada[1].texto,
                            (int) ((buff2.getWidth() / 2) - (tutoMsg.chegada[1].posX * modResolTuto)),
                            (int) (407 * modResolTuto));
                    break;
                case 4:// OBJETOS
                    fadedesejado = 331;

                    bbg22.setColor(new Color(150, 150, 150));
                    bbg22.drawString(tutoMsg.basico.texto, (int) (((tammenus) / 2) - (tutoMsg.basico.posX * modResolTuto)),
                            (int) (100 * modResolTuto));
                    bbg22.drawString(tutoMsg.controles.texto,
                            (int) (((tammenus * 1) + (tammenus / 2)) - (tutoMsg.controles.posX * modResolTuto)),
                            (int) (100 * modResolTuto));
                    bbg22.drawString(tutoMsg.itensAjuda.texto,
                            (int) (((tammenus * 2) + (tammenus / 2)) - (tutoMsg.itensAjuda.posX * modResolTuto)),
                            (int) (100 * modResolTuto));
                    bbg22.drawString(tutoMsg.estruturas.texto,
                            (int) (((tammenus * 3) + (tammenus / 2)) - (tutoMsg.estruturas.posX * modResolTuto)),
                            (int) (100 * modResolTuto));
                    bbg22.setColor(new Color(250, 250, 250));
                    bbg22.drawString(tutoMsg.objetos.texto,
                            (int) (((tammenus * 4) + (tammenus / 2)) - (tutoMsg.objetos.posX * modResolTuto)),
                            (int) (100 * modResolTuto));
                    bbg22.setColor(new Color(0, 0, 0, 45));
                    bbg22.fillRect(0, (int) (108 * modResolTuto), buff2.getWidth(),
                            (int) (buff2.getHeight() - (108 * modResolTuto)));

                    // CAIXOTE
                    bbg22.setColor(new Color(75, 75, 75, 75));
                    bbg22.fillRoundRect((int) (10 * modResolTuto), (int) (118 * modResolTuto),
                            (int) ((buff2.getWidth()) - (20 * modResolTuto)), (int) (90 * modResolTuto),
                            (int) (7 * modResolTuto), (int) (7 * modResolTuto));
                    bbg22.setColor(new Color(240, 240, 240));
                    bbg22.drawImage(new ImageIcon("arquivos/Images/cxc.cpn").getImage(), (int) (25 * modResolTuto),
                            (int) (147 * modResolTuto), (int) ((20 * modResolTuto) * 1.6),
                            (int) ((20 * modResolTuto) * 1.6), this);
                    bbg22.drawString(tutoMsg.caixote[0].texto,
                            (int) ((buff2.getWidth() / 2) - (tutoMsg.caixote[0].posX * modResolTuto)),
                            (int) (172 * modResolTuto));
                    // DESTRUIDOS
                    bbg22.setColor(new Color(75, 75, 75, 75));
                    bbg22.fillRoundRect((int) (10 * modResolTuto), (int) (216 * modResolTuto),
                            (int) ((buff2.getWidth()) - (20 * modResolTuto)), (int) (90 * modResolTuto),
                            (int) (7 * modResolTuto), (int) (7 * modResolTuto));
                    bbg22.setColor(new Color(240, 240, 240));
                    bbg22.drawImage(new ImageIcon("arquivos/Images/destroy.cpn").getImage(), (int) (25 * modResolTuto),
                            (int) (245 * modResolTuto), (int) ((20 * modResolTuto) * 1.6),
                            (int) ((20 * modResolTuto) * 1.6), this);
                    bbg22.drawString(tutoMsg.destruidos[0].texto,
                            (int) ((buff2.getWidth() / 2) - (tutoMsg.destruidos[0].posX * modResolTuto)),
                            (int) (265 * modResolTuto));
                    bbg22.drawString(tutoMsg.destruidos[1].texto,
                            (int) ((buff2.getWidth() / 2) - (tutoMsg.destruidos[1].posX * modResolTuto)),
                            (int) (281 * modResolTuto));
                    break;

            }

            alpha = 0;
            for (int j = 0; j < (25 * MODRESOL); j++) {
                if (fadeinicio > fadedesejado) {
                    fadeinicio--;
                } else if (fadeinicio < fadedesejado) {
                    fadeinicio++;
                }
            }
            for (int i = (int) (fadeinicio * MODRESOL); i < buff2.getHeight(); i++) {
                alpha++;
                if (alpha > 170 && i < (buff2.getHeight() - (150 * MODRESOL))) {
                    alpha = 170;
                } else if (alpha > 245) {
                    alpha = 245;
                }
                bbg22.setColor(new Color(0, 0, 0, alpha));
                bbg22.fillRect(0, i, buff2.getWidth(), 1);
            }

            bbg22.setColor(new Color(200, 200, 200, 20));
            bbg22.drawLine((int) (10 * modResolTuto), (int) (75 * modResolTuto),
                    (int) (buff2.getWidth() - (11 * modResolTuto)), (int) (75 * modResolTuto));
            bbg22.drawLine((int) (10 * modResolTuto), (int) (107 * modResolTuto),
                    (int) (buff2.getWidth() - (11 * modResolTuto)), (int) (107 * modResolTuto));
            bbg.drawImage(buff2, (int) ((janelaW - buff2.getWidth()) / 2), 0, buff2.getWidth(), buff2.getHeight(),
                    this);
            if (alinhandotextos) {
                bbg.setColor(Color.WHITE);
                bbg.drawLine(janelaW / 2, 0, janelaW / 2, janelaH);
            }

            bbg22.setFont(Fontes.LIBEL.deriveFont(Font.BOLD, (int) (14 * modResolTuto)));
            bbg22.drawString(tutoMsg.sair.texto, (int) (2 * modResolTuto),
                    (int) ((buff2.getHeight()) - (5 * modResolTuto)));

            g.drawImage(backBuffer, 0, 0, this.getWidth(), this.getHeight(), this);
            try {
                Thread.sleep(1000 / FPS);
            } catch (InterruptedException ex) {
                System.out.println("ERRO FATAL");
                System.exit(0);
            }
        } while (yep == 8);
        yep = nxtyep;
        switch (yep) {
            case 1:
                antgame();
                break;
            case 0:
                mainMenu();
                break;
        }
    }

    public void fadeOut() {
        for (int i = 0; i < 30; i++) {
            bbg.setColor(new Color(0, 0, 0, 33));
            bbg.fillRect(0, 0, janelaW, janelaH);
            g.drawImage(backBuffer, 0, 0, this.getWidth(), this.getHeight(), this);
            try {
                Thread.sleep(1000 / FrPS._90);
            } catch (InterruptedException ex) {
                System.exit(1);
            }
        }
    }

}
