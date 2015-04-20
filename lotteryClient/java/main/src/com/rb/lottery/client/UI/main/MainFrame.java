/**
 * @文件名称: MainFrame.java
 * @类路径:   com.rb.lottery.UI.main
 * @描述:     系统主界面
 * @作者:     robin
 * @时间:     2011-9-30 上午09:32:10
 * @版本:     1.0.0
 */
package com.rb.lottery.client.UI.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TreeMap;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.rb.lottery.client.UI.list.BasketListCellRenderer;
import com.rb.lottery.client.UI.list.BasketListModel;
import com.rb.lottery.client.UI.listener.BetTypeListener;
import com.rb.lottery.client.UI.listener.MainFrameListener;
import com.rb.lottery.client.UI.listener.QihaoListener;
import com.rb.lottery.client.UI.table.BasketTableModel;
import com.rb.lottery.client.UI.table.DataTableCellRenderer;
import com.rb.lottery.client.UI.table.DataTableModel;
import com.rb.lottery.client.UI.table.DataTableMouseAdapter;
import com.rb.lottery.client.common.ColorConstants;
import com.rb.lottery.client.common.CommandConstants;
import com.rb.lottery.client.common.FilePathConstants;
import com.rb.lottery.client.common.FontConstants;
import com.rb.lottery.client.common.MessageConstants;
import com.rb.lottery.client.common.RegexConstants;
import com.rb.lottery.client.common.SystemConstants;
import com.rb.lottery.client.common.SystemFunctions;
import com.rb.lottery.client.handler.AnalysisHandler;
import com.rb.lottery.client.handler.FilterHandler;
import com.rb.lottery.client.handler.HelpHandler;
import com.rb.lottery.client.handler.IOHandler;
import com.rb.lottery.client.handler.PageHandler;
import com.rb.lottery.client.handler.ProjectHandler;
import com.rb.lottery.client.handler.QueryHandler;
import com.rb.lottery.client.handler.SystemConfHandler;
import com.rb.lottery.client.handler.UserHandler;
import com.rb.lottery.client.timer.IdleMemoryTimer;
import com.rb.lottery.client.util.Page;
import com.rb.lottery.datatable.DefaultGroup;
import com.rb.lottery.datatable.GroupableTableHeader;
import com.rb.lottery.domain.BetBasket;
import com.rb.lottery.domain.BetMatch;
import com.rb.lottery.system.SysConfig;
import com.rb.lottery.system.SystemCache;
import com.rb.lottery.system.SystemEnvironment;
import com.rb.lottery.system.SystemProcessor;
import com.rb.lottery.system.UserConfig;

/**
 * @类功能说明: 主界面
 * @类修改者: robin
 * @修改日期: 2012-2-25
 * @修改说明: 添加用户模块
 * @作者: robin
 * @创建时间: 2011-9-30 上午09:32:10
 * @版本: 1.0.0
 */

public class MainFrame extends JFrame {

    /**
     * @Fields serialVersionUID
     */
    private static final long serialVersionUID = 4353787694532984059L;

    private static final int MAIN_FRAME_WIDTH = 950;
    private static final int MAIN_FRAME_HEIGHT = 600;
    private static final int MAIN_FRAME_LOCATION_X = 200;
    private static final int MAIN_FRAME_LOCATION_Y = 50;
    private static final int TABLE_ROW_HEIGHT = 20;
    private static MainFrame mainFrame = null;
    private static Page page;
    private static int pageType = 0;

    private QihaoListener qihaoListener = new QihaoListener();

    // private DataCache dataCache;
    // private Lottery lottery;

    private JPanel mainPane;
    private JPanel menuPane; // 快速菜单栏
    private JPanel workPane; // 工作区
    private JPanel betPane; //
    private JPanel typePane;
    private JScrollPane dataPane;
    private JTable dataTable;
    private DataTableModel model;
    private JTabbedPane OperatePane;
    private JPanel fullSeat;
    private JPanel region;
    private JPanel shrink;
    private JPanel dantuo;
    private JPanel waterflood;
    private JPanel rondom;
    private JPanel dobetPane;
    private JPanel filterPane;
    private JTabbedPane filterTab;
    private JPanel filterCommon;
    private JButton commonfilterAll;
    private JButton commonbatfilter;
    private JButton commonallowerrBtn;
    private JTabbedPane filterCommonTab;
    private JPanel filterCommonA;
    private JCheckBox wins;
    private JCheckBox draws;
    private JCheckBox losses;
    private JCheckBox scores;
    private JCheckBox breaks;
    private JCheckBox continus;
    private JCheckBox hostWinContinu;
    private JCheckBox hostDrawContinu;
    private JCheckBox hostLossContinu;
    private JCheckBox winDrawContinu;
    private JCheckBox winLossContinu;
    private JCheckBox drawLossContinu;
    private JCheckBox conPartSum;
    private JCheckBox conPartCrs;
    private JCheckBox crsSum;
    private JComboBox left_wins;
    private JComboBox left_draws;
    private JComboBox left_losses;
    private JComboBox left_scores;
    private JComboBox left_breaks;
    private JComboBox left_continus;
    private JComboBox left_hostWinContinu;
    private JComboBox left_hostDrawContinu;
    private JComboBox left_hostLossContinu;
    private JComboBox left_winDrawContinu;
    private JComboBox left_winLossContinu;
    private JComboBox left_drawLossContinu;
    private JComboBox left_conPartSum;
    private JComboBox left_conPartCrs;
    private JComboBox left_crsSum;
    private JComboBox right_wins;
    private JComboBox right_draws;
    private JComboBox right_losses;
    private JComboBox right_scores;
    private JComboBox right_breaks;
    private JComboBox right_continus;
    private JComboBox right_hostWinContinu;
    private JComboBox right_hostDrawContinu;
    private JComboBox right_hostLossContinu;
    private JComboBox right_winDrawContinu;
    private JComboBox right_winLossContinu;
    private JComboBox right_drawLossContinu;
    private JComboBox right_conPartSum;
    private JComboBox right_conPartCrs;
    private JComboBox right_crsSum;
    private JCheckBox winserr;
    private JCheckBox drawserr;
    private JCheckBox losseserr;
    private JCheckBox scoreserr;
    private JCheckBox breakserr;
    private JCheckBox continuserr;
    private JCheckBox hostWinContinuerr;
    private JCheckBox hostDrawContinuerr;
    private JCheckBox hostLossContinuerr;
    private JCheckBox winDrawContinuerr;
    private JCheckBox winLossContinuerr;
    private JCheckBox drawLossContinuerr;
    private JCheckBox conPartSumerr;
    private JCheckBox conPartCrserr;
    private JCheckBox crsSumerr;
    private JPanel filterCommonB;
    private JCheckBox firstOdds;
    private JCheckBox secondOdds;
    private JCheckBox thirdOdds;
    private JCheckBox rangeRank;
    private JCheckBox probility;
    private JCheckBox expectBet;
    private JCheckBox probilitySum;
    private JCheckBox oddsSum;
    private JCheckBox oddsCrs;
    private JCheckBox winIndice;
    private JComboBox left_firstOdds;
    private JComboBox left_secondOdds;
    private JComboBox left_thirdOdds;
    private JTextField left_rangeRank;
    private JTextField left_probility;
    private JTextField left_expectBet;
    private JTextField left_probilitySum;
    private JTextField left_oddsSum;
    private JTextField left_oddsCrs;
    private JTextField left_winIndice;
    private JComboBox right_firstOdds;
    private JComboBox right_secondOdds;
    private JComboBox right_thirdOdds;
    private JTextField right_rangeRank;
    private JTextField right_probility;
    private JTextField right_expectBet;
    private JTextField right_probilitySum;
    private JTextField right_oddsSum;
    private JTextField right_oddsCrs;
    private JTextField right_winIndice;
    private JCheckBox firstOddserr;
    private JCheckBox secondOddserr;
    private JCheckBox thirdOddserr;
    private JCheckBox rangeRankerr;
    private JCheckBox probilityerr;
    private JCheckBox expectBeterr;
    private JCheckBox probilitySumerr;
    private JCheckBox oddsSumerr;
    private JCheckBox oddsCrserr;
    private JCheckBox winIndiceerr;
    private JPanel filterAdvance;
    private JButton advancefilterAll;
    private JButton advanceallowerrBtn;
    private JTabbedPane filterAdvanceTab;
    private JPanel filterAdvanceA;
    private JCheckBox advanceA1;
    private JCheckBox advanceA2;
    private JCheckBox advanceA3;
    private JCheckBox advanceA4;
    private JCheckBox advanceA5;
    private JCheckBox advanceA6;
    private JCheckBox advanceA7;
    private JCheckBox advanceA8;
    private JCheckBox advanceA9;
    private JCheckBox advanceA10;
    private JCheckBox advanceA11;
    private JCheckBox advanceA12;
    private JCheckBox advanceA13;
    private JCheckBox advanceA14;
    private JCheckBox advanceA15;
    private JCheckBox advanceA16;
    private JCheckBox advanceA17;
    private JCheckBox advanceA18;
    private JTextField advanceAText1;
    private JTextField advanceAText2;
    private JTextField advanceAText3;
    private JTextField advanceAText4;
    private JTextField advanceAText5;
    private JTextField advanceAText6;
    private JTextField advanceAText7;
    private JTextField advanceAText8;
    private JTextField advanceAText9;
    private JTextField advanceAText10;
    private JTextField advanceAText11;
    private JTextField advanceAText12;
    private JTextField advanceAText13;
    private JTextField advanceAText14;
    private JTextField advanceAText15;
    private JTextField advanceAText16;
    private JTextField advanceAText17;
    private JTextField advanceAText18;
    private JButton advanceABtn1;
    private JButton advanceABtn2;
    private JButton advanceABtn3;
    private JButton advanceABtn4;
    private JButton advanceABtn5;
    private JButton advanceABtn6;
    private JButton advanceABtn7;
    private JButton advanceABtn8;
    private JButton advanceABtn9;
    private JButton advanceABtn10;
    private JButton advanceABtn11;
    private JButton advanceABtn12;
    private JButton advanceABtn13;
    private JButton advanceABtn14;
    private JButton advanceABtn15;
    private JButton advanceABtn16;
    private JButton advanceABtn17;
    private JButton advanceABtn18;
    private JCheckBox advanceAerr1;
    private JCheckBox advanceAerr2;
    private JCheckBox advanceAerr3;
    private JCheckBox advanceAerr4;
    private JCheckBox advanceAerr5;
    private JCheckBox advanceAerr6;
    private JCheckBox advanceAerr7;
    private JCheckBox advanceAerr8;
    private JCheckBox advanceAerr9;
    private JCheckBox advanceAerr10;
    private JCheckBox advanceAerr11;
    private JCheckBox advanceAerr12;
    private JCheckBox advanceAerr13;
    private JCheckBox advanceAerr14;
    private JCheckBox advanceAerr15;
    private JCheckBox advanceAerr16;
    private JCheckBox advanceAerr17;
    private JCheckBox advanceAerr18;
    private JPanel filterAdvanceB;
    private JCheckBox advanceB1;
    private JCheckBox advanceB2;
    private JCheckBox advanceB3;
    private JCheckBox advanceB4;
    private JCheckBox advanceB5;
    private JCheckBox advanceB6;
    private JCheckBox advanceB7;
    private JCheckBox advanceB8;
    private JCheckBox advanceB9;
    private JCheckBox advanceB10;
    private JCheckBox advanceB11;
    private JCheckBox advanceB12;
    private JCheckBox advanceB13;
    private JCheckBox advanceB14;
    private JTextField advanceBText1;
    private JTextField advanceBText2;
    private JTextField advanceBText3;
    private JTextField advanceBText4;
    private JTextField advanceBText5;
    private JTextField advanceBText6;
    private JTextField advanceBText7;
    private JTextField advanceBText8;
    private JTextField advanceBText9;
    private JTextField advanceBText10;
    private JTextField advanceBText11;
    private JTextField advanceBText12;
    private JTextField advanceBText13;
    private JTextField advanceBText14;
    private JButton advanceBBtn1;
    private JButton advanceBBtn2;
    private JButton advanceBBtn3;
    private JButton advanceBBtn4;
    private JButton advanceBBtn5;
    private JButton advanceBBtn6;
    private JButton advanceBBtn7;
    private JButton advanceBBtn8;
    private JButton advanceBBtn9;
    private JButton advanceBBtn10;
    private JButton advanceBBtn11;
    private JButton advanceBBtn12;
    private JButton advanceBBtn13;
    private JButton advanceBBtn14;
    private JCheckBox advanceBerr1;
    private JCheckBox advanceBerr2;
    private JCheckBox advanceBerr3;
    private JCheckBox advanceBerr4;
    private JCheckBox advanceBerr5;
    private JCheckBox advanceBerr6;
    private JCheckBox advanceBerr7;
    private JCheckBox advanceBerr8;
    private JCheckBox advanceBerr9;
    private JCheckBox advanceBerr10;
    private JCheckBox advanceBerr11;
    private JCheckBox advanceBerr12;
    private JCheckBox advanceBerr13;
    private JCheckBox advanceBerr14;
    private JPanel filterPlugin;
    private JTabbedPane filterPluginTab;
    private JButton pluginfilterAll;
    private JButton pluginallowerrBtn;
    private JPanel filterPluginA;
    private JCheckBox pluginA1;
    private JTextField pluginAText1;
    private JButton pluginABtn1;
    private JCheckBox pluginAerr1;
    private JPanel filterSet;
    private ButtonGroup setGroup;
    private JRadioButton unionSet;
    private JRadioButton mixedSet;
    private JRadioButton minusSet;
    private JRadioButton diffSet;
    private JButton advanceSet;
    private JButton clearSet;
    private JCheckBox askType;
    private JPanel filterbetPane;
    private JMenuBar filterBar;
    private JMenu filterOperation;
    private JMenuItem openFilter;
    private JMenuItem saveFilter;
    private JMenuItem clearFilter;
    private JMenuItem singleVerify;
    private JMenuItem verifybefFilter;
    private JMenuItem filterVerify;
    private JMenuItem historyVerify;
    private JMenuItem filterRecycle;
    private JMenuItem keepFilter;
    private JMenuItem pluginMana;
    private JMenuItem showFilterProg;
    private JMenuItem batFilter;
    private JCheckBox allowerr;
    private JComboBox lefterr;
    private JComboBox righterr;
    private JButton filter;
    private JPanel resultPane;
    private JPanel procPane;
    private JMenuBar procmbar;
    private JMenu netbet;
    private JMenuItem arena2048;
    private JMenuItem arena128;
    private JMenuItem mitilbuy90;
    private JMenuItem mitilbuym70;
    private JMenuItem mitilbuyl70;
    private JMenuItem mitilbuyown;
    private JButton addproc;
    private JButton dropproc;
    private JMenu betBascket;
    private JMenuItem bs_item1;
    private JMenuItem bs_item2;
    private JMenuItem bs_item3;
    private JMenuItem bs_item4;
    private JMenuItem bs_item5;
    private JMenuItem bs_item6;
    private JMenuItem bs_item7;
    private JMenuItem bs_item8;
    private JMenuItem bs_item9;
    private JMenuItem bs_item10;
    private JMenuItem bs_item11;
    private JMenuItem bs_item12;
    private JMenuItem bs_item13;
    private JMenuItem bs_item14;
    private JMenuItem bs_item15;
    private JMenuItem bs_item16;
    private JMenu resultProc;
    private JMenuItem rp_item1;
    private JMenuItem rp_item2;
    private JMenuItem rp_item3;
    private JMenuItem rp_item4;
    private JMenuItem rp_item5;
    private JRadioButtonMenuItem rp_item6;
    private JRadioButtonMenuItem rp_item7;
    private JMenuItem rp_item8;
    private JMenuItem rp_item9;
    private JMenuItem rp_item10;
    private JMenuItem rp_item11;
    private JMenuItem rp_item12;
    private JMenuItem rp_item13;
    private JMenu singleExpand;
    private JMenuItem se_item1;
    private JMenuItem se_item2;
    private JMenuItem se_item3;
    private JMenu betShrink;
    private JMenuItem bts_item1;
    private JMenuItem bts_item2;
    private JMenuItem bts_item3;
    private JMenuItem bts_item4;
    private JMenuItem bts_item5;
    private JMenuItem bts_item6;
    private JMenuItem bts_item7;
    private JMenuItem bts_item8;
    private JMenuItem bts_item9;
    private JMenuItem bts_item10;
    private JMenu betProtect;
    private JMenuItem bp_item1;
    private JMenuItem bp_item2;
    private JMenuItem bp_item3;
    private JMenuItem bp_item4;
    private JMenuItem bp_item5;
    private JMenuItem bp_item6;
    private JMenuItem bp_item7;
    private JMenuItem bp_item8;
    private JMenuItem bp_item9;
    private JMenuItem bp_item10;
    private JMenu betSort;
    private JMenuItem bst_item1;
    private JMenuItem bst_item2;
    private JMenuItem bst_item3;
    private JMenuItem bst_item4;
    private JMenuItem bst_item5;
    private JMenuItem bst_item6;
    private JMenuItem bst_item7;
    private JMenuItem bst_item8;
    private JMenuItem bst_item9;
    private JMenuItem bst_item10;
    private JMenuItem bst_item11;
    private JMenuItem bst_item12;
    private JMenuItem bst_item13;
    private JMenu betSet;
    private JMenu betSet1;
    private JMenuItem bsts_item1;
    private JMenu betSet2;
    private JMenuItem bsts_item2;
    private JMenu betSet3;
    private JMenuItem bsts_item3;
    private JMenu betSet4;
    private JMenuItem bsts_item4;
    private JMenu betData;
    private JMenuItem bd_item1;
    private JMenuItem bd_item2;
    private JMenuItem bd_item3;
    private JMenuItem bd_item4;
    private JScrollPane procResult;
    private JList basketList;
    private BasketListModel listModel;
    private JPanel basketPane;
    private JPanel basketMenu;
    private JButton basketMenu1;
    private JButton basketMenu2;
    private JButton basketMenu3;
    private JButton basketMenu4;
    private JButton basketMenu5;
    private JButton basketMenu6;
    private JButton basketMenu7;
    private JButton basketMenu8;
    private JButton basketMenu9;
    private JButton basketMenu10;
    private JScrollPane basketData;
    private JTable basketTable;
    private BasketTableModel basketModel;
    private JPanel baskePage;
    private JButton firstPage;
    private JButton previousPage;
    private JButton nextPage;
    private JButton lastPage;
    private JButton gotoPage;
    private JTextField gotoPageText;
    private JLabel pageCount;
    private JPanel statuPane;
    private JMenuBar mbar;
    private JMenu account;
    private JMenuItem register;
    private JMenuItem login;
    private JMenu accountInfo;
    private JMenuItem userInfo;
    private JMenuItem betInfo;
    private JMenuItem tradeInfo;
    private JMenuItem capitalInfo;
    private JMenuItem changeUserInfo;
    private JMenuItem changePassword;
    private JMenu safeSetting;
    private JMenuItem cryptoguardSetting;
    private JMenuItem payPasswordSetting;
    private JMenuItem recharge;
    private JMenuItem toVIP;
    private JMenuItem logout;
    private JMenu file;
    private JMenu data;
    private JMenu betret;
    private JMenu table;
    private JMenu system;
    private JMenu help;
    private JMenuItem upload;
    private JMenuItem new_item;
    private JMenuItem openpro_item;
    private JMenuItem savepro_item;
    private JMenuItem openret_item;
    private JMenuItem saveret_item;
    private JMenuItem exit_item;
    private JMenuItem study_item;
    private JMenuItem compare_item;
    private JMenuItem mediaforcast_item;
    private JMenuItem survey_item;
    private JMenuItem mulsta_item;
    private JMenuItem mulana_item;
    private JMenuItem tzfesta_item;
    private JMenuItem tzfeana_item;
    private JMenuItem otesta_item;
    private JMenuItem oteana_item;
    private JMenuItem winquery_item;
    private JMenu betscat_item;
    private JMenuItem betretsta_item;
    private JMenuItem betscatsta_item;
    private JMenuItem batprofilter_item;
    private JMenuItem batmedfilter_item;
    private JMenuItem mergebet_item;
    private JMenuItem betundo_item;
    private JMenuItem betredo_item;
    private JMenuItem seturdo_item;
    private JMenuItem filrecycle_item;
    private JMenuItem datastatable_item;
    private JMenuItem hismedsta_item;
    private JMenuItem hisoddsta_item;
    private JMenuItem retprot_item;
    private JMenuItem oddprot_item;
    private JMenuItem medprot_item;
    private JMenuItem dataupdate_item;
    private JMenuItem datainit_item;
    private JMenuItem filman_item;
    private JMenuItem softupdate_item;
    private JMenuItem betedit_item;
    private JMenuItem notepad_item;
    private JMenuItem calc_item;
    private JMenuItem systemset_item;
    private JMenuItem usehelp_item;
    private JMenuItem olservice_item;
    private JMenuItem nowolservice_item;
    private JMenuItem homepage_item;
    private JMenuItem forum_item;
    private JMenuItem about_item;
    private JMenu user;
    private JLabel betTylab;
    private JLabel prdlab;
    private JLabel betstatus;
    private JLabel filterstatus;
    private JLabel betNum;
    private JLabel cachestatu;
    private JCheckBox newBascket;
    private JCheckBox range;
    private JCheckBox winexp;
    private JCheckBox dataexp;
    private JTextField rangeText;
    private JTextField winexpText;
    private JTextField dataexpText;
    private JButton rangeBtn;
    private JButton winexpBtn;
    private JButton dataexpBtn;
    private ButtonGroup shrinkGroup;
    private JRadioButton standarshrink;
    private JRadioButton danshrink;
    private JComboBox standarshrinkComb;
    private JComboBox danshrinkComb;
    private JTextField danshrinkText;
    private JButton danshrinkBtn;
    private ButtonGroup danGroup;
    private JRadioButton commondan;
    private JRadioButton groupdan;
    private JRadioButton groupaddan;
    private JTextField commondanText;
    private JTextField groupdanText;
    private JTextField groupaddanText;
    private JButton commondanBtn;
    private JButton groupdanBtn;
    private JButton groupaddanBtn;
    private ButtonGroup wfGroup;
    private JRadioButton standarwf;
    private JRadioButton coldwf;
    private JRadioButton expandwf;
    private JComboBox leftwfComb;
    private JComboBox rightwfComb;
    private JComboBox expandwfComb;
    private JLabel multiOn;
    private JLabel onexpand;
    private JTextField coldwfText;
    private JButton coldwfBtn;
    private JCheckBox refOdds;
    private JCheckBox rondomTimes;
    private JTextField rondomTimesText;
    private JTextField rondomGetText;
    private JTextField rondomdanGetText;
    private JTextField danGetText;
    private ButtonGroup rondomGroup;
    private JRadioButton rondomGet;
    private JRadioButton danGet;
    private JButton danGetBtn;
    private JComboBox period;
    private JComboBox betType;
    private JButton passway;
    private JComboBox betnumlimit;
    private JButton proj;
    private JButton save;
    private JButton abtn;
    private JButton bbtn;
    private JButton cbtn;
    private JButton dbtn;
    private JButton ebtn;
    private JButton fbtn;
    private JButton dopart;
    private JButton dobet;
    private ButtonGroup fullSeatGroup;
    private JRadioButton op_single;
    private JRadioButton op_small_multi;
    private JTextField multi;

    public static MainFrame getInstance() {
        if (mainFrame == null) {
            mainFrame = new MainFrame();
        }
        return mainFrame;
    }

    private MainFrame() {
        super();
        Image image = Toolkit.getDefaultToolkit().getImage(FilePathConstants.LOTTERY_IMG_FILE);
        this.setIconImage(image);
        this.setTitle(SystemEnvironment.getInstance().name + " - Client");
        this.setLocation(MAIN_FRAME_LOCATION_X, MAIN_FRAME_LOCATION_Y);
        this.setSize(new java.awt.Dimension(MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT));
        // 设置无标题栏
        this.setUndecorated(true);
        // 初始化背景图片
        ImageIcon bg_img = new ImageIcon(FilePathConstants.SYSTEM_INIT_IMG_FILE);
        JLabel bgimglab = new JLabel(bg_img);
        // 将背景标签添加到Frame的LayeredPane面板里
        this.getLayeredPane().add(bgimglab, new Integer(Integer.MIN_VALUE));
        // 设置背景标签的位置
        bgimglab.setBounds(0, 0, MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT);
        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout());
        JLabel sysInit = new JLabel(SystemConstants.SYSTEM_INIT, JLabel.RIGHT);

        sysInit.setFont(FontConstants.INIT_FONT);
        sysInit.setForeground(Color.DARK_GRAY);
        cp.add(sysInit, BorderLayout.SOUTH);
        // 将内容面板设为透明,这样LayeredPane面板中的背景才能显示出来
        ((JPanel) cp).setOpaque(false);
        this.setVisible(true);
        this.remove(sysInit);
        initMainFrame();
    }

    private void initMainFrame() {
        this.dispose();
        this.setUndecorated(false);
        SystemProcessor.initMatchData();
        SystemProcessor.initLottery();
        // 初始化GUI数据
        // Map<String, List<BetMatch>> map = SystemConfHandler.getGUIData();
        // key排序
        // TreeMap<String, List<BetMatch>> tmp = new TreeMap(map);
        // dataCache = new DataCache(tmp);

        initMenuBar(); // 菜单栏
        this.setJMenuBar(mbar);

        initMainPane(); // 主界面
        this.add(mainPane);

        this.setState(Frame.NORMAL);
        this.setResizable(false);
        // this.setBackground(SystemConstants.MAINPANEL_BG);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addWindowListener(new MainFrameListener());
        this.pack();
        this.setVisible(true);
    }

    /**
     * @param data
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initMainPane() {
        mainPane = new JPanel(new FlowLayout());
        // mainPane.setBorder(new EtchedBorder());
        mainPane.setBorder(null);
        mainPane.setPreferredSize(new java.awt.Dimension(942, 588));

        initMenuPane(); // 快速菜单栏
        mainPane.add(menuPane);

        initWorkPane(); // 工作区
        mainPane.add(workPane);

        initStatuPane(); // 状态栏
        mainPane.add(statuPane);
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initStatuPane() {
        statuPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statuPane.setBorder(new EtchedBorder());
        statuPane.setPreferredSize(new java.awt.Dimension(942, 50));
        betstatus = new JLabel(SystemConstants.BETSTATUS);
        // 初始化投注状态栏
        betstatus.setText(SystemProcessor.getBetStatu());
        betstatus.setBorder(new EtchedBorder());
        betstatus.setPreferredSize(new java.awt.Dimension(314, 30));
        statuPane.add(betstatus);
        filterstatus = new JLabel(SystemConstants.FILTERSTATUS);
        filterstatus.setBorder(new EtchedBorder());
        filterstatus.setPreferredSize(new java.awt.Dimension(314, 30));
        statuPane.add(filterstatus);

        betNum = new JLabel(SystemConstants.BETCODE
                + SystemConstants.ZERO_STRING
                + SystemConstants.BLANK_STRING
                + SystemConstants.BET);
        betNum.setBorder(new EtchedBorder());
        betNum.setPreferredSize(new java.awt.Dimension(145, 30));
        statuPane.add(betNum);

        cachestatu = new JLabel(SystemConstants.EMPTY_STRING);
        IdleMemoryTimer idleTimer = new IdleMemoryTimer();
        Timer timer = new Timer();
        timer.schedule(idleTimer, 2000, 5000);
        cachestatu.setBorder(new EtchedBorder());
        cachestatu.setPreferredSize(new java.awt.Dimension(145, 30));
        statuPane.add(cachestatu);
    }

    /**
     * @param data
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initWorkPane() {
        workPane = new JPanel(new GridLayout(1, 3));
        workPane.setBorder(new EtchedBorder());
        workPane.setPreferredSize(new java.awt.Dimension(942, 484));

        initBetPane();
        workPane.add(betPane);

        initFilterPane();
        workPane.add(filterPane);

        initResultPane();
        workPane.add(resultPane);
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initResultPane() {
        resultPane = new JPanel();
        resultPane.setBorder(new EtchedBorder());
        resultPane.setPreferredSize(new java.awt.Dimension(300, 484));

        initProcPane();
        resultPane.add(procPane);

        initBasketPane();
        resultPane.add(basketPane);
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initBasketPane() {
        basketPane = new JPanel(new FlowLayout());
        basketPane.setPreferredSize(new java.awt.Dimension(300, 342));
        basketPane.setBorder(new EtchedBorder());

        initBasketMenuPane();
        basketPane.add(basketMenu);

        initBasketDataPane();
        basketPane.add(basketData);

        initBasketPagePane();
        basketPane.add(baskePage);
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initBasketMenuPane() {
        basketMenu = new JPanel(new FlowLayout(FlowLayout.LEFT));
        basketMenu.setPreferredSize(new java.awt.Dimension(300, 26));

        basketMenu1 = new JButton(new ImageIcon(FilePathConstants.BASKET_MENU1_IMG));
        basketMenu1.setPreferredSize(new java.awt.Dimension(24, 20));
        basketMenu.add(basketMenu1);
        basketMenu2 = new JButton(new ImageIcon(FilePathConstants.BASKET_MENU2_IMG));
        basketMenu2.setPreferredSize(new java.awt.Dimension(24, 20));
        basketMenu.add(basketMenu2);
        basketMenu3 = new JButton(new ImageIcon(FilePathConstants.BASKET_MENU3_IMG));
        basketMenu3.setPreferredSize(new java.awt.Dimension(24, 20));
        basketMenu.add(basketMenu3);
        basketMenu4 = new JButton(new ImageIcon(FilePathConstants.BASKET_MENU4_IMG));
        basketMenu4.setPreferredSize(new java.awt.Dimension(24, 20));
        basketMenu.add(basketMenu4);
        basketMenu5 = new JButton(new ImageIcon(FilePathConstants.BASKET_MENU5_IMG));
        basketMenu5.setPreferredSize(new java.awt.Dimension(24, 20));
        basketMenu.add(basketMenu5);
        basketMenu6 = new JButton(new ImageIcon(FilePathConstants.BASKET_MENU6_IMG));
        basketMenu6.setPreferredSize(new java.awt.Dimension(24, 20));
        basketMenu.add(basketMenu6);
        basketMenu7 = new JButton(new ImageIcon(FilePathConstants.BASKET_MENU7_IMG));
        basketMenu7.setPreferredSize(new java.awt.Dimension(24, 20));
        basketMenu.add(basketMenu7);
        basketMenu8 = new JButton(new ImageIcon(FilePathConstants.BASKET_MENU8_IMG));
        basketMenu8.setPreferredSize(new java.awt.Dimension(24, 20));
        basketMenu.add(basketMenu8);
        basketMenu9 = new JButton(new ImageIcon(FilePathConstants.BASKET_MENU9_IMG));
        basketMenu9.setPreferredSize(new java.awt.Dimension(24, 20));
        basketMenu.add(basketMenu9);
        basketMenu10 = new JButton(new ImageIcon(FilePathConstants.BASKET_MENU10_IMG));
        basketMenu10.setPreferredSize(new java.awt.Dimension(24, 20));
        basketMenu.add(basketMenu10);
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initBasketDataPane() {
        basketTable = new JTable();
        basketModel = new BasketTableModel();
        basketTable.setModel(basketModel);
        basketModel.addColumn(SystemConstants.INDEX);
        basketModel.addColumn(SystemConstants.DEFAULT_BASKET_DATA);

        // 单元格显示
        DataTableCellRenderer render = new DataTableCellRenderer();
        render.setHorizontalAlignment(JLabel.LEFT);
        render.setVerticalAlignment(JLabel.CENTER);
        basketTable.setDefaultRenderer(Object.class, render);

        // 列管理
        TableColumnModel colModel = basketTable.getColumnModel();
        TableColumn column = colModel.getColumn(0);
        column.setPreferredWidth(80);
        basketData = new JScrollPane(basketTable);
        basketData.setPreferredSize(new java.awt.Dimension(300, 260));
        basketData.setBackground(Color.WHITE);
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initBasketPagePane() {
        baskePage = new JPanel(new FlowLayout(FlowLayout.LEFT + 20));
        baskePage.setPreferredSize(new java.awt.Dimension(300, 30));

        firstPage = new JButton(SystemConstants.FIRST_PAGE);
        firstPage.setPreferredSize(new java.awt.Dimension(30, 30));
        firstPage.setBorder(null);
        firstPage.addActionListener(new PageHandler(SystemConstants.FIRST_PAGE));
        baskePage.add(firstPage);
        previousPage = new JButton(SystemConstants.PREVIOUS_PAGE);
        previousPage.setPreferredSize(new java.awt.Dimension(30, 30));
        previousPage.setBorder(null);
        previousPage.addActionListener(new PageHandler(SystemConstants.PREVIOUS_PAGE));
        baskePage.add(previousPage);
        nextPage = new JButton(SystemConstants.NEXT_PAGE);
        nextPage.setPreferredSize(new java.awt.Dimension(30, 30));
        nextPage.setBorder(null);
        nextPage.addActionListener(new PageHandler(SystemConstants.NEXT_PAGE));
        baskePage.add(nextPage);
        lastPage = new JButton(SystemConstants.LAST_PAGE);
        lastPage.setPreferredSize(new java.awt.Dimension(30, 30));
        lastPage.setBorder(null);
        lastPage.addActionListener(new PageHandler(SystemConstants.LAST_PAGE));
        baskePage.add(lastPage);
        gotoPage = new JButton(SystemConstants.GOTO);
        gotoPage.setPreferredSize(new java.awt.Dimension(30, 30));
        gotoPage.setForeground(Color.BLUE);
        gotoPage.setBorder(null);
        gotoPage.addActionListener(new PageHandler(SystemConstants.GOTO));
        baskePage.add(gotoPage);
        gotoPageText = new JTextField();
        gotoPageText.setPreferredSize(new java.awt.Dimension(30, 30));
        gotoPageText.setBorder(null);
        baskePage.add(gotoPageText);
        baskePage.add(new JLabel(SystemConstants.SLASH));
        pageCount = new JLabel(SystemConstants.ZERO_STRING);
        baskePage.add(pageCount);
        baskePage.add(new JLabel(SystemConstants.PAGE));
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initProcPane() {
        procPane = new JPanel(new FlowLayout(FlowLayout.LEFT + 25));
        procPane.setPreferredSize(new java.awt.Dimension(300, 120));
        procPane.setBorder(new EtchedBorder());

        procmbar = new JMenuBar();
        netbet = new JMenu();
        netbet.setPreferredSize(new java.awt.Dimension(24, 24));
        netbet.setIcon(new ImageIcon(FilePathConstants.BET_NET_IMG));
        arena2048 = new JMenuItem(SystemConstants.ARENA2048);
        arena128 = new JMenuItem(SystemConstants.ARENA128);
        mitilbuy90 = new JMenuItem(SystemConstants.MULTI_BUY90);
        mitilbuym70 = new JMenuItem(SystemConstants.MULTI_BUYM70);
        mitilbuyl70 = new JMenuItem(SystemConstants.MULTI_BUYL70);
        mitilbuyown = new JMenuItem(SystemConstants.MULTI_BUYOWN);
        JLabel top = new JLabel(SystemConstants.DOWNLOAD_TO_BASKET, JLabel.CENTER);
        top.setForeground(Color.RED);
        netbet.add(top);
        netbet.addSeparator();
        netbet.add(arena2048);
        netbet.add(arena128);
        netbet.add(mitilbuy90);
        netbet.add(mitilbuym70);
        netbet.add(mitilbuyl70);
        netbet.add(mitilbuyown);
        netbet.addSeparator();
        JLabel bottom = new JLabel(SystemConstants.REALTIME_DATA, JLabel.CENTER);
        bottom.setForeground(Color.RED);
        netbet.add(bottom);
        procmbar.add(netbet);
        procPane.add(procmbar);
        addproc = new JButton(new ImageIcon(FilePathConstants.ADD_PROC_IMG));
        addproc.setPreferredSize(new java.awt.Dimension(24, 24));
        procPane.add(addproc);
        dropproc = new JButton(new ImageIcon(FilePathConstants.DROP_PROC_IMG));
        dropproc.setPreferredSize(new java.awt.Dimension(24, 24));
        procPane.add(dropproc);
        betBascket = new JMenu(SystemConstants.BET_BASCKET);
        betBascket.setPreferredSize(new java.awt.Dimension(60, 24));
        bs_item1 = new JMenuItem(SystemConstants.BET_BASCKET1);
        betBascket.add(bs_item1);
        bs_item2 = new JMenuItem(SystemConstants.BET_BASCKET2);
        betBascket.add(bs_item2);
        betBascket.addSeparator();
        bs_item3 = new JMenuItem(SystemConstants.BET_BASCKET3);
        bs_item3.setEnabled(false);
        betBascket.add(bs_item3);
        bs_item4 = new JMenuItem(SystemConstants.BET_BASCKET4);
        bs_item4.setEnabled(false);
        betBascket.add(bs_item4);
        betBascket.addSeparator();
        bs_item5 = new JMenuItem(SystemConstants.BET_BASCKET5);
        betBascket.add(bs_item5);
        bs_item6 = new JMenuItem(SystemConstants.BET_BASCKET6);
        bs_item6.setEnabled(false);
        betBascket.add(bs_item6);
        bs_item7 = new JMenuItem(SystemConstants.BET_BASCKET7);
        betBascket.add(bs_item7);
        betBascket.addSeparator();
        bs_item8 = new JMenuItem(SystemConstants.BET_BASCKET8);
        betBascket.add(bs_item8);
        bs_item9 = new JMenuItem(SystemConstants.BET_BASCKET9);
        bs_item9.setEnabled(false);
        betBascket.add(bs_item9);
        bs_item10 = new JMenuItem(SystemConstants.BET_BASCKET10);
        bs_item10.setEnabled(false);
        betBascket.add(bs_item10);
        betBascket.addSeparator();
        bs_item11 = new JMenuItem(SystemConstants.BET_BASCKET11);
        bs_item11.setEnabled(false);
        betBascket.add(bs_item11);
        bs_item12 = new JMenuItem(SystemConstants.BET_BASCKET12);
        bs_item12.setEnabled(false);
        betBascket.add(bs_item12);
        betBascket.addSeparator();
        bs_item13 = new JMenuItem(SystemConstants.BET_BASCKET13);
        betBascket.add(bs_item13);
        bs_item14 = new JMenuItem(SystemConstants.BET_BASCKET14);
        betBascket.add(bs_item14);
        bs_item15 = new JMenuItem(SystemConstants.BET_BASCKET15);
        betBascket.add(bs_item15);
        betBascket.addSeparator();
        bs_item16 = new JMenuItem(SystemConstants.BET_BASCKET16);
        betBascket.add(bs_item16);
        JMenuBar jmb = new JMenuBar();
        jmb.setPreferredSize(new java.awt.Dimension(150, 24));
        jmb.add(betBascket);
        resultProc = new JMenu(SystemConstants.RESULT_PROGRESS);
        resultProc.setPreferredSize(new java.awt.Dimension(70, 24));
        JLabel tj = new JLabel(SystemConstants.DEFAULT_BASCKET);
        tj.setForeground(Color.RED);
        resultProc.add(tj);
        resultProc.addSeparator();
        rp_item1 = new JMenuItem(SystemConstants.RESULT_PROGRESS1);
        resultProc.add(rp_item1);
        rp_item2 = new JMenuItem(SystemConstants.RESULT_PROGRESS2);
        resultProc.add(rp_item2);
        resultProc.addSeparator();
        rp_item3 = new JMenuItem(SystemConstants.RESULT_PROGRESS3);
        resultProc.add(rp_item3);
        rp_item4 = new JMenuItem(SystemConstants.RESULT_PROGRESS4);
        resultProc.add(rp_item4);
        resultProc.addSeparator();
        singleExpand = new JMenu(SystemConstants.SINGLE_EXPAND);
        singleExpand.setEnabled(false);
        se_item1 = new JMenuItem(SystemConstants.SINGLE_EXPAND1);
        singleExpand.add(se_item1);
        se_item2 = new JMenuItem(SystemConstants.SINGLE_EXPAND2);
        singleExpand.add(se_item2);
        se_item3 = new JMenuItem(SystemConstants.SINGLE_EXPAND3);
        singleExpand.add(se_item3);
        resultProc.add(singleExpand);
        betShrink = new JMenu(SystemConstants.BET_SHRINK);
        betShrink.setEnabled(false);
        bts_item1 = new JMenuItem(SystemConstants.BET_SHRINK1);
        betShrink.add(bts_item1);
        bts_item2 = new JMenuItem(SystemConstants.BET_SHRINK2);
        betShrink.add(bts_item2);
        bts_item3 = new JMenuItem(SystemConstants.BET_SHRINK3);
        betShrink.add(bts_item3);
        bts_item4 = new JMenuItem(SystemConstants.BET_SHRINK4);
        betShrink.add(bts_item4);
        bts_item5 = new JMenuItem(SystemConstants.BET_SHRINK5);
        betShrink.add(bts_item5);
        bts_item6 = new JMenuItem(SystemConstants.BET_SHRINK6);
        betShrink.add(bts_item6);
        bts_item7 = new JMenuItem(SystemConstants.BET_SHRINK7);
        betShrink.add(bts_item7);
        bts_item8 = new JMenuItem(SystemConstants.BET_SHRINK8);
        betShrink.add(bts_item8);
        bts_item9 = new JMenuItem(SystemConstants.BET_SHRINK9);
        betShrink.add(bts_item9);
        bts_item10 = new JMenuItem(SystemConstants.BET_SHRINK10);
        betShrink.add(bts_item10);
        resultProc.add(betShrink);
        rp_item5 = new JMenuItem(SystemConstants.RESULT_PROGRESS5);
        rp_item5.setEnabled(false);
        resultProc.add(rp_item5);
        ButtonGroup rpgp = new ButtonGroup();
        rp_item6 = new JRadioButtonMenuItem(SystemConstants.RESULT_PROGRESS6);
        rp_item6.setEnabled(false);
        rp_item7 = new JRadioButtonMenuItem(SystemConstants.RESULT_PROGRESS7);
        rp_item7.setEnabled(false);
        rpgp.add(rp_item6);
        rpgp.add(rp_item7);
        resultProc.add(rp_item6);
        resultProc.add(rp_item7);
        rp_item8 = new JMenuItem(SystemConstants.RESULT_PROGRESS8);
        rp_item8.setEnabled(false);
        resultProc.add(rp_item8);
        rp_item9 = new JMenuItem(SystemConstants.RESULT_PROGRESS9);
        rp_item9.setEnabled(false);
        resultProc.add(rp_item9);
        resultProc.addSeparator();
        betProtect = new JMenu(SystemConstants.BET_PROTECT);
        bp_item1 = new JMenuItem(SystemConstants.BET_PROTECT1);
        betProtect.add(bp_item1);
        bp_item2 = new JMenuItem(SystemConstants.BET_PROTECT2);
        betProtect.add(bp_item2);
        bp_item3 = new JMenuItem(SystemConstants.BET_PROTECT3);
        betProtect.add(bp_item3);
        betProtect.addSeparator();
        bp_item4 = new JMenuItem(SystemConstants.BET_PROTECT4);
        betProtect.add(bp_item4);
        bp_item5 = new JMenuItem(SystemConstants.BET_PROTECT5);
        bp_item5.setEnabled(false);
        betProtect.add(bp_item5);
        betProtect.addSeparator();
        bp_item6 = new JMenuItem(SystemConstants.BET_PROTECT6);
        betProtect.add(bp_item6);
        bp_item7 = new JMenuItem(SystemConstants.BET_PROTECT7);
        betProtect.add(bp_item7);
        betProtect.addSeparator();
        bp_item8 = new JMenuItem(SystemConstants.BET_PROTECT8);
        betProtect.add(bp_item8);
        bp_item9 = new JMenuItem(SystemConstants.BET_PROTECT9);
        betProtect.add(bp_item9);
        betProtect.addSeparator();
        bp_item10 = new JMenuItem(SystemConstants.BET_PROTECT10);
        betProtect.add(bp_item10);
        resultProc.add(betProtect);
        betSort = new JMenu(SystemConstants.BET_SORT);
        betSort.setEnabled(false);
        bst_item1 = new JMenuItem(SystemConstants.BET_SORT1);
        betSort.add(bst_item1);
        betSort.addSeparator();
        bst_item2 = new JMenuItem(SystemConstants.BET_SORT2);
        betSort.add(bst_item2);
        betSort.addSeparator();
        bst_item3 = new JMenuItem(SystemConstants.BET_SORT3);
        bst_item3.setEnabled(false);
        betSort.add(bst_item3);
        bst_item4 = new JMenuItem(SystemConstants.BET_SORT4);
        bst_item4.setEnabled(false);
        betSort.add(bst_item4);
        betSort.addSeparator();
        bst_item5 = new JMenuItem(SystemConstants.BET_SORT5);
        betSort.add(bst_item5);
        bst_item6 = new JMenuItem(SystemConstants.BET_SORT6);
        betSort.add(bst_item6);
        betSort.addSeparator();
        bst_item7 = new JMenuItem(SystemConstants.BET_SORT7);
        betSort.add(bst_item7);
        bst_item8 = new JMenuItem(SystemConstants.BET_SORT8);
        betSort.add(bst_item8);
        betSort.addSeparator();
        bst_item9 = new JMenuItem(SystemConstants.BET_SORT9);
        betSort.add(bst_item9);
        bst_item10 = new JMenuItem(SystemConstants.BET_SORT10);
        betSort.add(bst_item10);
        betSort.addSeparator();
        bst_item11 = new JMenuItem(SystemConstants.BET_SORT11);
        betSort.add(bst_item11);
        bst_item12 = new JMenuItem(SystemConstants.BET_SORT12);
        betSort.add(bst_item12);
        betSort.addSeparator();
        bst_item13 = new JMenuItem(SystemConstants.BET_SORT13);
        betSort.add(bst_item13);
        resultProc.add(betSort);
        resultProc.addSeparator();
        betSet = new JMenu(SystemConstants.BET_SET);
        betSet.setEnabled(false);
        betSet1 = new JMenu(SystemConstants.FILTER_UNION_SET);
        betSet.add(betSet1);
        bsts_item1 = new JMenuItem(SystemConstants.DEFAULT_BASCKET);
        betSet1.add(bsts_item1);
        betSet2 = new JMenu(SystemConstants.FILTER_MIXED_SET);
        betSet.add(betSet2);
        bsts_item2 = new JMenuItem(SystemConstants.DEFAULT_BASCKET);
        betSet2.add(bsts_item2);
        betSet3 = new JMenu(SystemConstants.FILTER_MINUS_SET);
        betSet.add(betSet3);
        bsts_item3 = new JMenuItem(SystemConstants.DEFAULT_BASCKET);
        betSet3.add(bsts_item3);
        betSet4 = new JMenu(SystemConstants.FILTER_DIFF_SET);
        betSet.add(betSet4);
        bsts_item4 = new JMenuItem(SystemConstants.DEFAULT_BASCKET);
        betSet4.add(bsts_item4);
        resultProc.add(betSet);
        betData = new JMenu(SystemConstants.BET_PRO_DT);
        betData.setEnabled(false);
        bd_item1 = new JMenuItem(SystemConstants.FILTER_UNION_SET);
        betData.add(bd_item1);
        bd_item2 = new JMenuItem(SystemConstants.FILTER_MIXED_SET);
        betData.add(bd_item2);
        bd_item3 = new JMenuItem(SystemConstants.FILTER_MINUS_SET);
        betData.add(bd_item3);
        bd_item4 = new JMenuItem(SystemConstants.FILTER_DIFF_SET);
        betData.add(bd_item4);
        resultProc.add(betData);
        resultProc.addSeparator();
        rp_item10 = new JMenuItem(SystemConstants.RESULT_PROGRESS10);
        rp_item10.setEnabled(false);
        resultProc.add(rp_item10);
        rp_item11 = new JMenuItem(SystemConstants.RESULT_PROGRESS11);
        rp_item11.setEnabled(false);
        resultProc.add(rp_item11);
        resultProc.addSeparator();
        rp_item12 = new JMenuItem(SystemConstants.RESULT_PROGRESS12);
        rp_item12.setEnabled(false);
        resultProc.add(rp_item12);
        rp_item13 = new JMenuItem(SystemConstants.RESULT_PROGRESS13);
        rp_item13.setEnabled(false);
        resultProc.add(rp_item13);
        jmb.add(resultProc);
        procPane.add(jmb);

        listModel = new BasketListModel();
        basketList = new JList(listModel);
        procResult = new JScrollPane(basketList);
        procResult.setBackground(Color.WHITE);
        procResult.setPreferredSize(new java.awt.Dimension(288, 80));
        procPane.add(procResult);
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initFilterPane() {
        filterPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        filterPane.setBorder(new EtchedBorder());
        filterPane.setPreferredSize(new java.awt.Dimension(300, 484));

        initFilterMenu();
        filterPane.add(filterBar);

        initFilterTabbedPane();
        filterPane.add(filterTab);

        initFilterBetPane();
        filterPane.add(filterbetPane);
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initFilterMenu() {
        filterBar = new JMenuBar();
        filterBar.setPreferredSize(new java.awt.Dimension(70, 15));
        filterOperation = new JMenu(SystemConstants.FILTER_OPTION);
        openFilter = new JMenuItem(SystemConstants.FILTER_OPEN);
        filterOperation.add(openFilter);
        saveFilter = new JMenuItem(SystemConstants.FILTER_SAVE);
        filterOperation.add(saveFilter);
        clearFilter = new JMenuItem(SystemConstants.FILTER_CLEAR);
        filterOperation.add(clearFilter);
        filterOperation.addSeparator();
        singleVerify = new JMenuItem(SystemConstants.FILTER_SINGLE_VERIFY);
        filterOperation.add(singleVerify);
        verifybefFilter = new JMenuItem(SystemConstants.VERIFY_BEFORE_FILTER);
        verifybefFilter.setEnabled(false);
        filterOperation.add(verifybefFilter);
        filterVerify = new JMenuItem(SystemConstants.FILTER_VERIFY);
        filterVerify.setEnabled(false);
        filterOperation.add(filterVerify);
        historyVerify = new JMenuItem(SystemConstants.FILTER_HISTORY_VERIFY);
        filterOperation.add(historyVerify);
        filterOperation.addSeparator();
        filterRecycle = new JMenuItem(SystemConstants.FILTER_RECYCLE);
        filterRecycle.setEnabled(false);
        filterOperation.add(filterRecycle);
        filterOperation.addSeparator();
        keepFilter = new JMenuItem(SystemConstants.KEEP_FILTER);
        filterOperation.add(keepFilter);
        pluginMana = new JMenuItem(SystemConstants.PLUGIN_MANAGER);
        filterOperation.add(pluginMana);
        filterOperation.addSeparator();
        showFilterProg = new JMenuItem(SystemConstants.SHOW_FILTER_PROGRESS);
        filterOperation.add(showFilterProg);
        batFilter = new JMenuItem(SystemConstants.BAT_FILTER);
        filterOperation.add(batFilter);
        filterBar.add(filterOperation);
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initFilterTabbedPane() {
        filterTab = new JTabbedPane();
        filterTab.setBorder(null);
        filterTab.setPreferredSize(new java.awt.Dimension(300, 420));

        initFilterCommonPane();
        filterTab.add(filterCommon);
        filterTab.setTitleAt(0, SystemConstants.FILTER_COMMON);

        initFilterAdvancePane();
        filterTab.add(filterAdvance);
        filterTab.setTitleAt(1, SystemConstants.FILTER_ADVANCE);

        initFilterPluginPane();
        filterTab.add(filterPlugin);
        filterTab.setTitleAt(2, SystemConstants.FILTER_PLUGIN);

        initFilterSetPane();
        filterTab.add(filterSet);
        filterTab.setTitleAt(3, SystemConstants.FILTER_SET);
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initFilterSetPane() {
        filterSet = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterSet.setBorder(new EtchedBorder());
        filterSet.setPreferredSize(new java.awt.Dimension(300, 420));

        JPanel top_pane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top_pane.setPreferredSize(new java.awt.Dimension(280, 80));
        setGroup = new ButtonGroup();
        JPanel setPane = new JPanel(new GridLayout(2, 2));
        setPane.setBorder(new TitledBorder(SystemConstants.FILTER_CALC_RULE));
        unionSet = new JRadioButton(SystemConstants.FILTER_UNION_SET);
        mixedSet = new JRadioButton(SystemConstants.FILTER_MIXED_SET);
        minusSet = new JRadioButton(SystemConstants.FILTER_MINUS_SET);
        diffSet = new JRadioButton(SystemConstants.FILTER_DIFF_SET);
        setGroup.add(unionSet);
        setGroup.add(mixedSet);
        setGroup.add(minusSet);
        setGroup.add(diffSet);
        setPane.add(unionSet);
        setPane.add(mixedSet);
        setPane.add(minusSet);
        setPane.add(diffSet);
        top_pane.add(setPane);
        JPanel bntPane = new JPanel(new GridLayout(3, 1));
        advanceSet = new JButton(SystemConstants.FILTER_ADVANCE_SET);
        advanceSet.setBackground(ColorConstants.BUTTON_COLOR);
        advanceSet.setPreferredSize(new java.awt.Dimension(120, 20));
        bntPane.add(advanceSet);
        clearSet = new JButton(SystemConstants.FILTER_CLEAR_SET);
        clearSet.setPreferredSize(new java.awt.Dimension(60, 20));
        clearSet.setEnabled(false);
        bntPane.add(clearSet);
        askType = new JCheckBox(SystemConstants.FILTER_ASK_TYPE);
        askType.setPreferredSize(new java.awt.Dimension(60, 20));
        bntPane.add(askType);
        top_pane.add(bntPane);
        filterSet.add(top_pane);

        JPanel bottom_pane = new JPanel();
        bottom_pane.setPreferredSize(new java.awt.Dimension(283, 295));
        bottom_pane.setBorder(new EtchedBorder());
        filterSet.add(bottom_pane);

    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initFilterPluginPane() {
        filterPlugin = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPlugin.setBorder(new EtchedBorder());
        filterPlugin.setPreferredSize(new java.awt.Dimension(300, 420));

        pluginfilterAll = new JButton(SystemConstants.FILTER_ALL);
        pluginfilterAll.setPreferredSize(new java.awt.Dimension(40, 20));
        pluginfilterAll.setBackground(Color.WHITE);
        pluginfilterAll.setForeground(Color.BLUE);
        pluginfilterAll.setBorder(null);
        filterPlugin.add(pluginfilterAll);
        JLabel keep = new JLabel(SystemConstants.EMPTY_STRING);
        keep.setPreferredSize(new java.awt.Dimension(185, 20));
        filterPlugin.add(keep);
        pluginallowerrBtn = new JButton(SystemConstants.ALLOW_ERROR);
        pluginallowerrBtn.setPreferredSize(new java.awt.Dimension(40, 20));
        pluginallowerrBtn.setBackground(Color.WHITE);
        pluginallowerrBtn.setForeground(Color.BLUE);
        pluginallowerrBtn.setBorder(null);
        filterPlugin.add(pluginallowerrBtn);

        initFilterPluginTab();
        filterPlugin.add(filterPluginTab);
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initFilterPluginTab() {
        filterPluginTab = new JTabbedPane(JTabbedPane.LEFT);
        filterPluginTab.setBorder(new EtchedBorder());
        filterPluginTab.setPreferredSize(new java.awt.Dimension(300, 380));

        initFilterPluginAPane();
        filterPluginTab.add(filterPluginA);
        filterPluginTab.setTitleAt(0, SystemConstants.A);
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initFilterPluginAPane() {
        filterPluginA = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPluginA.setPreferredSize(new java.awt.Dimension(300, 380));

        // a filter condition
        pluginA1 = new JCheckBox(SystemConstants.FILTER_PLUGINA1);
        pluginA1.setPreferredSize(new java.awt.Dimension(78, 16));
        filterPluginA.add(pluginA1);
        pluginAText1 = new JTextField();
        pluginAText1.setPreferredSize(new java.awt.Dimension(106, 16));
        pluginAText1.setEditable(false);
        pluginAText1.setEnabled(false);
        filterPluginA.add(pluginAText1);
        pluginABtn1 = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        pluginABtn1.setPreferredSize(new java.awt.Dimension(20, 16));
        filterPluginA.add(pluginABtn1);
        pluginAerr1 = new JCheckBox();
        pluginAerr1.setPreferredSize(new java.awt.Dimension(20, 16));
        pluginAerr1.setEnabled(false);
        filterPluginA.add(pluginAerr1);
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initFilterAdvancePane() {
        filterAdvance = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterAdvance.setBorder(new EtchedBorder());
        filterAdvance.setPreferredSize(new java.awt.Dimension(300, 420));

        advancefilterAll = new JButton(SystemConstants.FILTER_ALL);
        advancefilterAll.setPreferredSize(new java.awt.Dimension(40, 20));
        advancefilterAll.setBackground(Color.WHITE);
        advancefilterAll.setForeground(Color.BLUE);
        advancefilterAll.setBorder(null);
        filterAdvance.add(advancefilterAll);
        JLabel keep = new JLabel(SystemConstants.EMPTY_STRING);
        keep.setPreferredSize(new java.awt.Dimension(185, 20));
        filterAdvance.add(keep);
        advanceallowerrBtn = new JButton(SystemConstants.ALLOW_ERROR);
        advanceallowerrBtn.setPreferredSize(new java.awt.Dimension(40, 20));
        advanceallowerrBtn.setBackground(Color.WHITE);
        advanceallowerrBtn.setForeground(Color.BLUE);
        advanceallowerrBtn.setBorder(null);
        filterAdvance.add(advanceallowerrBtn);

        initFilterAdvanceTab();
        filterAdvance.add(filterAdvanceTab);
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initFilterAdvanceTab() {
        filterAdvanceTab = new JTabbedPane(JTabbedPane.LEFT);
        filterAdvanceTab.setBorder(new EtchedBorder());
        filterAdvanceTab.setPreferredSize(new java.awt.Dimension(300, 380));

        initFilterAdvanceAPane();
        filterAdvanceTab.add(filterAdvanceA);
        filterAdvanceTab.setTitleAt(0, SystemConstants.A);

        initFilterAdvanceBPane();
        filterAdvanceTab.add(filterAdvanceB);
        filterAdvanceTab.setTitleAt(1, SystemConstants.B);
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initFilterAdvanceBPane() {
        filterAdvanceB = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterAdvanceB.setPreferredSize(new java.awt.Dimension(300, 380));

        // a filter condition
        advanceB1 = new JCheckBox(SystemConstants.FILTER_ADVANCEB1);
        advanceB1.setPreferredSize(new java.awt.Dimension(78, 16));
        filterAdvanceB.add(advanceB1);
        advanceBText1 = new JTextField();
        advanceBText1.setPreferredSize(new java.awt.Dimension(106, 16));
        advanceBText1.setEditable(false);
        advanceBText1.setEnabled(false);
        filterAdvanceB.add(advanceBText1);
        advanceBBtn1 = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        advanceBBtn1.setPreferredSize(new java.awt.Dimension(20, 16));
        filterAdvanceB.add(advanceBBtn1);
        advanceBerr1 = new JCheckBox();
        advanceBerr1.setPreferredSize(new java.awt.Dimension(20, 16));
        advanceBerr1.setEnabled(false);
        filterAdvanceB.add(advanceBerr1);
        // a filter condition
        advanceB2 = new JCheckBox(SystemConstants.FILTER_ADVANCEB2);
        advanceB2.setPreferredSize(new java.awt.Dimension(78, 16));
        filterAdvanceB.add(advanceB2);
        advanceBText2 = new JTextField();
        advanceBText2.setPreferredSize(new java.awt.Dimension(106, 16));
        advanceBText2.setEditable(false);
        advanceBText2.setEnabled(false);
        filterAdvanceB.add(advanceBText2);
        advanceBBtn2 = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        advanceBBtn2.setPreferredSize(new java.awt.Dimension(20, 16));
        filterAdvanceB.add(advanceBBtn2);
        advanceBerr2 = new JCheckBox();
        advanceBerr2.setPreferredSize(new java.awt.Dimension(20, 16));
        advanceBerr2.setEnabled(false);
        filterAdvanceB.add(advanceBerr2);
        // a filter condition
        advanceB3 = new JCheckBox(SystemConstants.FILTER_ADVANCEB3);
        advanceB3.setPreferredSize(new java.awt.Dimension(78, 16));
        filterAdvanceB.add(advanceB3);
        advanceBText3 = new JTextField();
        advanceBText3.setPreferredSize(new java.awt.Dimension(106, 16));
        advanceBText3.setEditable(false);
        advanceBText3.setEnabled(false);
        filterAdvanceB.add(advanceBText3);
        advanceBBtn3 = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        advanceBBtn3.setPreferredSize(new java.awt.Dimension(20, 16));
        filterAdvanceB.add(advanceBBtn3);
        advanceBerr3 = new JCheckBox();
        advanceBerr3.setPreferredSize(new java.awt.Dimension(20, 16));
        advanceBerr3.setEnabled(false);
        filterAdvanceB.add(advanceBerr3);
        // a filter condition
        advanceB4 = new JCheckBox(SystemConstants.FILTER_ADVANCEB4);
        advanceB4.setPreferredSize(new java.awt.Dimension(78, 16));
        filterAdvanceB.add(advanceB4);
        advanceBText4 = new JTextField();
        advanceBText4.setPreferredSize(new java.awt.Dimension(106, 16));
        advanceBText4.setEditable(false);
        advanceBText4.setEnabled(false);
        filterAdvanceB.add(advanceBText4);
        advanceBBtn4 = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        advanceBBtn4.setPreferredSize(new java.awt.Dimension(20, 16));
        filterAdvanceB.add(advanceBBtn4);
        advanceBerr4 = new JCheckBox();
        advanceBerr4.setPreferredSize(new java.awt.Dimension(20, 16));
        advanceBerr4.setEnabled(false);
        filterAdvanceB.add(advanceBerr4);
        // a filter condition
        advanceB5 = new JCheckBox(SystemConstants.FILTER_ADVANCEB5);
        advanceB5.setPreferredSize(new java.awt.Dimension(78, 16));
        filterAdvanceB.add(advanceB5);
        advanceBText5 = new JTextField();
        advanceBText5.setPreferredSize(new java.awt.Dimension(106, 16));
        advanceBText5.setEditable(false);
        advanceBText5.setEnabled(false);
        filterAdvanceB.add(advanceBText5);
        advanceBBtn5 = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        advanceBBtn5.setPreferredSize(new java.awt.Dimension(20, 16));
        filterAdvanceB.add(advanceBBtn5);
        advanceBerr5 = new JCheckBox();
        advanceBerr5.setPreferredSize(new java.awt.Dimension(20, 16));
        advanceBerr5.setEnabled(false);
        filterAdvanceB.add(advanceBerr5);
        // a filter condition
        advanceB6 = new JCheckBox(SystemConstants.FILTER_ADVANCEB6);
        advanceB6.setPreferredSize(new java.awt.Dimension(78, 16));
        filterAdvanceB.add(advanceB6);
        advanceBText6 = new JTextField();
        advanceBText6.setPreferredSize(new java.awt.Dimension(106, 16));
        advanceBText6.setEditable(false);
        advanceBText6.setEnabled(false);
        filterAdvanceB.add(advanceBText6);
        advanceBBtn6 = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        advanceBBtn6.setPreferredSize(new java.awt.Dimension(20, 16));
        filterAdvanceB.add(advanceBBtn6);
        advanceBerr6 = new JCheckBox();
        advanceBerr6.setPreferredSize(new java.awt.Dimension(20, 16));
        advanceBerr6.setEnabled(false);
        filterAdvanceB.add(advanceBerr6);
        // a filter condition
        advanceB7 = new JCheckBox(SystemConstants.FILTER_ADVANCEB7);
        advanceB7.setPreferredSize(new java.awt.Dimension(78, 16));
        filterAdvanceB.add(advanceB7);
        advanceBText7 = new JTextField();
        advanceBText7.setPreferredSize(new java.awt.Dimension(106, 16));
        advanceBText7.setEditable(false);
        advanceBText7.setEnabled(false);
        filterAdvanceB.add(advanceBText7);
        advanceBBtn7 = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        advanceBBtn7.setPreferredSize(new java.awt.Dimension(20, 16));
        filterAdvanceB.add(advanceBBtn7);
        advanceBerr7 = new JCheckBox();
        advanceBerr7.setPreferredSize(new java.awt.Dimension(20, 16));
        advanceBerr7.setEnabled(false);
        filterAdvanceB.add(advanceBerr7);
        // a filter condition
        advanceB8 = new JCheckBox(SystemConstants.FILTER_ADVANCEB8);
        advanceB8.setPreferredSize(new java.awt.Dimension(78, 16));
        filterAdvanceB.add(advanceB8);
        advanceBText8 = new JTextField();
        advanceBText8.setPreferredSize(new java.awt.Dimension(106, 16));
        advanceBText8.setEditable(false);
        advanceBText8.setEnabled(false);
        filterAdvanceB.add(advanceBText8);
        advanceBBtn8 = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        advanceBBtn8.setPreferredSize(new java.awt.Dimension(20, 16));
        filterAdvanceB.add(advanceBBtn8);
        advanceBerr8 = new JCheckBox();
        advanceBerr8.setPreferredSize(new java.awt.Dimension(20, 16));
        advanceBerr8.setEnabled(false);
        filterAdvanceB.add(advanceBerr8);
        // a filter condition
        advanceB9 = new JCheckBox(SystemConstants.FILTER_ADVANCEB9);
        advanceB9.setPreferredSize(new java.awt.Dimension(78, 16));
        filterAdvanceB.add(advanceB9);
        advanceBText9 = new JTextField();
        advanceBText9.setPreferredSize(new java.awt.Dimension(106, 16));
        advanceBText9.setEditable(false);
        advanceBText9.setEnabled(false);
        filterAdvanceB.add(advanceBText9);
        advanceBBtn9 = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        advanceBBtn9.setPreferredSize(new java.awt.Dimension(20, 16));
        filterAdvanceB.add(advanceBBtn9);
        advanceBerr9 = new JCheckBox();
        advanceBerr9.setPreferredSize(new java.awt.Dimension(20, 16));
        advanceBerr9.setEnabled(false);
        filterAdvanceB.add(advanceBerr9);
        // a filter condition
        advanceB10 = new JCheckBox(SystemConstants.FILTER_ADVANCEB10);
        advanceB10.setPreferredSize(new java.awt.Dimension(78, 16));
        filterAdvanceB.add(advanceB10);
        advanceBText10 = new JTextField();
        advanceBText10.setPreferredSize(new java.awt.Dimension(106, 16));
        advanceBText10.setEditable(false);
        advanceBText10.setEnabled(false);
        filterAdvanceB.add(advanceBText10);
        advanceBBtn10 = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        advanceBBtn10.setPreferredSize(new java.awt.Dimension(20, 16));
        filterAdvanceB.add(advanceBBtn10);
        advanceBerr10 = new JCheckBox();
        advanceBerr10.setPreferredSize(new java.awt.Dimension(20, 16));
        advanceBerr10.setEnabled(false);
        filterAdvanceB.add(advanceBerr10);
        // a filter condition
        advanceB11 = new JCheckBox(SystemConstants.FILTER_ADVANCEB11);
        advanceB11.setPreferredSize(new java.awt.Dimension(78, 16));
        filterAdvanceB.add(advanceB11);
        advanceBText11 = new JTextField();
        advanceBText11.setPreferredSize(new java.awt.Dimension(106, 16));
        advanceBText11.setEditable(false);
        advanceBText11.setEnabled(false);
        filterAdvanceB.add(advanceBText11);
        advanceBBtn11 = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        advanceBBtn11.setPreferredSize(new java.awt.Dimension(20, 16));
        filterAdvanceB.add(advanceBBtn11);
        advanceBerr11 = new JCheckBox();
        advanceBerr11.setPreferredSize(new java.awt.Dimension(20, 16));
        advanceBerr11.setEnabled(false);
        filterAdvanceB.add(advanceBerr11);
        // a filter condition
        advanceB12 = new JCheckBox(SystemConstants.FILTER_ADVANCEB12);
        advanceB12.setPreferredSize(new java.awt.Dimension(78, 16));
        filterAdvanceB.add(advanceB12);
        advanceBText12 = new JTextField();
        advanceBText12.setPreferredSize(new java.awt.Dimension(106, 16));
        advanceBText12.setEditable(false);
        advanceBText12.setEnabled(false);
        filterAdvanceB.add(advanceBText12);
        advanceBBtn12 = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        advanceBBtn12.setPreferredSize(new java.awt.Dimension(20, 16));
        filterAdvanceB.add(advanceBBtn12);
        advanceBerr12 = new JCheckBox();
        advanceBerr12.setPreferredSize(new java.awt.Dimension(20, 16));
        advanceBerr12.setEnabled(false);
        filterAdvanceB.add(advanceBerr12);
        // a filter condition
        advanceB13 = new JCheckBox(SystemConstants.FILTER_ADVANCEB13);
        advanceB13.setPreferredSize(new java.awt.Dimension(78, 16));
        filterAdvanceB.add(advanceB13);
        advanceBText13 = new JTextField();
        advanceBText13.setPreferredSize(new java.awt.Dimension(106, 16));
        advanceBText13.setEditable(false);
        advanceBText13.setEnabled(false);
        filterAdvanceB.add(advanceBText13);
        advanceBBtn13 = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        advanceBBtn13.setPreferredSize(new java.awt.Dimension(20, 16));
        filterAdvanceB.add(advanceBBtn13);
        advanceBerr13 = new JCheckBox();
        advanceBerr13.setPreferredSize(new java.awt.Dimension(20, 16));
        advanceBerr13.setEnabled(false);
        filterAdvanceB.add(advanceBerr13);
        // a filter condition
        advanceB14 = new JCheckBox(SystemConstants.FILTER_ADVANCEB14);
        advanceB14.setPreferredSize(new java.awt.Dimension(78, 16));
        filterAdvanceB.add(advanceB14);
        advanceBText14 = new JTextField();
        advanceBText14.setPreferredSize(new java.awt.Dimension(106, 16));
        advanceBText14.setEditable(false);
        advanceBText14.setEnabled(false);
        filterAdvanceB.add(advanceBText14);
        advanceBBtn14 = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        advanceBBtn14.setPreferredSize(new java.awt.Dimension(20, 16));
        filterAdvanceB.add(advanceBBtn14);
        advanceBerr14 = new JCheckBox();
        advanceBerr14.setPreferredSize(new java.awt.Dimension(20, 16));
        advanceBerr14.setEnabled(false);
        filterAdvanceB.add(advanceBerr14);
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initFilterAdvanceAPane() {
        filterAdvanceA = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterAdvanceA.setPreferredSize(new java.awt.Dimension(300, 380));

        // a filter condition
        advanceA1 = new JCheckBox(SystemConstants.FILTER_ADVANCEA1);
        advanceA1.setPreferredSize(new java.awt.Dimension(78, 14));
        filterAdvanceA.add(advanceA1);
        advanceAText1 = new JTextField();
        advanceAText1.setPreferredSize(new java.awt.Dimension(106, 14));
        advanceAText1.setEditable(false);
        advanceAText1.setEnabled(false);
        filterAdvanceA.add(advanceAText1);
        advanceABtn1 = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        advanceABtn1.setPreferredSize(new java.awt.Dimension(20, 14));
        filterAdvanceA.add(advanceABtn1);
        advanceAerr1 = new JCheckBox();
        advanceAerr1.setPreferredSize(new java.awt.Dimension(20, 14));
        advanceAerr1.setEnabled(false);
        filterAdvanceA.add(advanceAerr1);
        // a filter condition
        advanceA2 = new JCheckBox(SystemConstants.FILTER_ADVANCEA2);
        advanceA2.setPreferredSize(new java.awt.Dimension(78, 14));
        filterAdvanceA.add(advanceA2);
        advanceAText2 = new JTextField();
        advanceAText2.setPreferredSize(new java.awt.Dimension(106, 14));
        advanceAText2.setEditable(false);
        advanceAText2.setEnabled(false);
        filterAdvanceA.add(advanceAText2);
        advanceABtn2 = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        advanceABtn2.setPreferredSize(new java.awt.Dimension(20, 14));
        filterAdvanceA.add(advanceABtn2);
        advanceAerr2 = new JCheckBox();
        advanceAerr2.setPreferredSize(new java.awt.Dimension(20, 14));
        advanceAerr2.setEnabled(false);
        filterAdvanceA.add(advanceAerr2);
        // a filter condition
        advanceA3 = new JCheckBox(SystemConstants.FILTER_ADVANCEA3);
        advanceA3.setPreferredSize(new java.awt.Dimension(78, 14));
        filterAdvanceA.add(advanceA3);
        advanceAText3 = new JTextField();
        advanceAText3.setPreferredSize(new java.awt.Dimension(106, 14));
        advanceAText3.setEditable(false);
        advanceAText3.setEnabled(false);
        filterAdvanceA.add(advanceAText3);
        advanceABtn3 = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        advanceABtn3.setPreferredSize(new java.awt.Dimension(20, 14));
        filterAdvanceA.add(advanceABtn3);
        advanceAerr3 = new JCheckBox();
        advanceAerr3.setPreferredSize(new java.awt.Dimension(20, 14));
        advanceAerr3.setEnabled(false);
        filterAdvanceA.add(advanceAerr3);
        // a filter condition
        advanceA4 = new JCheckBox(SystemConstants.FILTER_ADVANCEA4);
        advanceA4.setPreferredSize(new java.awt.Dimension(78, 14));
        filterAdvanceA.add(advanceA4);
        advanceAText4 = new JTextField();
        advanceAText4.setPreferredSize(new java.awt.Dimension(106, 14));
        advanceAText4.setEditable(false);
        advanceAText4.setEnabled(false);
        filterAdvanceA.add(advanceAText4);
        advanceABtn4 = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        advanceABtn4.setPreferredSize(new java.awt.Dimension(20, 14));
        filterAdvanceA.add(advanceABtn4);
        advanceAerr4 = new JCheckBox();
        advanceAerr4.setPreferredSize(new java.awt.Dimension(20, 14));
        advanceAerr4.setEnabled(false);
        filterAdvanceA.add(advanceAerr4);
        // a filter condition
        advanceA5 = new JCheckBox(SystemConstants.FILTER_ADVANCEA5);
        advanceA5.setPreferredSize(new java.awt.Dimension(78, 14));
        filterAdvanceA.add(advanceA5);
        advanceAText5 = new JTextField();
        advanceAText5.setPreferredSize(new java.awt.Dimension(106, 14));
        advanceAText5.setEditable(false);
        advanceAText5.setEnabled(false);
        filterAdvanceA.add(advanceAText5);
        advanceABtn5 = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        advanceABtn5.setPreferredSize(new java.awt.Dimension(20, 14));
        filterAdvanceA.add(advanceABtn5);
        advanceAerr5 = new JCheckBox();
        advanceAerr5.setPreferredSize(new java.awt.Dimension(20, 14));
        advanceAerr5.setEnabled(false);
        filterAdvanceA.add(advanceAerr5);
        // a filter condition
        advanceA6 = new JCheckBox(SystemConstants.FILTER_ADVANCEA6);
        advanceA6.setPreferredSize(new java.awt.Dimension(78, 14));
        filterAdvanceA.add(advanceA6);
        advanceAText6 = new JTextField();
        advanceAText6.setPreferredSize(new java.awt.Dimension(106, 14));
        advanceAText6.setEditable(false);
        advanceAText6.setEnabled(false);
        filterAdvanceA.add(advanceAText6);
        advanceABtn6 = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        advanceABtn6.setPreferredSize(new java.awt.Dimension(20, 14));
        filterAdvanceA.add(advanceABtn6);
        advanceAerr6 = new JCheckBox();
        advanceAerr6.setPreferredSize(new java.awt.Dimension(20, 14));
        advanceAerr6.setEnabled(false);
        filterAdvanceA.add(advanceAerr6);
        // a filter condition
        advanceA7 = new JCheckBox(SystemConstants.FILTER_ADVANCEA7);
        advanceA7.setPreferredSize(new java.awt.Dimension(78, 14));
        filterAdvanceA.add(advanceA7);
        advanceAText7 = new JTextField();
        advanceAText7.setPreferredSize(new java.awt.Dimension(106, 14));
        advanceAText7.setEditable(false);
        advanceAText7.setEnabled(false);
        filterAdvanceA.add(advanceAText7);
        advanceABtn7 = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        advanceABtn7.setPreferredSize(new java.awt.Dimension(20, 14));
        filterAdvanceA.add(advanceABtn7);
        advanceAerr7 = new JCheckBox();
        advanceAerr7.setPreferredSize(new java.awt.Dimension(20, 14));
        advanceAerr7.setEnabled(false);
        filterAdvanceA.add(advanceAerr7);
        // a filter condition
        advanceA8 = new JCheckBox(SystemConstants.FILTER_ADVANCEA8);
        advanceA8.setPreferredSize(new java.awt.Dimension(78, 14));
        filterAdvanceA.add(advanceA8);
        advanceAText8 = new JTextField();
        advanceAText8.setPreferredSize(new java.awt.Dimension(106, 14));
        advanceAText8.setEditable(false);
        advanceAText8.setEnabled(false);
        filterAdvanceA.add(advanceAText8);
        advanceABtn8 = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        advanceABtn8.setPreferredSize(new java.awt.Dimension(20, 14));
        filterAdvanceA.add(advanceABtn8);
        advanceAerr8 = new JCheckBox();
        advanceAerr8.setPreferredSize(new java.awt.Dimension(20, 14));
        advanceAerr8.setEnabled(false);
        filterAdvanceA.add(advanceAerr8);
        // a filter condition
        advanceA9 = new JCheckBox(SystemConstants.FILTER_ADVANCEA9);
        advanceA9.setPreferredSize(new java.awt.Dimension(78, 14));
        filterAdvanceA.add(advanceA9);
        advanceAText9 = new JTextField();
        advanceAText9.setPreferredSize(new java.awt.Dimension(106, 14));
        advanceAText9.setEditable(false);
        advanceAText9.setEnabled(false);
        filterAdvanceA.add(advanceAText9);
        advanceABtn9 = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        advanceABtn9.setPreferredSize(new java.awt.Dimension(20, 14));
        filterAdvanceA.add(advanceABtn9);
        advanceAerr9 = new JCheckBox();
        advanceAerr9.setPreferredSize(new java.awt.Dimension(20, 14));
        advanceAerr9.setEnabled(false);
        filterAdvanceA.add(advanceAerr9);
        // a filter condition
        advanceA10 = new JCheckBox(SystemConstants.FILTER_ADVANCEA10);
        advanceA10.setPreferredSize(new java.awt.Dimension(78, 14));
        filterAdvanceA.add(advanceA10);
        advanceAText10 = new JTextField();
        advanceAText10.setPreferredSize(new java.awt.Dimension(106, 14));
        advanceAText10.setEditable(false);
        advanceAText10.setEnabled(false);
        filterAdvanceA.add(advanceAText10);
        advanceABtn10 = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        advanceABtn10.setPreferredSize(new java.awt.Dimension(20, 14));
        filterAdvanceA.add(advanceABtn10);
        advanceAerr10 = new JCheckBox();
        advanceAerr10.setPreferredSize(new java.awt.Dimension(20, 14));
        advanceAerr10.setEnabled(false);
        filterAdvanceA.add(advanceAerr10);
        // a filter condition
        advanceA11 = new JCheckBox(SystemConstants.FILTER_ADVANCEA11);
        advanceA11.setPreferredSize(new java.awt.Dimension(78, 14));
        filterAdvanceA.add(advanceA11);
        advanceAText11 = new JTextField();
        advanceAText11.setPreferredSize(new java.awt.Dimension(106, 14));
        advanceAText11.setEditable(false);
        advanceAText11.setEnabled(false);
        filterAdvanceA.add(advanceAText11);
        advanceABtn11 = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        advanceABtn11.setPreferredSize(new java.awt.Dimension(20, 14));
        filterAdvanceA.add(advanceABtn11);
        advanceAerr11 = new JCheckBox();
        advanceAerr11.setPreferredSize(new java.awt.Dimension(20, 14));
        advanceAerr11.setEnabled(false);
        filterAdvanceA.add(advanceAerr11);
        // a filter condition
        advanceA12 = new JCheckBox(SystemConstants.FILTER_ADVANCEA12);
        advanceA12.setPreferredSize(new java.awt.Dimension(78, 14));
        filterAdvanceA.add(advanceA12);
        advanceAText12 = new JTextField();
        advanceAText12.setPreferredSize(new java.awt.Dimension(106, 14));
        advanceAText12.setEditable(false);
        advanceAText12.setEnabled(false);
        filterAdvanceA.add(advanceAText12);
        advanceABtn12 = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        advanceABtn12.setPreferredSize(new java.awt.Dimension(20, 14));
        filterAdvanceA.add(advanceABtn12);
        advanceAerr12 = new JCheckBox();
        advanceAerr12.setPreferredSize(new java.awt.Dimension(20, 14));
        advanceAerr12.setEnabled(false);
        filterAdvanceA.add(advanceAerr12);
        // a filter condition
        advanceA13 = new JCheckBox(SystemConstants.FILTER_ADVANCEA13);
        advanceA13.setPreferredSize(new java.awt.Dimension(78, 14));
        filterAdvanceA.add(advanceA13);
        advanceAText13 = new JTextField();
        advanceAText13.setPreferredSize(new java.awt.Dimension(106, 14));
        advanceAText13.setEditable(false);
        advanceAText13.setEnabled(false);
        filterAdvanceA.add(advanceAText13);
        advanceABtn13 = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        advanceABtn13.setPreferredSize(new java.awt.Dimension(20, 14));
        filterAdvanceA.add(advanceABtn13);
        advanceAerr13 = new JCheckBox();
        advanceAerr13.setPreferredSize(new java.awt.Dimension(20, 14));
        advanceAerr13.setEnabled(false);
        filterAdvanceA.add(advanceAerr13);
        // a filter condition
        advanceA14 = new JCheckBox(SystemConstants.FILTER_ADVANCEA14);
        advanceA14.setPreferredSize(new java.awt.Dimension(78, 14));
        filterAdvanceA.add(advanceA14);
        advanceAText14 = new JTextField();
        advanceAText14.setPreferredSize(new java.awt.Dimension(106, 14));
        advanceAText14.setEditable(false);
        advanceAText14.setEnabled(false);
        filterAdvanceA.add(advanceAText14);
        advanceABtn14 = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        advanceABtn14.setPreferredSize(new java.awt.Dimension(20, 14));
        filterAdvanceA.add(advanceABtn14);
        advanceAerr14 = new JCheckBox();
        advanceAerr14.setPreferredSize(new java.awt.Dimension(20, 14));
        advanceAerr14.setEnabled(false);
        filterAdvanceA.add(advanceAerr14);
        // a filter condition
        advanceA15 = new JCheckBox(SystemConstants.FILTER_ADVANCEA15);
        advanceA15.setPreferredSize(new java.awt.Dimension(78, 14));
        filterAdvanceA.add(advanceA15);
        advanceAText15 = new JTextField();
        advanceAText15.setPreferredSize(new java.awt.Dimension(106, 14));
        advanceAText15.setEditable(false);
        advanceAText15.setEnabled(false);
        filterAdvanceA.add(advanceAText15);
        advanceABtn15 = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        advanceABtn15.setPreferredSize(new java.awt.Dimension(20, 14));
        filterAdvanceA.add(advanceABtn15);
        advanceAerr15 = new JCheckBox();
        advanceAerr15.setPreferredSize(new java.awt.Dimension(20, 14));
        advanceAerr15.setEnabled(false);
        filterAdvanceA.add(advanceAerr15);
        // a filter condition
        advanceA16 = new JCheckBox(SystemConstants.FILTER_ADVANCEA16);
        advanceA16.setPreferredSize(new java.awt.Dimension(78, 14));
        filterAdvanceA.add(advanceA16);
        advanceAText16 = new JTextField();
        advanceAText16.setPreferredSize(new java.awt.Dimension(106, 14));
        advanceAText16.setEditable(false);
        advanceAText16.setEnabled(false);
        filterAdvanceA.add(advanceAText16);
        advanceABtn16 = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        advanceABtn16.setPreferredSize(new java.awt.Dimension(20, 14));
        filterAdvanceA.add(advanceABtn16);
        advanceAerr16 = new JCheckBox();
        advanceAerr16.setPreferredSize(new java.awt.Dimension(20, 14));
        advanceAerr16.setEnabled(false);
        filterAdvanceA.add(advanceAerr16);
        // a filter condition
        advanceA17 = new JCheckBox(SystemConstants.FILTER_ADVANCEA17);
        advanceA17.setPreferredSize(new java.awt.Dimension(78, 14));
        filterAdvanceA.add(advanceA17);
        advanceAText17 = new JTextField();
        advanceAText17.setPreferredSize(new java.awt.Dimension(106, 14));
        advanceAText17.setEditable(false);
        advanceAText17.setEnabled(false);
        filterAdvanceA.add(advanceAText17);
        advanceABtn17 = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        advanceABtn17.setPreferredSize(new java.awt.Dimension(20, 14));
        filterAdvanceA.add(advanceABtn17);
        advanceAerr17 = new JCheckBox();
        advanceAerr17.setPreferredSize(new java.awt.Dimension(20, 14));
        advanceAerr17.setEnabled(false);
        filterAdvanceA.add(advanceAerr17);
        // a filter condition
        advanceA18 = new JCheckBox(SystemConstants.FILTER_ADVANCEA18);
        advanceA18.setPreferredSize(new java.awt.Dimension(78, 14));
        filterAdvanceA.add(advanceA18);
        advanceAText18 = new JTextField();
        advanceAText18.setPreferredSize(new java.awt.Dimension(106, 14));
        advanceAText18.setEditable(false);
        advanceAText18.setEnabled(false);
        filterAdvanceA.add(advanceAText18);
        advanceABtn18 = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        advanceABtn18.setPreferredSize(new java.awt.Dimension(20, 14));
        filterAdvanceA.add(advanceABtn18);
        advanceAerr18 = new JCheckBox();
        advanceAerr18.setPreferredSize(new java.awt.Dimension(20, 14));
        advanceAerr18.setEnabled(false);
        filterAdvanceA.add(advanceAerr18);
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initFilterCommonPane() {
        filterCommon = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterCommon.setBorder(new EtchedBorder());
        filterCommon.setPreferredSize(new java.awt.Dimension(300, 420));

        commonfilterAll = new JButton(SystemConstants.FILTER_ALL);
        commonfilterAll.setPreferredSize(new java.awt.Dimension(40, 20));
        commonfilterAll.setBackground(Color.WHITE);
        commonfilterAll.setForeground(Color.BLUE);
        commonfilterAll.setBorder(null);
        filterCommon.add(commonfilterAll);
        commonbatfilter = new JButton(SystemConstants.BAT_FILTER_ASSIST);
        commonbatfilter.setPreferredSize(new java.awt.Dimension(80, 20));
        commonbatfilter.setBackground(Color.WHITE);
        commonbatfilter.setForeground(Color.BLUE);
        commonbatfilter.setBorder(null);
        filterCommon.add(commonbatfilter);
        JLabel keep = new JLabel(SystemConstants.KEEP_FILTER_RESULT);
        keep.setPreferredSize(new java.awt.Dimension(105, 20));
        filterCommon.add(keep);
        commonallowerrBtn = new JButton(SystemConstants.ALLOW_ERROR);
        commonallowerrBtn.setPreferredSize(new java.awt.Dimension(40, 20));
        commonallowerrBtn.setBackground(Color.WHITE);
        commonallowerrBtn.setForeground(Color.BLUE);
        commonallowerrBtn.setBorder(null);
        filterCommon.add(commonallowerrBtn);

        initFilterCommonTab();
        filterCommon.add(filterCommonTab);
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initFilterCommonTab() {
        filterCommonTab = new JTabbedPane(JTabbedPane.LEFT);
        filterCommonTab.setBorder(new EtchedBorder());
        filterCommonTab.setPreferredSize(new java.awt.Dimension(300, 380));

        initFilterCommonAPane();
        filterCommonTab.add(filterCommonA);
        filterCommonTab.setTitleAt(0, SystemConstants.A);

        initFilterCommonBPane();
        filterCommonTab.add(filterCommonB);
        filterCommonTab.setTitleAt(1, SystemConstants.B);
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initFilterCommonBPane() {
        filterCommonB = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterCommonB.setPreferredSize(new java.awt.Dimension(300, 380));

        // a filter condition
        firstOdds = new JCheckBox();
        firstOdds.setPreferredSize(new java.awt.Dimension(20, 16));
        firstOdds.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == e.SELECTED) {
                    left_firstOdds.setEnabled(true);
                    right_firstOdds.setEnabled(true);
                    firstOddserr.setEnabled(true);
                } else if (e.getStateChange() == e.DESELECTED) {
                    left_firstOdds.setEnabled(false);
                    right_firstOdds.setEnabled(false);
                    firstOddserr.setEnabled(false);
                }
            }
        });
        filterCommonB.add(firstOdds);
        left_firstOdds = new JComboBox(SystemConstants.DO_MATCHES);
        left_firstOdds.setPreferredSize(new java.awt.Dimension(42, 16));
        left_firstOdds.setEditable(false);
        left_firstOdds.setEnabled(false);
        filterCommonB.add(left_firstOdds);
        JLabel j1 = new JLabel(SystemConstants.FIRST_ODDS);
        j1.setPreferredSize(new java.awt.Dimension(100, 16));
        filterCommonB.add(j1);
        right_firstOdds = new JComboBox(SystemConstants.DO_MATCHES);
        right_firstOdds.setPreferredSize(new java.awt.Dimension(42, 16));
        right_firstOdds.setEditable(false);
        right_firstOdds.setEnabled(false);
        filterCommonB.add(right_firstOdds);
        firstOddserr = new JCheckBox();
        firstOddserr.setPreferredSize(new java.awt.Dimension(20, 16));
        firstOddserr.setEnabled(false);
        filterCommonB.add(firstOddserr);
        // a filter condition
        secondOdds = new JCheckBox();
        secondOdds.setPreferredSize(new java.awt.Dimension(20, 16));
        secondOdds.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == e.SELECTED) {
                    left_secondOdds.setEnabled(true);
                    right_secondOdds.setEnabled(true);
                    secondOddserr.setEnabled(true);
                } else if (e.getStateChange() == e.DESELECTED) {
                    left_secondOdds.setEnabled(false);
                    right_secondOdds.setEnabled(false);
                    secondOddserr.setEnabled(false);
                }
            }
        });
        filterCommonB.add(secondOdds);
        left_secondOdds = new JComboBox(SystemConstants.DO_MATCHES);
        left_secondOdds.setPreferredSize(new java.awt.Dimension(42, 16));
        left_secondOdds.setEditable(false);
        left_secondOdds.setEnabled(false);
        filterCommonB.add(left_secondOdds);
        JLabel j2 = new JLabel(SystemConstants.SECOND_ODDS);
        j2.setPreferredSize(new java.awt.Dimension(100, 16));
        filterCommonB.add(j2);
        right_secondOdds = new JComboBox(SystemConstants.DO_MATCHES);
        right_secondOdds.setPreferredSize(new java.awt.Dimension(42, 16));
        right_secondOdds.setEditable(false);
        right_secondOdds.setEnabled(false);
        filterCommonB.add(right_secondOdds);
        secondOddserr = new JCheckBox();
        secondOddserr.setPreferredSize(new java.awt.Dimension(20, 16));
        secondOddserr.setEnabled(false);
        filterCommonB.add(secondOddserr);
        // a filter condition
        thirdOdds = new JCheckBox();
        thirdOdds.setPreferredSize(new java.awt.Dimension(20, 16));
        thirdOdds.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == e.SELECTED) {
                    left_thirdOdds.setEnabled(true);
                    right_thirdOdds.setEnabled(true);
                    thirdOddserr.setEnabled(true);
                } else if (e.getStateChange() == e.DESELECTED) {
                    left_thirdOdds.setEnabled(false);
                    right_thirdOdds.setEnabled(false);
                    thirdOddserr.setEnabled(false);
                }
            }
        });
        filterCommonB.add(thirdOdds);
        left_thirdOdds = new JComboBox(SystemConstants.DO_MATCHES);
        left_thirdOdds.setPreferredSize(new java.awt.Dimension(42, 16));
        left_thirdOdds.setEditable(false);
        left_thirdOdds.setEnabled(false);
        filterCommonB.add(left_thirdOdds);
        JLabel j3 = new JLabel(SystemConstants.THIRD_ODDS);
        j3.setPreferredSize(new java.awt.Dimension(100, 16));
        filterCommonB.add(j3);
        right_thirdOdds = new JComboBox(SystemConstants.DO_MATCHES);
        right_thirdOdds.setPreferredSize(new java.awt.Dimension(42, 16));
        right_thirdOdds.setEditable(false);
        right_thirdOdds.setEnabled(false);
        filterCommonB.add(right_thirdOdds);
        thirdOddserr = new JCheckBox();
        thirdOddserr.setPreferredSize(new java.awt.Dimension(20, 16));
        thirdOddserr.setEnabled(false);
        filterCommonB.add(thirdOddserr);
        // a filter condition
        rangeRank = new JCheckBox();
        rangeRank.setPreferredSize(new java.awt.Dimension(20, 16));
        rangeRank.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == e.SELECTED) {
                    left_rangeRank.setEnabled(true);
                    right_rangeRank.setEnabled(true);
                    rangeRankerr.setEnabled(true);
                } else if (e.getStateChange() == e.DESELECTED) {
                    left_rangeRank.setEnabled(false);
                    right_rangeRank.setEnabled(false);
                    rangeRankerr.setEnabled(false);
                }
            }
        });
        filterCommonB.add(rangeRank);
        left_rangeRank = new JTextField();
        left_rangeRank.setPreferredSize(new java.awt.Dimension(42, 16));
        left_rangeRank.setEnabled(false);
        filterCommonB.add(left_rangeRank);
        JLabel j4 = new JLabel(SystemConstants.RANGE_RANK);
        j4.setPreferredSize(new java.awt.Dimension(100, 16));
        filterCommonB.add(j4);
        right_rangeRank = new JTextField();
        right_rangeRank.setPreferredSize(new java.awt.Dimension(42, 16));
        right_rangeRank.setEnabled(false);
        filterCommonB.add(right_rangeRank);
        rangeRankerr = new JCheckBox();
        rangeRankerr.setPreferredSize(new java.awt.Dimension(20, 16));
        rangeRankerr.setEnabled(false);
        filterCommonB.add(rangeRankerr);
        // a filter condition
        probility = new JCheckBox();
        probility.setPreferredSize(new java.awt.Dimension(20, 16));
        probility.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == e.SELECTED) {
                    left_probility.setEnabled(true);
                    right_probility.setEnabled(true);
                    probilityerr.setEnabled(true);
                } else if (e.getStateChange() == e.DESELECTED) {
                    left_probility.setEnabled(false);
                    right_probility.setEnabled(false);
                    probilityerr.setEnabled(false);
                }
            }
        });
        filterCommonB.add(probility);
        left_probility = new JTextField();
        left_probility.setPreferredSize(new java.awt.Dimension(42, 16));
        left_probility.setEnabled(false);
        filterCommonB.add(left_probility);
        JLabel j5 = new JLabel(SystemConstants.PROBILITY_CRS);
        j5.setPreferredSize(new java.awt.Dimension(100, 16));
        filterCommonB.add(j5);
        right_probility = new JTextField();
        right_probility.setPreferredSize(new java.awt.Dimension(42, 16));
        right_probility.setEnabled(false);
        filterCommonB.add(right_probility);
        probilityerr = new JCheckBox();
        probilityerr.setPreferredSize(new java.awt.Dimension(20, 16));
        probilityerr.setEnabled(false);
        filterCommonB.add(probilityerr);
        // a filter condition
        expectBet = new JCheckBox();
        expectBet.setPreferredSize(new java.awt.Dimension(20, 16));
        expectBet.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == e.SELECTED) {
                    left_expectBet.setEnabled(true);
                    right_expectBet.setEnabled(true);
                    expectBeterr.setEnabled(true);
                } else if (e.getStateChange() == e.DESELECTED) {
                    left_expectBet.setEnabled(false);
                    right_expectBet.setEnabled(false);
                    expectBeterr.setEnabled(false);
                }
            }
        });
        filterCommonB.add(expectBet);
        left_expectBet = new JTextField();
        left_expectBet.setPreferredSize(new java.awt.Dimension(42, 16));
        left_expectBet.setEnabled(false);
        filterCommonB.add(left_expectBet);
        JLabel j6 = new JLabel(SystemConstants.EXPECT_BET);
        j6.setPreferredSize(new java.awt.Dimension(100, 16));
        filterCommonB.add(j6);
        right_expectBet = new JTextField();
        right_expectBet.setPreferredSize(new java.awt.Dimension(42, 16));
        right_expectBet.setEnabled(false);
        filterCommonB.add(right_expectBet);
        expectBeterr = new JCheckBox();
        expectBeterr.setPreferredSize(new java.awt.Dimension(20, 16));
        expectBeterr.setEnabled(false);
        filterCommonB.add(expectBeterr);
        // a filter condition
        probilitySum = new JCheckBox();
        probilitySum.setPreferredSize(new java.awt.Dimension(20, 16));
        probilitySum.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == e.SELECTED) {
                    left_probilitySum.setEnabled(true);
                    right_probilitySum.setEnabled(true);
                    probilitySumerr.setEnabled(true);
                } else if (e.getStateChange() == e.DESELECTED) {
                    left_probilitySum.setEnabled(false);
                    right_probilitySum.setEnabled(false);
                    probilitySumerr.setEnabled(false);
                }
            }
        });
        filterCommonB.add(probilitySum);
        left_probilitySum = new JTextField();
        left_probilitySum.setPreferredSize(new java.awt.Dimension(42, 16));
        left_probilitySum.setEnabled(false);
        filterCommonB.add(left_probilitySum);
        JLabel j7 = new JLabel(SystemConstants.PROBILITY_SUM);
        j7.setPreferredSize(new java.awt.Dimension(100, 16));
        filterCommonB.add(j7);
        right_probilitySum = new JTextField();
        right_probilitySum.setPreferredSize(new java.awt.Dimension(42, 16));
        right_probilitySum.setEnabled(false);
        filterCommonB.add(right_probilitySum);
        probilitySumerr = new JCheckBox();
        probilitySumerr.setPreferredSize(new java.awt.Dimension(20, 16));
        probilitySumerr.setEnabled(false);
        filterCommonB.add(probilitySumerr);
        // a filter condition
        oddsSum = new JCheckBox();
        oddsSum.setPreferredSize(new java.awt.Dimension(20, 16));
        oddsSum.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == e.SELECTED) {
                    left_oddsSum.setEnabled(true);
                    right_oddsSum.setEnabled(true);
                    oddsSumerr.setEnabled(true);
                } else if (e.getStateChange() == e.DESELECTED) {
                    left_oddsSum.setEnabled(false);
                    right_oddsSum.setEnabled(false);
                    oddsSumerr.setEnabled(false);
                }
            }
        });
        filterCommonB.add(oddsSum);
        left_oddsSum = new JTextField();
        left_oddsSum.setPreferredSize(new java.awt.Dimension(42, 16));
        left_oddsSum.setEnabled(false);
        filterCommonB.add(left_oddsSum);
        JLabel j8 = new JLabel(SystemConstants.ODDS_SUM);
        j8.setPreferredSize(new java.awt.Dimension(100, 16));
        filterCommonB.add(j8);
        right_oddsSum = new JTextField();
        right_oddsSum.setPreferredSize(new java.awt.Dimension(42, 16));
        right_oddsSum.setEnabled(false);
        filterCommonB.add(right_oddsSum);
        oddsSumerr = new JCheckBox();
        oddsSumerr.setPreferredSize(new java.awt.Dimension(20, 16));
        oddsSumerr.setEnabled(false);
        filterCommonB.add(oddsSumerr);
        // a filter condition
        oddsCrs = new JCheckBox();
        oddsCrs.setPreferredSize(new java.awt.Dimension(20, 16));
        oddsCrs.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == e.SELECTED) {
                    left_oddsCrs.setEnabled(true);
                    right_oddsCrs.setEnabled(true);
                    oddsCrserr.setEnabled(true);
                } else if (e.getStateChange() == e.DESELECTED) {
                    left_oddsCrs.setEnabled(false);
                    right_oddsCrs.setEnabled(false);
                    oddsCrserr.setEnabled(false);
                }
            }
        });
        filterCommonB.add(oddsCrs);
        left_oddsCrs = new JTextField();
        left_oddsCrs.setPreferredSize(new java.awt.Dimension(42, 16));
        left_oddsCrs.setEnabled(false);
        filterCommonB.add(left_oddsCrs);
        JLabel j9 = new JLabel(SystemConstants.ODDS_CRS);
        j9.setPreferredSize(new java.awt.Dimension(100, 16));
        filterCommonB.add(j9);
        right_oddsCrs = new JTextField();
        right_oddsCrs.setPreferredSize(new java.awt.Dimension(42, 16));
        right_oddsCrs.setEnabled(false);
        filterCommonB.add(right_oddsCrs);
        oddsCrserr = new JCheckBox();
        oddsCrserr.setPreferredSize(new java.awt.Dimension(20, 16));
        oddsCrserr.setEnabled(false);
        filterCommonB.add(oddsCrserr);
        // a filter condition
        winIndice = new JCheckBox();
        winIndice.setPreferredSize(new java.awt.Dimension(20, 16));
        winIndice.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == e.SELECTED) {
                    left_winIndice.setEnabled(true);
                    right_winIndice.setEnabled(true);
                    winIndiceerr.setEnabled(true);
                } else if (e.getStateChange() == e.DESELECTED) {
                    left_winIndice.setEnabled(false);
                    right_winIndice.setEnabled(false);
                    winIndiceerr.setEnabled(false);
                }
            }
        });
        filterCommonB.add(winIndice);
        left_winIndice = new JTextField();
        left_winIndice.setPreferredSize(new java.awt.Dimension(42, 16));
        left_winIndice.setEnabled(false);
        filterCommonB.add(left_winIndice);
        JLabel j10 = new JLabel(SystemConstants.WIN_INDICE);
        j10.setPreferredSize(new java.awt.Dimension(100, 16));
        filterCommonB.add(j10);
        right_winIndice = new JTextField();
        right_winIndice.setPreferredSize(new java.awt.Dimension(42, 16));
        right_winIndice.setEnabled(false);
        filterCommonB.add(right_winIndice);
        winIndiceerr = new JCheckBox();
        winIndiceerr.setPreferredSize(new java.awt.Dimension(20, 16));
        winIndiceerr.setEnabled(false);
        filterCommonB.add(winIndiceerr);
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initFilterCommonAPane() {
        filterCommonA = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterCommonA.setPreferredSize(new java.awt.Dimension(300, 380));

        int i = 0;
        // a filter condition
        wins = new JCheckBox();
        wins.setPreferredSize(new java.awt.Dimension(20, 16));
        wins.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == e.SELECTED) {
                    left_wins.setEnabled(true);
                    right_wins.setEnabled(true);
                    winserr.setEnabled(true);
                } else if (e.getStateChange() == e.DESELECTED) {
                    left_wins.setEnabled(false);
                    right_wins.setEnabled(false);
                    winserr.setEnabled(false);
                }
            }
        });
        filterCommonA.add(wins);
        left_wins = new JComboBox(SystemConstants.DO_MATCHES);
        left_wins.setPreferredSize(new java.awt.Dimension(42, 16));
        left_wins.setEditable(false);
        left_wins.setEnabled(false);
        filterCommonA.add(left_wins);
        JLabel j1 = new JLabel(SystemConstants.WINS);
        j1.setPreferredSize(new java.awt.Dimension(100, 16));
        filterCommonA.add(j1);
        right_wins = new JComboBox(SystemConstants.DO_MATCHES);
        right_wins.setPreferredSize(new java.awt.Dimension(42, 16));
        right_wins.setEditable(false);
        right_wins.setEnabled(false);
        filterCommonA.add(right_wins);
        winserr = new JCheckBox();
        winserr.setPreferredSize(new java.awt.Dimension(20, 16));
        winserr.setEnabled(false);
        filterCommonA.add(winserr);
        // a filter condition
        draws = new JCheckBox();
        draws.setPreferredSize(new java.awt.Dimension(20, 16));
        draws.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == e.SELECTED) {
                    left_draws.setEnabled(true);
                    right_draws.setEnabled(true);
                    drawserr.setEnabled(true);
                } else if (e.getStateChange() == e.DESELECTED) {
                    left_draws.setEnabled(false);
                    right_draws.setEnabled(false);
                    drawserr.setEnabled(false);
                }
            }
        });
        filterCommonA.add(draws);
        left_draws = new JComboBox(SystemConstants.DO_MATCHES);
        left_draws.setPreferredSize(new java.awt.Dimension(42, 16));
        left_draws.setEditable(false);
        left_draws.setEnabled(false);
        filterCommonA.add(left_draws);
        JLabel j2 = new JLabel(SystemConstants.DRAWS);
        j2.setPreferredSize(new java.awt.Dimension(100, 16));
        filterCommonA.add(j2);
        right_draws = new JComboBox(SystemConstants.DO_MATCHES);
        right_draws.setPreferredSize(new java.awt.Dimension(42, 16));
        right_draws.setEditable(false);
        right_draws.setEnabled(false);
        filterCommonA.add(right_draws);
        drawserr = new JCheckBox();
        drawserr.setPreferredSize(new java.awt.Dimension(20, 16));
        drawserr.setEnabled(false);
        filterCommonA.add(drawserr);
        // a filter condition
        losses = new JCheckBox();
        losses.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == e.SELECTED) {
                    left_losses.setEnabled(true);
                    right_losses.setEnabled(true);
                    losseserr.setEnabled(true);
                } else if (e.getStateChange() == e.DESELECTED) {
                    left_losses.setEnabled(false);
                    right_losses.setEnabled(false);
                    losseserr.setEnabled(false);
                }
            }
        });
        losses.setPreferredSize(new java.awt.Dimension(20, 16));
        filterCommonA.add(losses);
        left_losses = new JComboBox(SystemConstants.DO_MATCHES);
        left_losses.setPreferredSize(new java.awt.Dimension(42, 16));
        left_losses.setEditable(false);
        left_losses.setEnabled(false);
        filterCommonA.add(left_losses);
        JLabel j3 = new JLabel(SystemConstants.LOSSES);
        j3.setPreferredSize(new java.awt.Dimension(100, 16));
        filterCommonA.add(j3);
        right_losses = new JComboBox(SystemConstants.DO_MATCHES);
        right_losses.setPreferredSize(new java.awt.Dimension(42, 16));
        right_losses.setEditable(false);
        right_losses.setEnabled(false);
        filterCommonA.add(right_losses);
        losseserr = new JCheckBox();
        losseserr.setPreferredSize(new java.awt.Dimension(20, 16));
        losseserr.setEnabled(false);
        filterCommonA.add(losseserr);
        // a filter condition
        scores = new JCheckBox();
        scores.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == e.SELECTED) {
                    left_scores.setEnabled(true);
                    right_scores.setEnabled(true);
                    scoreserr.setEnabled(true);
                } else if (e.getStateChange() == e.DESELECTED) {
                    left_scores.setEnabled(false);
                    right_scores.setEnabled(false);
                    scoreserr.setEnabled(false);
                }
            }
        });
        scores.setPreferredSize(new java.awt.Dimension(20, 16));
        filterCommonA.add(scores);
        left_scores = new JComboBox();
        left_scores.setPreferredSize(new java.awt.Dimension(42, 16));
        for (i = 0; i < SystemConstants.SCORES_I; i++) {
            left_scores.addItem(i);
        }
        left_scores.setEditable(false);
        left_scores.setEnabled(false);
        filterCommonA.add(left_scores);
        JLabel j4 = new JLabel(SystemConstants.SCORES);
        j4.setPreferredSize(new java.awt.Dimension(100, 16));
        filterCommonA.add(j4);
        right_scores = new JComboBox();
        right_scores.setPreferredSize(new java.awt.Dimension(42, 16));
        for (i = 0; i < SystemConstants.SCORES_I; i++) {
            right_scores.addItem(i);
        }
        right_scores.setEditable(false);
        right_scores.setEnabled(false);
        filterCommonA.add(right_scores);
        scoreserr = new JCheckBox();
        scoreserr.setPreferredSize(new java.awt.Dimension(20, 16));
        scoreserr.setEnabled(false);
        filterCommonA.add(scoreserr);
        // a filter condition
        breaks = new JCheckBox();
        breaks.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == e.SELECTED) {
                    left_breaks.setEnabled(true);
                    right_breaks.setEnabled(true);
                    breakserr.setEnabled(true);
                } else if (e.getStateChange() == e.DESELECTED) {
                    left_breaks.setEnabled(false);
                    right_breaks.setEnabled(false);
                    breakserr.setEnabled(false);
                }
            }
        });
        breaks.setPreferredSize(new java.awt.Dimension(20, 16));
        filterCommonA.add(breaks);
        left_breaks = new JComboBox(SystemConstants.DO_MATCHES);
        left_breaks.setPreferredSize(new java.awt.Dimension(42, 16));
        left_breaks.setEditable(false);
        left_breaks.setEnabled(false);
        filterCommonA.add(left_breaks);
        JLabel j5 = new JLabel(SystemConstants.BREAKS);
        j5.setPreferredSize(new java.awt.Dimension(100, 16));
        filterCommonA.add(j5);
        right_breaks = new JComboBox(SystemConstants.DO_MATCHES);
        right_breaks.setPreferredSize(new java.awt.Dimension(42, 16));
        right_breaks.setEditable(false);
        right_breaks.setEnabled(false);
        filterCommonA.add(right_breaks);
        breakserr = new JCheckBox();
        breakserr.setPreferredSize(new java.awt.Dimension(20, 16));
        breakserr.setEnabled(false);
        filterCommonA.add(breakserr);
        // a filter condition
        continus = new JCheckBox();
        continus.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == e.SELECTED) {
                    left_continus.setEnabled(true);
                    right_continus.setEnabled(true);
                    continuserr.setEnabled(true);
                } else if (e.getStateChange() == e.DESELECTED) {
                    left_continus.setEnabled(false);
                    right_continus.setEnabled(false);
                    continuserr.setEnabled(false);
                }
            }
        });
        continus.setPreferredSize(new java.awt.Dimension(20, 16));
        filterCommonA.add(continus);
        left_continus = new JComboBox(SystemConstants.DO_MATCHES);
        left_continus.setPreferredSize(new java.awt.Dimension(42, 16));
        left_continus.setEditable(false);
        left_continus.setEnabled(false);
        filterCommonA.add(left_continus);
        JLabel j6 = new JLabel(SystemConstants.CONTINUS);
        j6.setPreferredSize(new java.awt.Dimension(100, 16));
        filterCommonA.add(j6);
        right_continus = new JComboBox(SystemConstants.DO_MATCHES);
        right_continus.setPreferredSize(new java.awt.Dimension(42, 16));
        right_continus.setEditable(false);
        right_continus.setEnabled(false);
        filterCommonA.add(right_continus);
        continuserr = new JCheckBox();
        continuserr.setPreferredSize(new java.awt.Dimension(20, 16));
        continuserr.setEnabled(false);
        filterCommonA.add(continuserr);
        // a filter condition
        hostWinContinu = new JCheckBox();
        hostWinContinu.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == e.SELECTED) {
                    left_hostWinContinu.setEnabled(true);
                    right_hostWinContinu.setEnabled(true);
                    hostWinContinuerr.setEnabled(true);
                } else if (e.getStateChange() == e.DESELECTED) {
                    left_hostWinContinu.setEnabled(false);
                    right_hostWinContinu.setEnabled(false);
                    hostWinContinuerr.setEnabled(false);
                }
            }
        });
        hostWinContinu.setPreferredSize(new java.awt.Dimension(20, 16));
        filterCommonA.add(hostWinContinu);
        left_hostWinContinu = new JComboBox(SystemConstants.DO_MATCHES);
        left_hostWinContinu.setPreferredSize(new java.awt.Dimension(42, 16));
        left_hostWinContinu.setEditable(false);
        left_hostWinContinu.setEnabled(false);
        filterCommonA.add(left_hostWinContinu);
        JLabel j7 = new JLabel(SystemConstants.HOST_WIN_CON);
        j7.setPreferredSize(new java.awt.Dimension(100, 16));
        filterCommonA.add(j7);
        right_hostWinContinu = new JComboBox(SystemConstants.DO_MATCHES);
        right_hostWinContinu.setPreferredSize(new java.awt.Dimension(42, 16));
        right_hostWinContinu.setEditable(false);
        right_hostWinContinu.setEnabled(false);
        filterCommonA.add(right_hostWinContinu);
        hostWinContinuerr = new JCheckBox();
        hostWinContinuerr.setPreferredSize(new java.awt.Dimension(20, 16));
        hostWinContinuerr.setEnabled(false);
        filterCommonA.add(hostWinContinuerr);
        // a filter condition
        hostDrawContinu = new JCheckBox();
        hostDrawContinu.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == e.SELECTED) {
                    left_hostDrawContinu.setEnabled(true);
                    right_hostDrawContinu.setEnabled(true);
                    hostDrawContinuerr.setEnabled(true);
                } else if (e.getStateChange() == e.DESELECTED) {
                    left_hostDrawContinu.setEnabled(false);
                    right_hostDrawContinu.setEnabled(false);
                    hostDrawContinuerr.setEnabled(false);
                }
            }
        });
        hostDrawContinu.setPreferredSize(new java.awt.Dimension(20, 16));
        filterCommonA.add(hostDrawContinu);
        left_hostDrawContinu = new JComboBox(SystemConstants.DO_MATCHES);
        left_hostDrawContinu.setPreferredSize(new java.awt.Dimension(42, 16));
        left_hostDrawContinu.setEditable(false);
        left_hostDrawContinu.setEnabled(false);
        filterCommonA.add(left_hostDrawContinu);
        JLabel j8 = new JLabel(SystemConstants.HOST_DRAW_CON);
        j8.setPreferredSize(new java.awt.Dimension(100, 16));
        filterCommonA.add(j8);
        right_hostDrawContinu = new JComboBox(SystemConstants.DO_MATCHES);
        right_hostDrawContinu.setPreferredSize(new java.awt.Dimension(42, 16));
        right_hostDrawContinu.setEditable(false);
        right_hostDrawContinu.setEnabled(false);
        filterCommonA.add(right_hostDrawContinu);
        hostDrawContinuerr = new JCheckBox();
        hostDrawContinuerr.setPreferredSize(new java.awt.Dimension(20, 16));
        hostDrawContinuerr.setEnabled(false);
        filterCommonA.add(hostDrawContinuerr);
        // a filter condition
        hostLossContinu = new JCheckBox();
        hostLossContinu.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == e.SELECTED) {
                    left_hostLossContinu.setEnabled(true);
                    right_hostLossContinu.setEnabled(true);
                    hostLossContinuerr.setEnabled(true);
                } else if (e.getStateChange() == e.DESELECTED) {
                    left_hostLossContinu.setEnabled(false);
                    right_hostLossContinu.setEnabled(false);
                    hostLossContinuerr.setEnabled(false);
                }
            }
        });
        hostLossContinu.setPreferredSize(new java.awt.Dimension(20, 16));
        filterCommonA.add(hostLossContinu);
        left_hostLossContinu = new JComboBox(SystemConstants.DO_MATCHES);
        left_hostLossContinu.setPreferredSize(new java.awt.Dimension(42, 16));
        left_hostLossContinu.setEditable(false);
        left_hostLossContinu.setEnabled(false);
        filterCommonA.add(left_hostLossContinu);
        JLabel j9 = new JLabel(SystemConstants.HOST_LOSS_CON);
        j9.setPreferredSize(new java.awt.Dimension(100, 16));
        filterCommonA.add(j9);
        right_hostLossContinu = new JComboBox(SystemConstants.DO_MATCHES);
        right_hostLossContinu.setPreferredSize(new java.awt.Dimension(42, 16));
        right_hostLossContinu.setEditable(false);
        right_hostLossContinu.setEnabled(false);
        filterCommonA.add(right_hostLossContinu);
        hostLossContinuerr = new JCheckBox();
        hostLossContinuerr.setPreferredSize(new java.awt.Dimension(20, 16));
        hostLossContinuerr.setEnabled(false);
        filterCommonA.add(hostLossContinuerr);
        // a filter condition
        winDrawContinu = new JCheckBox();
        winDrawContinu.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == e.SELECTED) {
                    left_winDrawContinu.setEnabled(true);
                    right_winDrawContinu.setEnabled(true);
                    winDrawContinuerr.setEnabled(true);
                } else if (e.getStateChange() == e.DESELECTED) {
                    left_winDrawContinu.setEnabled(false);
                    right_winDrawContinu.setEnabled(false);
                    winDrawContinuerr.setEnabled(false);
                }
            }
        });
        winDrawContinu.setPreferredSize(new java.awt.Dimension(20, 16));
        filterCommonA.add(winDrawContinu);
        left_winDrawContinu = new JComboBox(SystemConstants.DO_MATCHES);
        left_winDrawContinu.setPreferredSize(new java.awt.Dimension(42, 16));
        left_winDrawContinu.setEditable(false);
        left_winDrawContinu.setEnabled(false);
        filterCommonA.add(left_winDrawContinu);
        JLabel j10 = new JLabel(SystemConstants.WIN_DRAW_CON);
        j10.setPreferredSize(new java.awt.Dimension(100, 16));
        filterCommonA.add(j10);
        right_winDrawContinu = new JComboBox(SystemConstants.DO_MATCHES);
        right_winDrawContinu.setPreferredSize(new java.awt.Dimension(42, 16));
        right_winDrawContinu.setEditable(false);
        right_winDrawContinu.setEnabled(false);
        filterCommonA.add(right_winDrawContinu);
        winDrawContinuerr = new JCheckBox();
        winDrawContinuerr.setPreferredSize(new java.awt.Dimension(20, 16));
        winDrawContinuerr.setEnabled(false);
        filterCommonA.add(winDrawContinuerr);
        // a filter condition
        winLossContinu = new JCheckBox();
        winLossContinu.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == e.SELECTED) {
                    left_winLossContinu.setEnabled(true);
                    right_winLossContinu.setEnabled(true);
                    winLossContinuerr.setEnabled(true);
                } else if (e.getStateChange() == e.DESELECTED) {
                    left_winLossContinu.setEnabled(false);
                    right_winLossContinu.setEnabled(false);
                    winLossContinuerr.setEnabled(false);
                }
            }
        });
        winLossContinu.setPreferredSize(new java.awt.Dimension(20, 16));
        filterCommonA.add(winLossContinu);
        left_winLossContinu = new JComboBox(SystemConstants.DO_MATCHES);
        left_winLossContinu.setPreferredSize(new java.awt.Dimension(42, 16));
        left_winLossContinu.setEditable(false);
        left_winLossContinu.setEnabled(false);
        filterCommonA.add(left_winLossContinu);
        JLabel j11 = new JLabel(SystemConstants.WIN_LOSS_CON);
        j11.setPreferredSize(new java.awt.Dimension(100, 16));
        filterCommonA.add(j11);
        right_winLossContinu = new JComboBox(SystemConstants.DO_MATCHES);
        right_winLossContinu.setPreferredSize(new java.awt.Dimension(42, 16));
        right_winLossContinu.setEditable(false);
        right_winLossContinu.setEnabled(false);
        filterCommonA.add(right_winLossContinu);
        winLossContinuerr = new JCheckBox();
        winLossContinuerr.setPreferredSize(new java.awt.Dimension(20, 16));
        winLossContinuerr.setEnabled(false);
        filterCommonA.add(winLossContinuerr);
        // a filter condition
        drawLossContinu = new JCheckBox();
        drawLossContinu.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == e.SELECTED) {
                    left_drawLossContinu.setEnabled(true);
                    right_drawLossContinu.setEnabled(true);
                    drawLossContinuerr.setEnabled(true);
                } else if (e.getStateChange() == e.DESELECTED) {
                    left_drawLossContinu.setEnabled(false);
                    right_drawLossContinu.setEnabled(false);
                    drawLossContinuerr.setEnabled(false);
                }
            }
        });
        drawLossContinu.setPreferredSize(new java.awt.Dimension(20, 16));
        filterCommonA.add(drawLossContinu);
        left_drawLossContinu = new JComboBox(SystemConstants.DO_MATCHES);
        left_drawLossContinu.setPreferredSize(new java.awt.Dimension(42, 16));
        left_drawLossContinu.setEditable(false);
        left_drawLossContinu.setEnabled(false);
        filterCommonA.add(left_drawLossContinu);
        JLabel j12 = new JLabel(SystemConstants.DRAW_LOSS_CON);
        j12.setPreferredSize(new java.awt.Dimension(100, 16));
        filterCommonA.add(j12);
        right_drawLossContinu = new JComboBox(SystemConstants.DO_MATCHES);
        right_drawLossContinu.setPreferredSize(new java.awt.Dimension(42, 16));
        right_drawLossContinu.setEditable(false);
        right_drawLossContinu.setEnabled(false);
        filterCommonA.add(right_drawLossContinu);
        drawLossContinuerr = new JCheckBox();
        drawLossContinuerr.setPreferredSize(new java.awt.Dimension(20, 16));
        drawLossContinuerr.setEnabled(false);
        filterCommonA.add(drawLossContinuerr);
        // a filter condition
        conPartSum = new JCheckBox();
        conPartSum.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == e.SELECTED) {
                    left_conPartSum.setEnabled(true);
                    right_conPartSum.setEnabled(true);
                    conPartSumerr.setEnabled(true);
                } else if (e.getStateChange() == e.DESELECTED) {
                    left_conPartSum.setEnabled(false);
                    right_conPartSum.setEnabled(false);
                    conPartSumerr.setEnabled(false);
                }
            }
        });
        conPartSum.setPreferredSize(new java.awt.Dimension(20, 16));
        filterCommonA.add(conPartSum);
        left_conPartSum = new JComboBox();
        left_conPartSum.setPreferredSize(new java.awt.Dimension(42, 16));
        for (i = 0; i < SystemConstants.CON_SUM_I; i++) {
            left_conPartSum.addItem(i);
        }
        left_conPartSum.setEditable(false);
        left_conPartSum.setEnabled(false);
        filterCommonA.add(left_conPartSum);
        JLabel j13 = new JLabel(SystemConstants.CON_SUM);
        j13.setPreferredSize(new java.awt.Dimension(100, 16));
        filterCommonA.add(j13);
        right_conPartSum = new JComboBox();
        right_conPartSum.setPreferredSize(new java.awt.Dimension(42, 16));
        for (i = 0; i < SystemConstants.CON_SUM_I; i++) {
            right_conPartSum.addItem(i);
        }
        right_conPartSum.setEditable(false);
        right_conPartSum.setEnabled(false);
        filterCommonA.add(right_conPartSum);
        conPartSumerr = new JCheckBox();
        conPartSumerr.setPreferredSize(new java.awt.Dimension(20, 16));
        conPartSumerr.setEnabled(false);
        filterCommonA.add(conPartSumerr);
        // a filter condition
        conPartCrs = new JCheckBox();
        conPartCrs.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == e.SELECTED) {
                    left_conPartCrs.setEnabled(true);
                    right_conPartCrs.setEnabled(true);
                    conPartCrserr.setEnabled(true);
                } else if (e.getStateChange() == e.DESELECTED) {
                    left_conPartCrs.setEnabled(false);
                    right_conPartCrs.setEnabled(false);
                    conPartCrserr.setEnabled(false);
                }
            }
        });
        conPartCrs.setPreferredSize(new java.awt.Dimension(20, 16));
        filterCommonA.add(conPartCrs);
        left_conPartCrs = new JComboBox();
        left_conPartCrs.setPreferredSize(new java.awt.Dimension(42, 16));
        for (i = 0; i < SystemConstants.CON_CRS_I; i++) {
            left_conPartCrs.addItem(i);
        }
        left_conPartCrs.setEditable(false);
        left_conPartCrs.setEnabled(false);
        filterCommonA.add(left_conPartCrs);
        JLabel j14 = new JLabel(SystemConstants.CON_CRS);
        j14.setPreferredSize(new java.awt.Dimension(100, 16));
        filterCommonA.add(j14);
        right_conPartCrs = new JComboBox();
        right_conPartCrs.setPreferredSize(new java.awt.Dimension(42, 16));
        for (i = 0; i < SystemConstants.CON_CRS_I; i++) {
            right_conPartCrs.addItem(i);
        }
        right_conPartCrs.setEditable(false);
        right_conPartCrs.setEnabled(false);
        filterCommonA.add(right_conPartCrs);
        conPartCrserr = new JCheckBox();
        conPartCrserr.setPreferredSize(new java.awt.Dimension(20, 16));
        conPartCrserr.setEnabled(false);
        filterCommonA.add(conPartCrserr);
        // a filter condition
        crsSum = new JCheckBox();
        crsSum.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == e.SELECTED) {
                    left_crsSum.setEnabled(true);
                    right_crsSum.setEnabled(true);
                    crsSumerr.setEnabled(true);
                } else if (e.getStateChange() == e.DESELECTED) {
                    left_crsSum.setEnabled(false);
                    right_crsSum.setEnabled(false);
                    crsSumerr.setEnabled(false);
                }
            }
        });
        crsSum.setPreferredSize(new java.awt.Dimension(20, 16));
        filterCommonA.add(crsSum);
        left_crsSum = new JComboBox();
        left_crsSum.setPreferredSize(new java.awt.Dimension(42, 16));
        for (i = 0; i < SystemConstants.CRS_SUM_I; i++) {
            left_crsSum.addItem(i);
        }
        left_crsSum.setEditable(false);
        left_crsSum.setEnabled(false);
        filterCommonA.add(left_crsSum);
        JLabel j15 = new JLabel(SystemConstants.CRS_SUM);
        j15.setPreferredSize(new java.awt.Dimension(100, 16));
        filterCommonA.add(j15);
        right_crsSum = new JComboBox();
        right_crsSum.setPreferredSize(new java.awt.Dimension(42, 16));
        for (i = 0; i < SystemConstants.CRS_SUM_I; i++) {
            right_crsSum.addItem(i);
        }
        right_crsSum.setEditable(false);
        right_crsSum.setEnabled(false);
        filterCommonA.add(right_crsSum);
        crsSumerr = new JCheckBox();
        crsSumerr.setPreferredSize(new java.awt.Dimension(20, 16));
        crsSumerr.setEnabled(false);
        filterCommonA.add(crsSumerr);

        // filterScroll = new JScrollPane(filterCommonA);
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initFilterBetPane() {
        filterbetPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterbetPane.setPreferredSize(new java.awt.Dimension(300, 25));
        filterbetPane.setBorder(null);

        allowerr = new JCheckBox();
        allowerr.setPreferredSize(new java.awt.Dimension(30, 25));
        filterbetPane.add(allowerr);
        lefterr = new JComboBox(SystemConstants.ALLOW_ERROR_COMBO);
        lefterr.setPreferredSize(new java.awt.Dimension(50, 25));
        filterbetPane.add(lefterr);
        JLabel tmp = new JLabel(SystemConstants.ALLOW_ERROR_COUNT);
        tmp.setPreferredSize(new java.awt.Dimension(65, 25));
        filterbetPane.add(tmp);
        righterr = new JComboBox(SystemConstants.ALLOW_ERROR_COMBO);
        righterr.setPreferredSize(new java.awt.Dimension(50, 25));
        filterbetPane.add(righterr);
        filter = new JButton(SystemConstants.FILTER);
        filter.setPreferredSize(new java.awt.Dimension(60, 25));
        filter.setBackground(ColorConstants.BUTTON_COLOR);
        filter.addActionListener(new FilterHandler(CommandConstants.CMD_DO_FILTER));
        filterbetPane.add(filter);
    }

    /**
     * @param data
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initBetPane() {
        betPane = new JPanel(new FlowLayout());
        betPane.setBorder(new EtchedBorder());
        betPane.setPreferredSize(new java.awt.Dimension(328, 484));

        initTypePane();
        betPane.add(typePane);

        initDataPane();
        betPane.add(dataPane);

        initOperatePane();
        betPane.add(OperatePane);

        initDoBetPane();
        betPane.add(dobetPane);
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initDoBetPane() {
        dobetPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dobetPane.setPreferredSize(new java.awt.Dimension(314, 35));
        dobetPane.setBorder(null);

        newBascket = new JCheckBox(SystemConstants.NEW_BASCKET);
        newBascket.setFont(FontConstants.DEFAULT_FONT);
        // newBascket.setPreferredSize(new java.awt.Dimension(90, 25));
        dobetPane.add(newBascket);
        passway = new JButton(SystemConstants.PASSWAY);
        passway.setBackground(ColorConstants.BUTTON_COLOR);
        passway.setFont(FontConstants.DEFAULT_FONT);
        passway.addActionListener(new ProjectHandler(CommandConstants.CMD_CHOOSE_PASSWAY));
        int type = SystemCache.currentType;
        if (type < 2) {
            passway.setVisible(false);
        }
        // passway.setPreferredSize(new java.awt.Dimension(75, 25));
        dobetPane.add(passway);
        dopart = new JButton(SystemConstants.DATA_PART);
        // dataEvl.setPreferredSize(new java.awt.Dimension(75, 25));
        dopart.setBackground(ColorConstants.BUTTON_COLOR);
        dopart.setFont(FontConstants.DEFAULT_FONT);
        dopart.addActionListener(new ProjectHandler(CommandConstants.CMD_DO_PART));
        dobetPane.add(dopart);
        dobet = new JButton(SystemConstants.DO_BET);
        dobet.addActionListener(new ProjectHandler(CommandConstants.CMD_DO_BET));
        dobet.setBackground(ColorConstants.BUTTON_COLOR);
        dobet.setFont(FontConstants.DEFAULT_FONT);
        dobetPane.add(dobet);
    }

    /**
     * @方法说明: 是否到新号码篮
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isNewBasket() {
        return newBascket.isSelected();
    }

    /**
     * @方法说明: 分页菜单栏
     * @参数:
     * @return void
     * @throws
     */
    private void initOperatePane() {
        OperatePane = new JTabbedPane();
        OperatePane.setBorder(null);
        OperatePane.setPreferredSize(new java.awt.Dimension(314, 85));

        initFullSeatPane();
        OperatePane.add(fullSeat);
        OperatePane.setTitleAt(0, SystemConstants.FULL_SEAT);

        initRegionPane();
        OperatePane.add(region);
        OperatePane.setTitleAt(1, SystemConstants.REGION);

        initShrinkPane();
        OperatePane.add(shrink);
        OperatePane.setTitleAt(2, SystemConstants.SHRINK);

        initDantuoPane();
        OperatePane.add(dantuo);
        OperatePane.setTitleAt(3, SystemConstants.DANTUO);

        initWaterFloodPane();
        OperatePane.add(waterflood);
        OperatePane.setTitleAt(4, SystemConstants.WATER_FLOOD);

        initRondomPane();
        OperatePane.add(rondom);
        OperatePane.setTitleAt(5, SystemConstants.RONDOM);
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initRondomPane() {
        rondom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rondom.setBorder(new EtchedBorder());
        rondom.setPreferredSize(new java.awt.Dimension(314, 85));

        refOdds = new JCheckBox(SystemConstants.REF_ODDS);
        refOdds.setPreferredSize(new java.awt.Dimension(140, 13));
        rondomTimes = new JCheckBox(SystemConstants.RONDOM_TIMES);
        rondomTimes.setPreferredSize(new java.awt.Dimension(80, 13));
        rondomTimesText = new JTextField();
        rondomTimesText.setPreferredSize(new java.awt.Dimension(40, 13));
        rondomTimesText.setEditable(true);
        rondomTimesText.setEnabled(false);
        rondomGetText = new JTextField();
        rondomGetText.setPreferredSize(new java.awt.Dimension(185, 13));
        rondomGetText.setEditable(true);
        rondomGetText.setEnabled(false);
        rondomdanGetText = new JTextField();
        rondomdanGetText.setPreferredSize(new java.awt.Dimension(50, 13));
        rondomdanGetText.setEditable(true);
        rondomdanGetText.setEnabled(false);
        danGetText = new JTextField();
        danGetText.setPreferredSize(new java.awt.Dimension(110, 13));
        danGetText.setEditable(false);
        danGetText.setEnabled(false);
        rondomGroup = new ButtonGroup();
        rondomGet = new JRadioButton(SystemConstants.RONDOM_GET);
        rondomGet.setPreferredSize(new java.awt.Dimension(80, 13));
        danGet = new JRadioButton(SystemConstants.RONDOM_DAN_GET);
        danGet.setPreferredSize(new java.awt.Dimension(80, 13));
        danGetBtn = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        danGetBtn.setPreferredSize(new java.awt.Dimension(20, 13));

        rondomGroup.add(rondomGet);
        rondomGroup.add(danGet);
        rondom.add(refOdds);
        rondom.add(rondomTimes);
        rondom.add(rondomTimesText);
        rondom.add(rondomGet);
        rondom.add(rondomGetText);
        JLabel zhu1 = new JLabel(SystemConstants.BET);
        zhu1.setPreferredSize(new java.awt.Dimension(15, 13));
        rondom.add(zhu1);
        rondom.add(danGet);
        rondom.add(rondomdanGetText);
        JLabel zhu2 = new JLabel(SystemConstants.BET);
        zhu2.setPreferredSize(new java.awt.Dimension(15, 13));
        rondom.add(zhu2);
        rondom.add(danGetText);
        rondom.add(danGetBtn);
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initWaterFloodPane() {
        waterflood = new JPanel(new FlowLayout(FlowLayout.LEFT));
        waterflood.setBorder(new EtchedBorder());
        waterflood.setPreferredSize(new java.awt.Dimension(314, 85));

        wfGroup = new ButtonGroup();
        standarwf = new JRadioButton(SystemConstants.STANDAR_WATER_FLOOD);
        standarwf.setPreferredSize(new java.awt.Dimension(80, 13));
        coldwf = new JRadioButton(SystemConstants.COLD_WATER_FLOOD);
        coldwf.setPreferredSize(new java.awt.Dimension(80, 13));
        expandwf = new JRadioButton(SystemConstants.EXPAND_WATER_FLOOD);
        expandwf.setPreferredSize(new java.awt.Dimension(80, 13));
        leftwfComb = new JComboBox(SystemConstants.DO_MATCHES);
        leftwfComb.setPreferredSize(new java.awt.Dimension(60, 13));
        leftwfComb.setEditable(false);
        leftwfComb.setEnabled(false);
        rightwfComb = new JComboBox(SystemConstants.DO_MATCHES);
        rightwfComb.setPreferredSize(new java.awt.Dimension(60, 13));
        rightwfComb.setEditable(false);
        rightwfComb.setEnabled(false);
        expandwfComb = new JComboBox(SystemConstants.DO_MATCHES);
        expandwfComb.setPreferredSize(new java.awt.Dimension(50, 13));
        expandwfComb.setEditable(false);
        expandwfComb.setEnabled(false);
        multiOn = new JLabel(SystemConstants.MULTI_ON);
        multiOn.setPreferredSize(new java.awt.Dimension(80, 13));
        onexpand = new JLabel(SystemConstants.ON_ADD_MULTI);
        onexpand.setPreferredSize(new java.awt.Dimension(150, 13));
        coldwfText = new JTextField();
        coldwfText.setPreferredSize(new java.awt.Dimension(180, 13));
        coldwfText.setEditable(false);
        coldwfText.setEnabled(false);
        coldwfBtn = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        coldwfBtn.setPreferredSize(new java.awt.Dimension(20, 13));

        wfGroup.add(standarwf);
        wfGroup.add(coldwf);
        wfGroup.add(expandwf);
        waterflood.add(standarwf);
        waterflood.add(leftwfComb);
        waterflood.add(multiOn);
        waterflood.add(rightwfComb);
        waterflood.add(coldwf);
        waterflood.add(coldwfText);
        waterflood.add(coldwfBtn);
        waterflood.add(expandwf);
        waterflood.add(onexpand);
        waterflood.add(expandwfComb);
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initDantuoPane() {
        dantuo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dantuo.setBorder(new EtchedBorder());
        dantuo.setPreferredSize(new java.awt.Dimension(314, 85));

        danGroup = new ButtonGroup();
        commondan = new JRadioButton(SystemConstants.COMMON_DAN);
        commondan.setPreferredSize(new java.awt.Dimension(80, 13));
        groupdan = new JRadioButton(SystemConstants.GROUP_DAN);
        groupdan.setPreferredSize(new java.awt.Dimension(80, 13));
        groupaddan = new JRadioButton(SystemConstants.GROUP_AD_DAN);
        groupaddan.setPreferredSize(new java.awt.Dimension(80, 13));
        commondanText = new JTextField();
        commondanText.setPreferredSize(new java.awt.Dimension(180, 13));
        commondanText.setEditable(false);
        commondanText.setEnabled(false);
        groupdanText = new JTextField();
        groupdanText.setPreferredSize(new java.awt.Dimension(180, 13));
        groupdanText.setEditable(false);
        groupdanText.setEnabled(false);
        groupaddanText = new JTextField();
        groupaddanText.setPreferredSize(new java.awt.Dimension(180, 13));
        groupaddanText.setEditable(false);
        groupaddanText.setEnabled(false);
        commondanBtn = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        commondanBtn.setPreferredSize(new java.awt.Dimension(20, 13));
        groupdanBtn = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        groupdanBtn.setPreferredSize(new java.awt.Dimension(20, 13));
        groupaddanBtn = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        groupaddanBtn.setPreferredSize(new java.awt.Dimension(20, 13));

        danGroup.add(commondan);
        danGroup.add(groupdan);
        danGroup.add(groupaddan);
        dantuo.add(commondan);
        dantuo.add(commondanText);
        dantuo.add(commondanBtn);
        dantuo.add(groupdan);
        dantuo.add(groupdanText);
        dantuo.add(groupdanBtn);
        dantuo.add(groupaddan);
        dantuo.add(groupaddanText);
        dantuo.add(groupaddanBtn);
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initShrinkPane() {
        shrink = new JPanel(new FlowLayout(FlowLayout.LEFT));
        shrink.setBorder(new EtchedBorder());
        shrink.setPreferredSize(new java.awt.Dimension(314, 85));

        shrinkGroup = new ButtonGroup();
        standarshrink = new JRadioButton(SystemConstants.STANDAR_SHRINK);
        standarshrink.setPreferredSize(new java.awt.Dimension(80, 20));
        danshrink = new JRadioButton(SystemConstants.DAN_SHRINK);
        danshrink.setPreferredSize(new java.awt.Dimension(80, 20));
        shrinkGroup.add(standarshrink);
        shrinkGroup.add(danshrink);
        standarshrinkComb = new JComboBox(SystemConstants.SHRINK_COMB);
        standarshrinkComb.setPreferredSize(new java.awt.Dimension(85, 20));
        standarshrinkComb.setEnabled(false);
        standarshrinkComb.setEditable(false);
        danshrinkComb = new JComboBox(SystemConstants.SHRINK_COMB);
        danshrinkComb.setPreferredSize(new java.awt.Dimension(85, 20));
        danshrinkComb.setEnabled(false);
        danshrinkComb.setEditable(false);
        danshrinkText = new JTextField();
        danshrinkText.setPreferredSize(new java.awt.Dimension(95, 20));
        danshrinkText.setEnabled(false);
        danshrinkText.setEditable(false);
        danshrinkBtn = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        danshrinkBtn.setPreferredSize(new java.awt.Dimension(20, 20));

        shrink.add(standarshrink);
        shrink.add(standarshrinkComb);
        JLabel tmp = new JLabel(SystemConstants.EMPTY_STRING);
        tmp.setPreferredSize(new java.awt.Dimension(80, 20));
        shrink.add(tmp);
        shrink.add(danshrink);
        shrink.add(danshrinkComb);
        shrink.add(danshrinkText);
        shrink.add(danshrinkBtn);
    }

    /**
     * @方法说明: 初始化区间分页
     * @参数:
     * @return void
     * @throws
     */
    private void initRegionPane() {
        region = new JPanel(new FlowLayout(FlowLayout.LEFT));
        region.setBorder(new EtchedBorder());
        region.setPreferredSize(new java.awt.Dimension(314, 85));

        range = new JCheckBox(SystemConstants.RANGE);
        range.setPreferredSize(new java.awt.Dimension(80, 13));
        winexp = new JCheckBox(SystemConstants.WIN_EXPECT);
        winexp.setPreferredSize(new java.awt.Dimension(80, 13));
        dataexp = new JCheckBox(SystemConstants.DATA_EXPECT);
        dataexp.setPreferredSize(new java.awt.Dimension(80, 13));
        rangeText = new JTextField();
        rangeText.setPreferredSize(new java.awt.Dimension(180, 13));
        rangeText.setEditable(false);
        rangeText.setEnabled(false);
        winexpText = new JTextField();
        winexpText.setPreferredSize(new java.awt.Dimension(180, 13));
        winexpText.setEditable(false);
        winexpText.setEnabled(false);
        dataexpText = new JTextField();
        dataexpText.setPreferredSize(new java.awt.Dimension(180, 13));
        dataexpText.setEditable(false);
        dataexpText.setEditable(false);
        rangeBtn = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        rangeBtn.setPreferredSize(new java.awt.Dimension(20, 13));
        // rangeBtn.setBorder(null);
        winexpBtn = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        winexpBtn.setPreferredSize(new java.awt.Dimension(20, 13));
        // winexpBtn.setBorder(null);
        dataexpBtn = new JButton(new ImageIcon(FilePathConstants.EDIT_BUTTON_IMG));
        dataexpBtn.setPreferredSize(new java.awt.Dimension(20, 13));
        // dataexpBtn.setBorder(null);

        region.add(range);
        region.add(rangeText);
        region.add(rangeBtn);
        region.add(winexp);
        region.add(winexpText);
        region.add(winexpBtn);
        region.add(dataexp);
        region.add(dataexpText);
        region.add(dataexpBtn);
    }

    /**
     * @方法说明: 初始化全排分页
     * @参数:
     * @return void
     * @throws
     */
    private void initFullSeatPane() {
        fullSeat = new JPanel(new GridLayout(2, 2));
        fullSeat.setBorder(new EtchedBorder());
        fullSeat.setPreferredSize(new java.awt.Dimension(314, 85));

        fullSeatGroup = new ButtonGroup();
        JPanel rpn = new JPanel(new FlowLayout(FlowLayout.LEFT));
        // rpn.setBackground(SystemConstants.SYSTEM_DEFAULT_COLOR);
        op_single = new JRadioButton(SystemConstants.RADIO_SINGLE);
        op_single.setSelected(true);
        // op_single.setBackground(SystemConstants.SYSTEM_DEFAULT_COLOR);
        op_single.addActionListener(new FilterHandler(CommandConstants.CMD_SINGLE));
        fullSeatGroup.add(op_single);
        rpn.add(op_single);
        fullSeat.add(rpn);

        JPanel lpn = new JPanel(new FlowLayout(FlowLayout.LEFT));
        // lpn.setBackground(SystemConstants.SYSTEM_DEFAULT_COLOR);
        op_small_multi = new JRadioButton(SystemConstants.RADIO_SML_MULTI);
        op_small_multi.addActionListener(new FilterHandler(CommandConstants.CMD_SML_MULTI));
        fullSeatGroup.add(op_small_multi);
        betnumlimit = new JComboBox(SystemConstants.COMBO_BET_NUM);
        betnumlimit.setEditable(false);
        betnumlimit.setEnabled(false);
        // op_small_multi.setBackground(SystemConstants.SYSTEM_DEFAULT_COLOR);
        // betnumlimit.setBackground(SystemConstants.SYSTEM_DEFAULT_COLOR);
        lpn.add(op_small_multi);
        lpn.add(betnumlimit);
        fullSeat.add(lpn);

        JPanel mpn = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel jle = new JLabel(SystemConstants.MUTIPLE, JLabel.RIGHT);
        jle.setPreferredSize(new java.awt.Dimension(45, 20));
        mpn.add(jle);
        multi = new JTextField("1");
        multi.setHorizontalAlignment(JTextField.CENTER);
        multi.setPreferredSize(new java.awt.Dimension(80, 20));
        mpn.add(multi);
        fullSeat.add(mpn);
    }

    /**
     * @方法说明: 是否拆分为单式
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isSingleDispaly() {
        return (!op_small_multi.isSelected());
    }

    /**
     * @param data
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initDataPane() {
        /*
         * int row = 0; int col = 7; if (type < 2) { row = 14; } else { row = SystemFunctions.getRowSize(data); }
         */
        model = new DataTableModel();
        dataTable = new JTable(model);
        dataTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        // dataTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        dataTable.setBorder(null);
        dataTable.setRowHeight(TABLE_ROW_HEIGHT);
        dataTable.setShowHorizontalLines(false);
        dataTable.setShowVerticalLines(false);
        dataTable.setSelectionBackground(Color.magenta);
        dataTable.setSelectionForeground(Color.pink);
        dataTable.addMouseListener(new DataTableMouseAdapter() {
            @Override
            public void mouseSingleClicked(MouseEvent e) {
                int rowIndex = dataTable.rowAtPoint(e.getPoint());
                int colIndex = dataTable.columnAtPoint(e.getPoint());
                if (colIndex > 6) {
                    checkCellBox(rowIndex, colIndex);
                }
            }
        });

        // 单元格显示
        DataTableCellRenderer render = new DataTableCellRenderer();
        render.setHorizontalAlignment(JLabel.CENTER);
        render.setVerticalAlignment(JLabel.CENTER);
        dataTable.setDefaultRenderer(Object.class, render);

        // 列管理
        TableColumnModel colModel = dataTable.getColumnModel();
        TableColumn column = colModel.getColumn(3);
        column.setPreferredWidth(240);
        colModel.getColumn(7).setCellEditor(new DefaultCellEditor(new JCheckBox()));
        colModel.getColumn(8).setCellEditor(new DefaultCellEditor(new JCheckBox()));
        colModel.getColumn(9).setCellEditor(new DefaultCellEditor(new JCheckBox()));
        colModel.getColumn(10).setCellEditor(new DefaultCellEditor(new JCheckBox()));
        colModel.getColumn(11).setCellEditor(new DefaultCellEditor(new JCheckBox()));

        String[] columnName = { SystemConstants.GAME_ID, SystemConstants.ITEM_NAME, SystemConstants.PLAY_TIME,
                SystemConstants.VENUS, SystemConstants.BWIN, SystemConstants.WIN, SystemConstants.DRAW,
                SystemConstants.LOSS, SystemConstants.BETCHOOSE, SystemConstants.DAN_SET, SystemConstants.OPERATION };
        GroupableTableHeader tableHeader = new GroupableTableHeader();
        dataTable.setTableHeader(tableHeader);
        DefaultGroup group = new DefaultGroup();
        group.setLocation(0, 0, 2, 1);
        group.setHeaderValue(columnName[0]);
        tableHeader.addGroup(group);
        group = new DefaultGroup();
        group.setLocation(0, 1, 2, 1);
        group.setHeaderValue(columnName[1]);
        tableHeader.addGroup(group);
        group = new DefaultGroup();
        group.setLocation(0, 2, 2, 1);
        group.setHeaderValue(columnName[2]);
        tableHeader.addGroup(group);
        group = new DefaultGroup();
        group.setLocation(0, 3, 2, 1);
        group.setHeaderValue(columnName[3]);
        tableHeader.addGroup(group);
        group = new DefaultGroup();
        group.setLocation(0, 4, 1, 3);
        group.setHeaderValue(columnName[4]);
        tableHeader.addGroup(group);
        group = new DefaultGroup();
        group.setLocation(1, 4, 1, 1);
        group.setHeaderValue(columnName[5]);
        tableHeader.addGroup(group);
        group = new DefaultGroup();
        group.setLocation(1, 5, 1, 1);
        group.setHeaderValue(columnName[6]);
        tableHeader.addGroup(group);
        group = new DefaultGroup();
        group.setLocation(1, 6, 1, 1);
        group.setHeaderValue(columnName[7]);
        tableHeader.addGroup(group);
        group = new DefaultGroup();
        group.setLocation(0, 7, 1, 3);
        group.setHeaderValue(columnName[8]);
        tableHeader.addGroup(group);
        group = new DefaultGroup();
        group.setLocation(1, 7, 1, 1);
        group.setHeaderValue(columnName[5]);
        tableHeader.addGroup(group);
        group = new DefaultGroup();
        group.setLocation(1, 8, 1, 1);
        group.setHeaderValue(columnName[6]);
        tableHeader.addGroup(group);
        group = new DefaultGroup();
        group.setLocation(1, 9, 1, 1);
        group.setHeaderValue(columnName[7]);
        tableHeader.addGroup(group);
        group = new DefaultGroup();
        group.setLocation(0, 10, 2, 1);
        group.setHeaderValue(columnName[9]);
        tableHeader.addGroup(group);
        group = new DefaultGroup();
        group.setLocation(0, 11, 2, 1);
        group.setHeaderValue(columnName[10]);
        tableHeader.addGroup(group);

        // initial GUI data
        initGUIData();

        dataPane = new JScrollPane(dataTable);
        // dataPane.setLayout(new ScrollPaneLayout());
        dataPane.setBorder(new EtchedBorder());
        dataPane.setPreferredSize(new java.awt.Dimension(314, 294));
        dataPane.setVisible(true);
    }

    /**
     * @方法说明:
     * @参数: @param rowIndex
     * @参数: @param colIndex
     * @return void
     * @throws
     */
    public void checkCellBox(int rowIndex, int colIndex) {
        boolean flag = false;
        if (rowIndex == 0) {
            if (colIndex > 6) {
                if (colIndex == 11) {
                    checkAll();
                } else {
                    checkAllColumn(colIndex);
                }
                flag = true;
            }
            return;
        }
        if (colIndex > 6) {
            if (colIndex == 11) {
                checkAllRow(rowIndex);
            } else {
                checkCell(rowIndex, colIndex);
            }
            flag = true;
        }
        if (flag) {
            updateBetStatu();
        }
    }

    /**
     * @方法说明: 更新状态栏
     * @参数:
     * @return void
     * @throws
     */
    public void updateBetStatu() {
        betstatus.setText(SystemProcessor.getBetStatu());

    }

    /**
     * @方法说明: 更新倍数栏
     * @参数: String
     * @return void
     * @throws
     */
    public void updateMulti(String text) {
        multi.setText(text);
    }

    /**
     * @方法说明:
     * @参数: @param rowIndex
     * @参数: @param colIndex
     * @return void
     * @throws
     */
    public void checkCell(int rowIndex, int colIndex) {
        String id = (String) dataTable.getValueAt(rowIndex, 0);
        boolean noneCheck = isNoneCheck(rowIndex);
        if (noneCheck) {
            dataTable.setValueAt(new Boolean(false), rowIndex, 10);
        }
        if (!id.equals(SystemConstants.EMPTY_STRING)) {
            Boolean checked = (Boolean) dataTable.getValueAt(rowIndex, colIndex);
            if (checked.booleanValue() == false) {
                SystemProcessor.removeOneBet(id, SystemFunctions.convertToBet(colIndex));
            } else {
                SystemProcessor.addOneBet(id, SystemFunctions.convertToBet(colIndex));
            }
        }
    }

    /**
     * @方法说明:
     * @参数: @param rowIndex
     * @return void
     * @throws
     */
    public void checkAllRow(int rowIndex) {
        String id = (String) dataTable.getValueAt(rowIndex, 0);
        Boolean checked = (Boolean) dataTable.getValueAt(rowIndex, 11);
        if (checked.booleanValue() == false) {
            dataTable.setValueAt(checked, rowIndex, 10);
        }
        dataTable.setValueAt(checked, rowIndex, 7);
        dataTable.setValueAt(checked, rowIndex, 8);
        dataTable.setValueAt(checked, rowIndex, 9);
        if (!id.equals(SystemConstants.EMPTY_STRING)) {
            if (checked.booleanValue() == false) {
                SystemProcessor.removeOneBet(id, SystemFunctions.convertToBet(11));
            } else {
                SystemProcessor.addOneBet(id, SystemFunctions.convertToBet(11));
            }
        }
    }

    /**
     * @方法说明:
     * @参数: @param colIndex
     * @return void
     * @throws
     */
    public void checkAllColumn(int colIndex) {
        String id = SystemConstants.EMPTY_STRING;
        Boolean checked = (Boolean) dataTable.getValueAt(0, colIndex);
        int rows = dataTable.getRowCount();
        if (checked.booleanValue() == false) {
            for (int i = 1; i < rows; i++) {
                dataTable.setValueAt(checked, i, colIndex);
                boolean noneCheck = isNoneCheck(i);
                if (noneCheck) {
                    dataTable.setValueAt(checked, i, 10);
                }
                id = (String) dataTable.getValueAt(i, 0);
                if (!id.equals(SystemConstants.EMPTY_STRING)) {
                    SystemProcessor.removeOneBet(id, SystemFunctions.convertToBet(colIndex));
                }
            }
        } else {
            for (int i = 1; i < rows; i++) {
                if (colIndex != 10 || !isNoneCheck(i)) {
                    dataTable.setValueAt(checked, i, colIndex);
                }
                id = (String) dataTable.getValueAt(i, 0);
                if (!id.equals(SystemConstants.EMPTY_STRING)) {
                    SystemProcessor.addOneBet(id, SystemFunctions.convertToBet(colIndex));
                }
            }
        }
    }

    /**
     * @方法说明:
     * @参数: @param colIndex
     * @return void
     * @throws
     */
    public void checkAll() {
        int rows = dataTable.getRowCount();
        for (int i = 0; i < rows; i++) {
            this.checkAllRow(i);
        }
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    private boolean isNoneCheck(int rowIndex) {
        Boolean checked1 = (Boolean) dataTable.getValueAt(rowIndex, 7);
        Boolean checked2 = (Boolean) dataTable.getValueAt(rowIndex, 8);
        Boolean checked3 = (Boolean) dataTable.getValueAt(rowIndex, 9);
        boolean result = !(checked1.booleanValue() || checked2.booleanValue() || checked3.booleanValue());
        return result;
    }

    /**
     * @方法说明: 初始化数据
     * @参数:
     * @return void
     * @throws
     */
    @SuppressWarnings("unchecked")
    public void initGUIData() {
        model.clear();
        int type = SystemCache.currentType;
        TreeMap<String, List<BetMatch>> data = SystemProcessor.getMatchData();
        Object[] rowData = new Object[12];
        String vs = SystemConstants.EMPTY_STRING;
        Map<String, String> betmatches = SystemProcessor.getLottery(SystemCache.currentLotteryId).getMulMatches();
        if (type < 2) {
            Set keySet = data.keySet();
            Iterator iterator = keySet.iterator();
            String key = (String) iterator.next();
            rowData[0] = SystemConstants.EMPTY_STRING;
            rowData[1] = SystemConstants.EMPTY_STRING;
            rowData[2] = SystemConstants.EMPTY_STRING;
            rowData[3] = key;
            rowData[4] = SystemConstants.EMPTY_STRING;
            rowData[5] = SystemConstants.EMPTY_STRING;
            rowData[6] = SystemConstants.EMPTY_STRING;
            rowData[7] = new Boolean(false);
            rowData[8] = new Boolean(false);
            rowData[9] = new Boolean(false);
            rowData[10] = new Boolean(false);
            rowData[11] = new Boolean(false);
            model.addRow(rowData);
            List<BetMatch> matches = data.get(key);
            for (BetMatch match : matches) {
                rowData[0] = match.getMatchId();
                rowData[1] = match.getCompetition();
                rowData[2] = match.getTime();
                String hr = match.getHomeRank();
                String vr = match.getVistRank();
                if (null == hr) {
                    hr = "";
                }
                if (null == vr) {
                    vr = "";
                }
                vs = hr
                        + match.getHome()
                        + SystemConstants.BLANK_STRING
                        + SystemConstants.VS
                        + SystemConstants.BLANK_STRING
                        + match.getVisitor()
                        + vr;
                rowData[3] = vs;
                rowData[4] = match.getWinOdds() + SystemConstants.EMPTY_STRING;
                rowData[5] = match.getDrawOdds() + SystemConstants.EMPTY_STRING;
                rowData[6] = match.getLossOdds() + SystemConstants.EMPTY_STRING;
                rowData[7] = new Boolean(false);
                rowData[8] = new Boolean(false);
                rowData[9] = new Boolean(false);
                rowData[10] = new Boolean(false);
                rowData[11] = new Boolean(false);
                if (betmatches.containsKey(rowData[0])) {
                    String value = betmatches.get(rowData[0]);
                    int dan = 0;
                    for (int i = 0; i < value.length(); i++) {
                        if (value.charAt(i) == SystemConstants.THREE) {
                            rowData[7] = new Boolean(true);
                        } else if (value.charAt(i) == SystemConstants.ONE) {
                            rowData[8] = new Boolean(true);
                        } else if (value.charAt(i) == SystemConstants.ZERO) {
                            rowData[9] = new Boolean(true);
                        } else if (value.charAt(i) == SystemConstants.DAN_CHAR) {
                            rowData[10] = new Boolean(true);
                            dan++;
                        }
                    }
                    int vlen = value.length() - dan;
                    if (vlen > 2) {
                        rowData[11] = new Boolean(true);
                    }
                }
                model.addRow(rowData);
            }
        } else {
            Set keySet = data.keySet();
            Iterator iterator = keySet.iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                String[] mondat = key.split(SystemConstants.PART);
                String period = mondat[0]
                        + SystemConstants.YEAR
                        + mondat[1]
                        + SystemConstants.MONTH
                        + mondat[2]
                        + SystemConstants.DATE
                        + SystemConstants.TIME_10PERIOD;
                rowData[0] = SystemConstants.EMPTY_STRING;
                rowData[1] = SystemConstants.EMPTY_STRING;
                rowData[2] = SystemConstants.EMPTY_STRING;
                rowData[3] = period;
                rowData[4] = SystemConstants.EMPTY_STRING;
                rowData[5] = SystemConstants.EMPTY_STRING;
                rowData[6] = SystemConstants.EMPTY_STRING;
                rowData[7] = new Boolean(false);
                rowData[8] = new Boolean(false);
                rowData[9] = new Boolean(false);
                rowData[10] = new Boolean(false);
                rowData[11] = new Boolean(false);
                model.addRow(rowData);
                List<BetMatch> matches = data.get(key);
                for (BetMatch match : matches) {
                    rowData[0] = match.getMatchId();
                    rowData[1] = match.getCompetition();
                    rowData[2] = match.getTime();
                    String hr = match.getHomeRank();
                    String vr = match.getVistRank();
                    if (null == hr) {
                        hr = "";
                    }
                    if (null == vr) {
                        vr = "";
                    }
                    vs = hr
                            + match.getHome()
                            + SystemConstants.LEFT_PAREBTHESE
                            + match.getConcede()
                            + SystemConstants.RIGHT_PAREBTHESE
                            + SystemConstants.BLANK_STRING
                            + SystemConstants.VS
                            + SystemConstants.BLANK_STRING
                            + match.getVisitor()
                            + vr;
                    rowData[3] = vs;
                    rowData[4] = match.getWinOdds() + SystemConstants.EMPTY_STRING;
                    rowData[5] = match.getDrawOdds() + SystemConstants.EMPTY_STRING;
                    rowData[6] = match.getLossOdds() + SystemConstants.EMPTY_STRING;
                    rowData[7] = new Boolean(false);
                    rowData[8] = new Boolean(false);
                    rowData[9] = new Boolean(false);
                    rowData[10] = new Boolean(false);
                    rowData[11] = new Boolean(false);
                    // rowData[11] = new JButton(SystemConstants.ALL_BUTTON);
                    if (betmatches.containsKey(rowData[0])) {
                        String value = betmatches.get(rowData[0]);
                        int dan = 0;
                        for (int i = 0; i < value.length(); i++) {
                            if (value.charAt(i) == SystemConstants.THREE) {
                                rowData[7] = new Boolean(true);
                            } else if (value.charAt(i) == SystemConstants.ONE) {
                                rowData[8] = new Boolean(true);
                            } else if (value.charAt(i) == SystemConstants.ZERO) {
                                rowData[9] = new Boolean(true);
                            } else if (value.charAt(i) == SystemConstants.DAN_CHAR) {
                                rowData[10] = new Boolean(true);
                                dan++;
                            }
                        }
                        int vlen = value.length() - dan;
                        if (vlen > 2) {
                            rowData[11] = new Boolean(true);
                        }
                    }
                    model.addRow(rowData);
                }
            }
        }
    }

    /**
     * @param qihao
     * @param type
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initTypePane() {
        typePane = new JPanel(new FlowLayout());
        typePane.setBorder(new EtchedBorder());
        typePane.setPreferredSize(new java.awt.Dimension(314, 47));

        initBetTypeBox();
        typePane.add(betType);

        initPeriodBox();
        typePane.add(period);
    }

    /**
     * @param type
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initBetTypeBox() {
        betTylab = new JLabel(SystemConstants.LABEL_BETTYPE);
        betTylab.setForeground(Color.BLUE);
        typePane.add(betTylab);
        int type = SystemCache.currentType;
        betType = new JComboBox(SystemConstants.BET_TYPES);
        betType.setSelectedIndex(type);
        betType.addActionListener(new IOHandler());
        betType.addItemListener(new BetTypeListener());
    }

    /**
     * @param qihao
     * @方法说明: 初始化ComboBox
     * @参数:
     * @return void
     * @throws
     */
    private void initPeriodBox() {
        prdlab = new JLabel(SystemConstants.LABEL_PERIOD);
        prdlab.setForeground(Color.BLUE);
        typePane.add(prdlab);
        int type = SystemCache.currentType;
        String qihao = SystemCache.currentQihao;
        int maxQihaoCount = SysConfig.getInstance().getMaxQihaoCount();
        String[] qihaos = new String[maxQihaoCount];
        if (maxQihaoCount > 0) {
            qihaos = SystemFunctions.generateQihaos(type, qihao, maxQihaoCount);
        }
        period = new JComboBox(qihaos);
        period.addActionListener(new IOHandler());
        period.addItemListener(qihaoListener);
    }

    /**
     * @param qihao
     * @方法说明: 初始化ComboBox
     * @参数:
     * @return void
     * @throws
     */
    private void updatePeriodBox() {
        int type = SystemCache.currentType;
        String qihao = SystemCache.currentQihao;
        int maxQihaoCount = SysConfig.getInstance().getMaxQihaoCount();
        String[] qihaos = new String[maxQihaoCount];
        if (maxQihaoCount > 0) {
            qihaos = SystemFunctions.generateQihaos(type, qihao, maxQihaoCount);
        }
        period.removeAllItems();
        period.removeItemListener(qihaoListener);
        int len = qihaos.length;
        if (len > 0) {
            for (int i = 0; i < len; i++) {
                period.addItem(qihaos[i]);
                if (qihaos[i].indexOf(SystemConstants.CURRENT_PERIOD) >= 0) {
                    period.setSelectedIndex(i);
                }
            }
        }
        period.addItemListener(qihaoListener);
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initMenuPane() {
        menuPane = new JPanel(new FlowLayout(FlowLayout.LEFT + 25, 25, 5));
        menuPane.setBorder(new EtchedBorder());
        menuPane.setPreferredSize(new java.awt.Dimension(938, 52));
        // menuPane.setBackground(SystemConstants.QICKMENU_BG);

        ImageIcon pro_img = new ImageIcon(FilePathConstants.EXPORT_IMG_FILE);
        proj = new JButton(pro_img);
        proj.setBorder(null);
        proj.setText(SystemConstants.QUICKMENU_OPENPRO);
        menuPane.add(proj);

        ImageIcon save_img = new ImageIcon(FilePathConstants.SAVE_IMG_FILE);
        save = new JButton(save_img);
        save.setBorder(null);
        save.setText(SystemConstants.QUICKMENU_SAVEPRO);
        menuPane.add(save);

        JSeparator s1 = new JSeparator();
        s1.setPreferredSize(new java.awt.Dimension(2, 35));
        s1.setOrientation(SwingConstants.VERTICAL);
        menuPane.add(s1);

        ImageIcon i1_img = new ImageIcon(FilePathConstants.DATAUPDATE_IMG_FILE);
        abtn = new JButton(i1_img);
        abtn.setBorder(null);
        abtn.setText(SystemConstants.QUICKMENU_DATAUPDATE);
        menuPane.add(abtn);

        JSeparator s2 = new JSeparator();
        s2.setPreferredSize(new java.awt.Dimension(2, 35));
        s2.setOrientation(SwingConstants.VERTICAL);
        menuPane.add(s2);

        ImageIcon i2_img = new ImageIcon(FilePathConstants.DATATABLE_IMG_FILE);
        bbtn = new JButton(i2_img);
        bbtn.setBorder(null);
        bbtn.setText(SystemConstants.QUICKMENU_DATATABLE);
        menuPane.add(bbtn);

        ImageIcon i3_img = new ImageIcon(FilePathConstants.BETSCAT_IMG_FILE);
        cbtn = new JButton(i3_img);
        cbtn.setBorder(null);
        cbtn.setText(SystemConstants.QUICKMENU_BETSCAT);
        menuPane.add(cbtn);

        ImageIcon i4_img = new ImageIcon(FilePathConstants.WINQUERY_IMG_FILE);
        dbtn = new JButton(i4_img);
        dbtn.setBorder(null);
        dbtn.setText(SystemConstants.QUICKMENU_WINQUERY);
        menuPane.add(dbtn);

        JSeparator s3 = new JSeparator();
        s3.setPreferredSize(new java.awt.Dimension(2, 35));
        s3.setOrientation(SwingConstants.VERTICAL);
        menuPane.add(s3);

        ImageIcon i5_img = new ImageIcon(FilePathConstants.BUY_IMG_FILE);
        ebtn = new JButton(i5_img);
        ebtn.setBorder(null);
        ebtn.setText(SystemConstants.QUICKMENU_BUY);
        menuPane.add(ebtn);

        ImageIcon i6_img = new ImageIcon(FilePathConstants.STUDY_IMG_FILE);
        fbtn = new JButton(i6_img);
        fbtn.setBorder(null);
        fbtn.setText(SystemConstants.QUICKMENU_STUDY);
        menuPane.add(fbtn);
    }

    private void initMenuBar() {
        mbar = new JMenuBar();
        mbar.setBackground(ColorConstants.MENUBAR_BG);

        account = new JMenu(SystemConstants.MENU_ACCOUNT);
        register = new JMenuItem(SystemConstants.MENU_REGISTER);
        register.addActionListener(new UserHandler(CommandConstants.CMD_REGISTER));
        login = new JMenuItem(SystemConstants.MENU_LOGIN);
        login.addActionListener(new UserHandler(CommandConstants.CMD_LOGIN));
        accountInfo = new JMenu(SystemConstants.MENU_ACCOUNT_INFO);
        accountInfo.setVisible(false);
        userInfo = new JMenuItem(SystemConstants.MENU_ACCOUNT_INFO_CK);
        userInfo.addActionListener(new UserHandler(CommandConstants.CMD_USERINFO_CK));
        betInfo = new JMenuItem(SystemConstants.MENU_BET_INFO_CK);
        betInfo.addActionListener(new UserHandler(CommandConstants.CMD_BETINFO_CK));
        tradeInfo = new JMenuItem(SystemConstants.MENU_TRADE_INFO_CK);
        capitalInfo = new JMenuItem(SystemConstants.MENU_CAPITAL_INFO_CK);
        changeUserInfo = new JMenuItem(SystemConstants.MENU_ACCOUNT_INFO_XG);
        accountInfo.add(userInfo);
        accountInfo.add(betInfo);
        accountInfo.add(tradeInfo);
        accountInfo.add(capitalInfo);
        accountInfo.addSeparator();
        accountInfo.add(changeUserInfo);
        safeSetting = new JMenu(SystemConstants.MENU_SAFE_SETTING);
        safeSetting.setEnabled(false);
        changePassword = new JMenuItem(SystemConstants.MENU_CHANGE_PASSWORD);
        changePassword.addActionListener(new UserHandler(CommandConstants.CMD_CHANGEPASSWORD));
        changePassword.setEnabled(false);
        safeSetting.add(changePassword);
        payPasswordSetting = new JMenuItem(SystemConstants.MENU_PPD_SETTING);
        payPasswordSetting.addActionListener(new UserHandler(CommandConstants.CMD_PPD_SETTING));
        payPasswordSetting.setEnabled(false);
        safeSetting.add(payPasswordSetting);
        cryptoguardSetting = new JMenuItem(SystemConstants.MENU_CPD_SETTING);
        cryptoguardSetting.addActionListener(new UserHandler(CommandConstants.CMD_CPD_SETTING));
        cryptoguardSetting.setEnabled(false);
        safeSetting.add(cryptoguardSetting);
        recharge = new JMenuItem(SystemConstants.MENU_RECHARGE);
        recharge.addActionListener(new UserHandler(CommandConstants.CMD_RECHARGE));
        recharge.setEnabled(false);
        toVIP = new JMenuItem(SystemConstants.MENU_TOVIP);
        toVIP.addActionListener(new UserHandler(CommandConstants.CMD_TOVIP));
        toVIP.setEnabled(false);
        logout = new JMenuItem(SystemConstants.MENU_LOGOUT);
        logout.addActionListener(new UserHandler(CommandConstants.CMD_LOGOUT));
        logout.setEnabled(false);
        account.add(register);
        account.addSeparator();
        account.add(login);
        account.add(accountInfo);
        account.add(safeSetting);
        account.addSeparator();
        account.add(recharge);
        account.add(toVIP);
        account.addSeparator();
        account.add(logout);
        mbar.add(account);

        file = new JMenu(SystemConstants.MENU_FILE);

        upload = new JMenuItem(SystemConstants.MENU_UPLOAD);
        upload.addActionListener(new ProjectHandler(CommandConstants.CMD_UPLOAD));
        file.add(upload);

        file.addSeparator();

        new_item = new JMenuItem(SystemConstants.MENU_NEWPRO);
        new_item.addActionListener(new ProjectHandler(CommandConstants.CMD_NEWPRO));
        file.add(new_item);

        file.addSeparator();

        openpro_item = new JMenuItem(SystemConstants.MENU_OPENPRO);
        openpro_item.addActionListener(new ProjectHandler(CommandConstants.CMD_OPENPRO));
        file.add(openpro_item);

        savepro_item = new JMenuItem(SystemConstants.MENU_SAVEPRO);
        savepro_item.addActionListener(new ProjectHandler(CommandConstants.CMD_SAVEPRO));
        file.add(savepro_item);

        file.addSeparator();

        openret_item = new JMenuItem(SystemConstants.MENU_OPENRET);
        openret_item.addActionListener(new ProjectHandler(CommandConstants.CMD_OPENRET));
        file.add(openret_item);

        saveret_item = new JMenuItem(SystemConstants.MENU_SAVERET);
        saveret_item.addActionListener(new ProjectHandler(CommandConstants.CMD_SAVERET));
        file.add(saveret_item);

        file.addSeparator();

        exit_item = new JMenuItem(SystemConstants.MENU_EXIT);
        exit_item.addActionListener(new SystemConfHandler(CommandConstants.CMD_EXIT));
        file.add(exit_item);
        mbar.add(file);

        data = new JMenu(SystemConstants.MENU_PROFDATA);
        study_item = new JMenuItem(SystemConstants.MENU_STUDY);
        study_item.addActionListener(new AnalysisHandler(CommandConstants.CMD_STUDY));
        data.add(study_item);
        data.addSeparator();
        compare_item = new JMenuItem(SystemConstants.MENU_COMPARE);
        compare_item.addActionListener(new AnalysisHandler(CommandConstants.CMD_COMPARE));
        data.add(compare_item);
        data.addSeparator();
        mediaforcast_item = new JMenuItem(SystemConstants.MENU_MEDIAFORCAST);
        mediaforcast_item.addActionListener(new AnalysisHandler(CommandConstants.CMD_MEDIAFORCAST));
        data.add(mediaforcast_item);
        survey_item = new JMenuItem(SystemConstants.MENU_SURVEY);
        survey_item.addActionListener(new AnalysisHandler(CommandConstants.CMD_SURVEY));
        data.add(survey_item);
        data.addSeparator();
        mulsta_item = new JMenuItem(SystemConstants.MENU_MULSTA);
        mulsta_item.addActionListener(new AnalysisHandler(CommandConstants.CMD_MULSTA));
        data.add(mulsta_item);
        mulana_item = new JMenuItem(SystemConstants.MENU_MULANA);
        mulana_item.addActionListener(new AnalysisHandler(CommandConstants.CMD_MULANA));
        data.add(mulana_item);
        data.addSeparator();
        tzfesta_item = new JMenuItem(SystemConstants.MENU_2048STA);
        tzfesta_item.addActionListener(new AnalysisHandler(CommandConstants.CMD_2048STA));
        data.add(tzfesta_item);
        tzfeana_item = new JMenuItem(SystemConstants.MENU_2048ANA);
        tzfeana_item.addActionListener(new AnalysisHandler(CommandConstants.CMD_2048ANA));
        data.add(tzfeana_item);
        data.addSeparator();
        otesta_item = new JMenuItem(SystemConstants.MENU_128STA);
        otesta_item.addActionListener(new AnalysisHandler(CommandConstants.CMD_128STA));
        data.add(otesta_item);
        oteana_item = new JMenuItem(SystemConstants.MENU_128ANA);
        oteana_item.addActionListener(new AnalysisHandler(CommandConstants.CMD_128ANA));
        data.add(oteana_item);
        mbar.add(data);

        betret = new JMenu(SystemConstants.MENU_BETRET);
        winquery_item = new JMenuItem(SystemConstants.MENU_WINQUERY);
        winquery_item.addActionListener(new QueryHandler(CommandConstants.CMD_WINQUERY));
        betret.add(winquery_item);

        betscat_item = new JMenu(SystemConstants.MENU_BETSCAT);
        // betscat_item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,KeyEvent.CTRL_MASK,false));
        // betscat_item.addActionListener(new ActionHandler());
        betretsta_item = new JMenuItem(SystemConstants.MENU_BETRETSTA);
        betretsta_item.addActionListener(new FilterHandler(CommandConstants.CMD_BETRETSTA));
        betscat_item.add(betretsta_item);
        betscatsta_item = new JMenuItem(SystemConstants.MENU_BETSCATSTA);
        betscatsta_item.addActionListener(new FilterHandler(CommandConstants.CMD_BETSCATSTA));
        betscat_item.add(betscatsta_item);
        betret.add(betscat_item);
        betret.addSeparator();
        batprofilter_item = new JMenuItem(SystemConstants.MENU_BATPROFILTER);
        batprofilter_item.addActionListener(new FilterHandler(CommandConstants.CMD_BATPROFILTER));
        betret.add(batprofilter_item);
        batmedfilter_item = new JMenuItem(SystemConstants.MENU_BATMEDFILTER);
        batmedfilter_item.addActionListener(new FilterHandler(CommandConstants.CMD_BATMEDFILTER));
        betret.add(batmedfilter_item);
        mergebet_item = new JMenuItem(SystemConstants.MENU_MERGEBET);
        mergebet_item.addActionListener(new FilterHandler(CommandConstants.CMD_MERGEBET));
        betret.add(mergebet_item);
        betret.addSeparator();
        betundo_item = new JMenuItem(SystemConstants.MENU_BETUNDO);
        betundo_item.addActionListener(new FilterHandler(CommandConstants.CMD_BETUNDO));
        betundo_item.setEnabled(false);
        betret.add(betundo_item);
        betredo_item = new JMenuItem(SystemConstants.MENU_BETREDO);
        betredo_item.addActionListener(new FilterHandler(CommandConstants.CMD_BETREDO));
        betredo_item.setEnabled(false);
        betret.add(betredo_item);
        seturdo_item = new JMenuItem(SystemConstants.MENU_SETURDO);
        seturdo_item.addActionListener(new FilterHandler(CommandConstants.CMD_SETURDO));
        betret.add(seturdo_item);
        betret.addSeparator();
        filrecycle_item = new JMenuItem(SystemConstants.MENU_FILRECYCLE);
        filrecycle_item.addActionListener(new FilterHandler(CommandConstants.CMD_FILRECYCLE));
        filrecycle_item.setEnabled(false);
        betret.add(filrecycle_item);
        mbar.add(betret);

        table = new JMenu(SystemConstants.MENU_TABLE);
        datastatable_item = new JMenuItem(SystemConstants.MENU_DATASTATABLE);
        datastatable_item.addActionListener(new AnalysisHandler(CommandConstants.CMD_DATASTATABLE));
        table.add(datastatable_item);
        table.addSeparator();
        hismedsta_item = new JMenuItem(SystemConstants.MENU_HISMEDSTA);
        hismedsta_item.addActionListener(new AnalysisHandler(CommandConstants.CMD_HISMEDSTA));
        table.add(hismedsta_item);
        hisoddsta_item = new JMenuItem(SystemConstants.MENU_HISODDSTA);
        hisoddsta_item.addActionListener(new AnalysisHandler(CommandConstants.CMD_HISODDSTA));
        table.add(hisoddsta_item);
        table.addSeparator();
        retprot_item = new JMenuItem(SystemConstants.MENU_RETPROT);
        retprot_item.addActionListener(new AnalysisHandler(CommandConstants.CMD_RETPROT));
        table.add(retprot_item);
        oddprot_item = new JMenuItem(SystemConstants.MENU_ODDPROT);
        oddprot_item.addActionListener(new AnalysisHandler(CommandConstants.CMD_ODDPROT));
        table.add(oddprot_item);
        medprot_item = new JMenuItem(SystemConstants.MENU_MEDPROT);
        medprot_item.addActionListener(new AnalysisHandler(CommandConstants.CMD_MEDPROT));
        table.add(medprot_item);
        mbar.add(table);

        system = new JMenu(SystemConstants.MENU_SYSTEM);
        dataupdate_item = new JMenuItem(SystemConstants.MENU_DATAUPDATE);
        dataupdate_item.addActionListener(new SystemConfHandler(CommandConstants.CMD_DATAUPDATE));
        system.add(dataupdate_item);
        datainit_item = new JMenuItem(SystemConstants.MENU_DATAINIT);
        datainit_item.addActionListener(new SystemConfHandler(CommandConstants.CMD_DATAINIT));
        system.add(datainit_item);
        filman_item = new JMenuItem(SystemConstants.MENU_FILMAN);
        filman_item.addActionListener(new SystemConfHandler(CommandConstants.CMD_FILMAN));
        system.add(filman_item);
        softupdate_item = new JMenuItem(SystemConstants.MENU_SOFTUPDATE);
        softupdate_item.addActionListener(new SystemConfHandler(CommandConstants.CMD_SOFTUPDATE));
        system.add(softupdate_item);
        system.addSeparator();
        betedit_item = new JMenuItem(SystemConstants.MENU_BETEDIT);
        betedit_item.addActionListener(new SystemConfHandler(CommandConstants.CMD_BETEDIT));
        system.add(betedit_item);
        notepad_item = new JMenuItem(SystemConstants.MENU_NOTEPAD);
        notepad_item.addActionListener(new SystemConfHandler(CommandConstants.CMD_NOTEPAD));
        system.add(notepad_item);
        calc_item = new JMenuItem(SystemConstants.MENU_CALC);
        calc_item.addActionListener(new SystemConfHandler(CommandConstants.CMD_CALC));
        system.add(calc_item);
        system.addSeparator();
        systemset_item = new JMenuItem(SystemConstants.MENU_SYSTEMSET);
        systemset_item.addActionListener(new SystemConfHandler(CommandConstants.CMD_SYSTEMSET));
        system.add(systemset_item);
        mbar.add(system);

        help = new JMenu(SystemConstants.MENU_HELP);
        usehelp_item = new JMenuItem(SystemConstants.MENU_USEHELP);
        usehelp_item.addActionListener(new HelpHandler(CommandConstants.CMD_USEHELP));
        help.add(usehelp_item);
        olservice_item = new JMenuItem(SystemConstants.MENU_OLSERVICE);
        olservice_item.addActionListener(new HelpHandler(CommandConstants.CMD_OLSERVICE));
        help.add(olservice_item);
        nowolservice_item = new JMenuItem(SystemConstants.MENU_NOWOLSERVICE);
        nowolservice_item.addActionListener(new HelpHandler(CommandConstants.CMD_NOWOLSERVICE));
        help.add(nowolservice_item);
        help.addSeparator();
        homepage_item = new JMenuItem(SystemConstants.MENU_HOMEPAGE);
        homepage_item.addActionListener(new HelpHandler(CommandConstants.CMD_HOMEPAGE));
        help.add(homepage_item);
        forum_item = new JMenuItem(SystemConstants.MENU_FORUM);
        forum_item.addActionListener(new HelpHandler(CommandConstants.CMD_FORUM));
        help.add(forum_item);
        help.addSeparator();
        about_item = new JMenuItem(SystemConstants.MENU_ABOUT);
        about_item.addActionListener(new HelpHandler(CommandConstants.CMD_ABOUT));
        help.add(about_item);
        mbar.add(help);
        JMenu jem = new JMenu();
        jem.setEnabled(false);
        jem.setPreferredSize(new java.awt.Dimension(375, mbar.getHeight()));
        mbar.add(jem);
        user = new JMenu();
        user.setText("欢迎您，");
        user.setEnabled(false);
        mbar.add(user);
    }

    /**
     * @方法说明: set combobox betnumlimit to enable
     * @参数:
     * @return void
     * @throws
     */
    public void setBNLEnable(boolean flag) {
        betnumlimit.setEnabled(flag);
    }

    /**
     * @方法说明:
     * @参数: @param free
     * @return void
     * @throws
     */
    public void setIdleMemory() {
        String idle = SystemConstants.CACHESTATU + SystemConstants.BLANK_STRING + SystemCache.free_memory;
        cachestatu.setText(idle);
    }

    /**
     * @方法说明: 获取按钮位置
     * @参数: @return
     * @return Point
     * @throws
     */
    public Point getPasswayButtonLocation() {
        return passway.getLocation();
    }

    /**
     * @方法说明: 获取按钮位置
     * @参数: @return
     * @return Point
     * @throws
     */
    public void setFrameEnable(boolean flag) {
        this.setEnabled(flag);
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    private void initTypeBox() {
        int type = SystemCache.currentType;
        betType.setSelectedIndex(type);
        period.setSelectedIndex(0);
    }

    /**
     * @方法说明: 初始化工程GUI
     * @参数:
     * @return void
     * @throws
     */
    public void initProject() {
        initTypeBox();
        initGUIData();
        updateBetStatu();
        updateMulti("1");
    }

    /**
     * @方法说明:单式号码篮显示
     * @参数:
     * @return void
     * @throws
     */
    @SuppressWarnings("unchecked")
    public void displaySingleBet(int displayPage) {
        pageType = 0;
        displayBasketNames();
        BetBasket basket = SystemCache.basketCache.getBasketById(SystemCache.currentBasketId);
        basketModel.clear();
        String[] rowData = new String[2];
        Map<String, List<String>> singleBets = basket.getSingleBets();
        page = new Page(singleBets);
        page.setCurrentPage(displayPage);
        Map<String, List<String>> display = page.doPaging();
        Iterator it = display.keySet().iterator();
        while (it.hasNext()) {
            String matchIds = (String) it.next();
            List<String> bets = display.get(matchIds);
            rowData[0] = matchIds;
            for (String bet : bets) {
                rowData[1] = bet;
                basketModel.addRow(rowData);
            }
        }
        int singleBetCount = basket.getSingleBetCount();
        updateBetNumber(singleBetCount);
        updatePageInfo();
    }

    /**
     * @方法说明:复式号码篮显示
     * @参数:
     * @return void
     * @throws
     */
    @SuppressWarnings("unchecked")
    public void displayMultiBet(int displayPage) {
        pageType = 1;
        displayBasketNames();
        BetBasket basket = SystemCache.basketCache.getBasketById(SystemCache.currentBasketId);
        basketModel.clear();
        String[] rowData = new String[2];
        Map<String, List<String>> multiBets = basket.getMultiBets();
        page = new Page(multiBets);
        page.setCurrentPage(displayPage);
        Map<String, List<String>> display = page.doPaging();
        Iterator it = display.keySet().iterator();
        while (it.hasNext()) {
            String matchIds = (String) it.next();
            List<String> bets = display.get(matchIds);
            rowData[0] = matchIds;
            for (String bet : bets) {
                rowData[1] = bet;
                basketModel.addRow(rowData);
            }
        }
        int multiBetCount = basket.getMultiBetCount();
        updateBetNumber(multiBetCount);
        updatePageInfo();
    }

    /**
     * @方法说明:
     * @参数: @param betCount
     * @return void
     * @throws
     */
    private void updateBetNumber(int betCount) {
        String text = SystemConstants.BETCODE + betCount + SystemConstants.BLANK_STRING + SystemConstants.BET;
        betNum.setText(text);
    }

    /**
     * @方法说明: 更新页码信息
     * @参数:
     * @return void
     * @throws
     */
    private void updatePageInfo() {
        gotoPageText.setText(page.getCurrentPage() + SystemConstants.EMPTY_STRING);
        pageCount.setText(page.getPageCount() + SystemConstants.EMPTY_STRING);
    }

    /**
     * @方法说明:显示号码篮列表
     * @参数:
     * @return void
     * @throws
     */
    @SuppressWarnings("unchecked")
    private void displayBasketNames() {
        listModel.clear();
        HashMap<String, BetBasket> baskets = SystemCache.basketCache.getData();
        // listModel.addElement(baskets);
        Iterator it = baskets.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            BetBasket bst = baskets.get(key);
            int betCount = 0;
            if (isSingleDispaly()) {
                betCount = bst.getSingleBetCount();
            } else {
                betCount = bst.getMultiBetCount();
            }
            String name = SystemConstants.LEFT_ANGLE
                    + bst.getName()
                    + SystemConstants.RIGHT_ANGLE
                    + SystemConstants.BLANK_STRING
                    + SystemConstants.LEFT_BRACKET
                    + SystemConstants.BET_COUNT
                    + betCount
                    + SystemConstants.RIGHT_BRACKET;
            JCheckBox basket = new JCheckBox(name);
            if (key.equals(SystemCache.currentBasketId)) {
                basket.setSelected(true);
            }
            basketList.setCellRenderer(new BasketListCellRenderer(basket));
            listModel.addElement(basket);
        }
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    public void firstPage() {
        if (pageType == 0) {
            displaySingleBet(1);
        } else {
            displayMultiBet(1);
        }
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    public void previousPage() {
        int previousPage = page.getCurrentPage() - 1;
        if (pageType == 0) {
            displaySingleBet(previousPage);
        } else {
            displayMultiBet(previousPage);
        }
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    public void nextPage() {
        int nextPage = page.getCurrentPage() + 1;
        if (pageType == 0) {
            displaySingleBet(nextPage);
        } else {
            displayMultiBet(nextPage);
        }
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    public void lastPage() {
        int lastPage = page.getPageCount();
        if (pageType == 0) {
            displaySingleBet(lastPage);
        } else {
            displayMultiBet(lastPage);
        }
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    public void gotoPage() {
        String pg = gotoPageText.getText();
        if (pg.matches(RegexConstants.INTEGER_REGEX)) {
            int gotoPage = Integer.parseInt(pg);
            if (pageType == 0) {
                displaySingleBet(gotoPage);
            } else {
                displayMultiBet(gotoPage);
            }
        } else {
            JOptionPane.showMessageDialog(null, MessageConstants.PAGE_ERROR);
        }
    }

    /**
     * @方法说明: 投注类型切换
     * @参数: @param item
     * @return void
     * @throws
     */
    public void betTypeChange() {
        int isOK = JOptionPane.showConfirmDialog(null, MessageConstants.TYPE_CHANGE_INFO, MessageConstants.INFOMATION,
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (isOK == 0) {
            SystemProcessor.saveAll();
        }
        SystemCache.init();
        String thisType = betType.getSelectedItem().toString();
        if (thisType.equals(SystemConstants.BET_TYPES[0])) {
            UserConfig.getInstance().setTzlx(0);
            UserConfig.getInstance().setQihao(SysConfig.getInstance().getSfcQihao());
            passway.setVisible(false);
        } else if (thisType.equals(SystemConstants.BET_TYPES[1])) {
            UserConfig.getInstance().setTzlx(1);
            UserConfig.getInstance().setQihao(SysConfig.getInstance().getSfcQihao());
            passway.setVisible(false);
        } else if (thisType.equals(SystemConstants.BET_TYPES[2])) {
            UserConfig.getInstance().setTzlx(2);
            UserConfig.getInstance().setQihao(SysConfig.getInstance().getBjdcQihao());
            passway.setVisible(true);
        } else if (thisType.equals(SystemConstants.BET_TYPES[3])) {
            UserConfig.getInstance().setTzlx(3);
            UserConfig.getInstance().setQihao(SysConfig.getInstance().getJczqPeriod());
            passway.setVisible(true);
        }
        SystemProcessor.initMatchData();
        SystemProcessor.initLottery();
        updatePeriodBox();
        initGUIData();
        updateBetStatu();
    }

    /**
     * @方法说明: 期号切换
     * @参数:
     * @return void
     * @throws
     */
    public void qihaoChange(Object deselected) {
        String thisQihao = period.getSelectedItem().toString();
        thisQihao = thisQihao.replace(SystemConstants.BLANK_STRING + SystemConstants.CURRENT_PERIOD,
                SystemConstants.EMPTY_STRING);
        UserConfig.getInstance().setQihao(thisQihao);
        Map<String, List<BetMatch>> data = SystemProcessor.getUpdateMatchData();
        if (data == null) {
            period.removeItemListener(qihaoListener);
            period.setSelectedItem(deselected);
            period.addItemListener(qihaoListener);
            thisQihao = deselected.toString();
            thisQihao = thisQihao.replace(SystemConstants.BLANK_STRING + SystemConstants.CURRENT_PERIOD,
                    SystemConstants.EMPTY_STRING);
            UserConfig.getInstance().setQihao(thisQihao);
            return;
        }
        int isOK = JOptionPane.showConfirmDialog(null, MessageConstants.QIHAO_CHANGE_INFO, MessageConstants.INFOMATION,
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (isOK == 0) {
            SystemProcessor.saveAll();
        }
        SystemCache.init();
        SystemProcessor.updateMatchData(data);
        SystemProcessor.initLottery();
        initGUIData();
        updateBetStatu();
    }

    /**
     * @return boolean
     */
    public boolean isWinFilter() {
        return wins.isSelected();
    }

    public float getLeastWin() {
        String lst = left_wins.getSelectedItem().toString().trim();
        return Float.parseFloat(lst);
    }

    public float getMostWin() {
        String mst = right_wins.getSelectedItem().toString().trim();
        return Float.parseFloat(mst);
    }

    public boolean isWinFt() {
        return winserr.isSelected();
    }

    /**
     * @return boolean
     */
    public boolean isDrawFilter() {
        return draws.isSelected();
    }

    public float getLeastDraw() {
        String lst = left_draws.getSelectedItem().toString().trim();
        return Float.parseFloat(lst);
    }

    public float getMostDraw() {
        String mst = right_draws.getSelectedItem().toString().trim();
        return Float.parseFloat(mst);
    }

    public boolean isDrawFt() {
        return drawserr.isSelected();
    }

    /**
     * @return boolean
     */
    public boolean isLossFilter() {
        return losses.isSelected();
    }

    public float getLeastLoss() {
        String lst = left_losses.getSelectedItem().toString().trim();
        return Float.parseFloat(lst);
    }

    public float getMostLoss() {
        String mst = right_losses.getSelectedItem().toString().trim();
        return Float.parseFloat(mst);
    }

    public boolean isLossFt() {
        return losseserr.isSelected();
    }

    /**
     * @return boolean
     */
    public boolean isScoreFilter() {
        return scores.isSelected();
    }

    public float getLeastScore() {
        String lst = left_scores.getSelectedItem().toString().trim();
        return Float.parseFloat(lst);
    }

    public float getMostScore() {
        String mst = right_scores.getSelectedItem().toString().trim();
        return Float.parseFloat(mst);
    }

    public boolean isScoreFt() {
        return scoreserr.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isBreakFilter() {
        return breaks.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getLeastBreak() {
        String lst = left_breaks.getSelectedItem().toString().trim();
        return Float.parseFloat(lst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getMostBreak() {
        String mst = right_breaks.getSelectedItem().toString().trim();
        return Float.parseFloat(mst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isBreakFt() {
        return breakserr.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isContinueFilter() {
        return continus.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getLeastContinue() {
        String lst = left_continus.getSelectedItem().toString().trim();
        return Float.parseFloat(lst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getMostContinue() {
        String mst = right_continus.getSelectedItem().toString().trim();
        return Float.parseFloat(mst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isContinueFt() {
        return continuserr.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isWinConFilter() {
        return hostWinContinu.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getLeastWinCon() {
        String lst = left_hostWinContinu.getSelectedItem().toString().trim();
        return Float.parseFloat(lst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getMostWinCon() {
        String mst = right_hostWinContinu.getSelectedItem().toString().trim();
        return Float.parseFloat(mst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isWinConFt() {
        return hostWinContinuerr.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isDrawConFilter() {
        return hostDrawContinu.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getLeastDrawCon() {
        String lst = left_hostDrawContinu.getSelectedItem().toString().trim();
        return Float.parseFloat(lst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getMostDrawCon() {
        String mst = right_hostDrawContinu.getSelectedItem().toString().trim();
        return Float.parseFloat(mst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isDrawConFt() {
        return hostDrawContinuerr.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isLossConFilter() {
        return hostLossContinu.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getLeastLossCon() {
        String lst = left_hostLossContinu.getSelectedItem().toString().trim();
        return Float.parseFloat(lst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getMostLossCon() {
        String mst = right_hostLossContinu.getSelectedItem().toString().trim();
        return Float.parseFloat(mst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isLossConFt() {
        return hostLossContinuerr.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isWinDrawFilter() {
        return winDrawContinu.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getLeastWinDraw() {
        String lst = left_winDrawContinu.getSelectedItem().toString().trim();
        return Float.parseFloat(lst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getMostWinDraw() {
        String mst = right_winDrawContinu.getSelectedItem().toString().trim();
        return Float.parseFloat(mst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isWinDrawFt() {
        return winDrawContinuerr.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isWinLossFilter() {
        return winLossContinu.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getLeastWinLoss() {
        String lst = left_winLossContinu.getSelectedItem().toString().trim();
        return Float.parseFloat(lst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getMostWinLoss() {
        String mst = right_winLossContinu.getSelectedItem().toString().trim();
        return Float.parseFloat(mst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isWinLossFt() {
        return winLossContinuerr.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isDrawLossFilter() {
        return drawLossContinu.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getLeastDrawLoss() {
        String lst = left_drawLossContinu.getSelectedItem().toString().trim();
        return Float.parseFloat(lst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getMostDrawLoss() {
        String mst = right_drawLossContinu.getSelectedItem().toString().trim();
        return Float.parseFloat(mst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isDrawLossFt() {
        return drawLossContinuerr.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isDstSumFilter() {
        return conPartSum.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getLeastDstSum() {
        String lst = left_conPartSum.getSelectedItem().toString().trim();
        return Float.parseFloat(lst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getMostDstSum() {
        String mst = right_conPartSum.getSelectedItem().toString().trim();
        return Float.parseFloat(mst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isDstSumFt() {
        return conPartSumerr.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isNbrCrsFilter() {
        return conPartCrs.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getLeastNbrCrs() {
        String lst = left_conPartCrs.getSelectedItem().toString().trim();
        return Float.parseFloat(lst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getMostNbrCrs() {
        String mst = right_conPartCrs.getSelectedItem().toString().trim();
        return Float.parseFloat(mst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isNbrCrsFt() {
        return conPartCrserr.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isPosCrsFilter() {
        return crsSum.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getLeastPosCrs() {
        String lst = left_crsSum.getSelectedItem().toString().trim();
        return Float.parseFloat(lst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getMostPosCrs() {
        String mst = right_crsSum.getSelectedItem().toString().trim();
        return Float.parseFloat(mst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isPosCrsFt() {
        return crsSumerr.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isFirstBetFilter() {
        return firstOdds.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getLeastFirstBet() {
        String lst = left_firstOdds.getSelectedItem().toString().trim();
        return Float.parseFloat(lst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getMostFirstBet() {
        String mst = right_firstOdds.getSelectedItem().toString().trim();
        return Float.parseFloat(mst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isFirstBetFt() {
        return firstOddserr.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isSecondBetFilter() {
        return secondOdds.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getLeastSecondBet() {
        String lst = left_secondOdds.getSelectedItem().toString().trim();
        return Float.parseFloat(lst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getMostSecondBet() {
        String mst = right_secondOdds.getSelectedItem().toString().trim();
        return Float.parseFloat(mst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isSecondBetFt() {
        return secondOddserr.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isThirdBetFilter() {
        return thirdOdds.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getLeastThirdBet() {
        String lst = left_thirdOdds.getSelectedItem().toString().trim();
        return Float.parseFloat(lst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getMostThirdBet() {
        String mst = right_thirdOdds.getSelectedItem().toString().trim();
        return Float.parseFloat(mst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isThirdBetFt() {
        return thirdOddserr.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isRangeRankFilter() {
        return rangeRank.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getLeastRangeRank() {
        String lst = left_rangeRank.getText().trim();
        if (!lst.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
            JOptionPane.showMessageDialog(null, MessageConstants.NUMBER_ERROR);
        }
        return Float.parseFloat(lst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getMostRangeRank() {
        String mst = right_rangeRank.getText().trim();
        if (!mst.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
            JOptionPane.showMessageDialog(null, MessageConstants.NUMBER_ERROR);
        }
        return Float.parseFloat(mst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isRangeRankFt() {
        return rangeRankerr.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isProbCrsFilter() {
        return probility.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getLeastProbCrs() {
        String lst = left_probility.getText().trim();
        if (!lst.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
            JOptionPane.showMessageDialog(null, MessageConstants.NUMBER_ERROR);
        }
        return Float.parseFloat(lst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getMostProbCrs() {
        String mst = right_probility.getText().trim();
        if (!mst.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
            JOptionPane.showMessageDialog(null, MessageConstants.NUMBER_ERROR);
        }
        return Float.parseFloat(mst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isProbCrsFt() {
        return probilityerr.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isExpectFilter() {
        return expectBet.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getLeastExpect() {
        String lst = left_expectBet.getText().trim();
        if (!lst.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
            JOptionPane.showMessageDialog(null, MessageConstants.NUMBER_ERROR);
        }
        return Float.parseFloat(lst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getMostExpect() {
        String mst = right_expectBet.getText().trim();
        if (!mst.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
            JOptionPane.showMessageDialog(null, MessageConstants.NUMBER_ERROR);
        }
        return Float.parseFloat(mst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isExpectFt() {
        return expectBeterr.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isProbSumFilter() {
        return probilitySum.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getLeastProbSum() {
        String lst = left_probilitySum.getText().trim();
        if (!lst.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
            JOptionPane.showMessageDialog(null, MessageConstants.NUMBER_ERROR);
        }
        return Float.parseFloat(lst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getMostProbSum() {
        String mst = right_probilitySum.getText().trim();
        if (!mst.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
            JOptionPane.showMessageDialog(null, MessageConstants.NUMBER_ERROR);
        }
        return Float.parseFloat(mst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isProbSumFt() {
        return probilitySumerr.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isOddsSumFilter() {
        return oddsSum.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getLeastOddsSum() {
        String lst = left_oddsSum.getText().trim();
        if (!lst.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
            JOptionPane.showMessageDialog(null, MessageConstants.NUMBER_ERROR);
        }
        return Float.parseFloat(lst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getMostOddsSum() {
        String mst = right_oddsSum.getText().trim();
        if (!mst.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
            JOptionPane.showMessageDialog(null, MessageConstants.NUMBER_ERROR);
        }
        return Float.parseFloat(mst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isOddsSumFt() {
        return oddsSumerr.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isOddsCrsFilter() {
        return oddsCrs.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getLeastOddsCrs() {
        String lst = left_oddsCrs.getText().trim();
        if (!lst.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
            JOptionPane.showMessageDialog(null, MessageConstants.NUMBER_ERROR);
        }
        return Float.parseFloat(lst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getMostOddsCrs() {
        String mst = right_oddsCrs.getText().trim();
        if (!mst.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
            JOptionPane.showMessageDialog(null, MessageConstants.NUMBER_ERROR);
        }
        return Float.parseFloat(mst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isOddsCrsFt() {
        return oddsCrserr.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isRewardFilter() {
        return winIndice.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getLeastReward() {
        String lst = left_winIndice.getText().trim();
        if (!lst.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
            JOptionPane.showMessageDialog(null, MessageConstants.NUMBER_ERROR);
        }
        return Float.parseFloat(lst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return float
     * @throws
     */
    public float getMostReward() {
        String mst = right_winIndice.getText().trim();
        if (!mst.matches(RegexConstants.POSITIVE_FLOAT_REGEX)) {
            JOptionPane.showMessageDialog(null, MessageConstants.NUMBER_ERROR);
        }
        return Float.parseFloat(mst);
    }

    /**
     * @方法说明:
     * @参数: @return
     * @return boolean
     * @throws
     */
    public boolean isRewardFt() {
        return winIndiceerr.isSelected();
    }

    /**
     * @方法说明:
     * @参数: @param username
     * @return void
     * @throws
     */
    public void loginSuccess(String username) {
        user.setText("欢迎您，" + username);
        accountInfo.setVisible(true);
        changePassword.setEnabled(true);
        safeSetting.setEnabled(true);
        cryptoguardSetting.setEnabled(true);
        payPasswordSetting.setEnabled(true);
        recharge.setEnabled(true);
        if (SystemCache.fee_mode == SystemConstants.CHARGE_MODE) {
            toVIP.setEnabled(true);
        }
        logout.setEnabled(true);
    }

    /**
     * @方法说明:
     * @参数:
     * @return void
     * @throws
     */
    public void logout() {
        user.setText("欢迎您，");
        accountInfo.setVisible(false);
        changePassword.setEnabled(false);
        safeSetting.setEnabled(false);
        cryptoguardSetting.setEnabled(false);
        payPasswordSetting.setEnabled(false);
        recharge.setEnabled(false);
        toVIP.setEnabled(false);
        logout.setEnabled(false);
    }

    /**
     * @方法说明: 获取倍数
     * @参数: @return
     * @return String
     * @throws
     */
    public String getMulti() {
        return multi.getText();
    }

}
