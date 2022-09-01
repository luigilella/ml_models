/****************************************************************************

Copyright (C) (2003) Luigi Lella

This program is free software: you can redistribute it and/or modify it under
the terms of the GNU General Public License as published by the Free Software
Foundation, either  version 3 of the License, or  (at your option)  any later
version.

This program is distributed  in the hope that it will be useful,  but WITHOUT 
ANY WARRANTY, without even the implied warranty of MERCHANTABILITY or FITNESS
FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License  along with 
this program. If not, see <http://www.gnu.org/licenses/>.

*****************************************************************************/

import java.util.HashSet;
import java.io.*;

public class ReducedStopList implements Serializable
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private HashSet<String> set;
  public ReducedStopList()
  {
    set = new HashSet<String>();
  }
  public synchronized void initialize()
  {
    String[] reducedList = {
    //articoli
    "il","lo","l","la","i","li","le","gli","un","uno","una",
    //congiunzioni
    "e","ed","o","od","ma","affinché","perché","poiché","siccome","quando","mentre","finché","che","come","così","tanto","più","meno",
    "anche",
     //preposizioni
    "di","del","dello","dei","degli","dell","degl","della","delle","a","ad","al","allo","ai","agli","all","agl","alla","alle","da","dal",
    "dallo","dai","dagli","dall","dagl","dalla","dalle","in","nel","nello","nei","negli","nell","negl","nella","nelle","con","col","coi",
    "su","sul","sullo","sui","sugli","sull","sugl","sulla","sulle","per","tra","fra",
    //pronomi
    "io","tu","egli","ella","lui","lei","esso","noi","voi","essi","loro","me","te","sé","mio","mia","miei","mie","tuo","tua","tuoi","tue",
    "suo","sua","suoi","sue","nostro","nostra","nostri","nostre","vostro","vostra","vostri","vostre","m","mi","ti","ci","c","si","s","vi","v",
    "quanto","quanti","quanta","quante","glielo","gliela","glieli","gliele","ciò","costui","costei","costoro","colui","colei","coloro","alcuno",
    "alcuna","alcuni","alcune","altro","altra","altri","altre","certo","certa","certi","certe","ciascuno","ciascuna","ognuno","ognuna",
    "qualcuno","qualcuna","chiunque","qualcosa","alcunché","ne","se","quale","quali","cui","tutto","tutta","tutti",
    //avverbi
    "dov","dove","non","raramente","frequentemente","spesso","mai","sempre","talvolta","sopra","sotto","dietro","davanti","vicino","accanto",
    "lontano","qui","là","fuori","dentro","abbastanza","poco","molto","troppo","ieri","oggi","adesso","subito","poi","davvero","sicuramente",
    "mai","giammai","nemmeno","forse","probabilmente","chissà","contro","già",
    //sostantivi
    "chi","giù","destra","sinistra",
    //aggettivi dimostrativi
    "quel","quello","quelli","quella","quelle","questo","questi","questa","queste","codesto","codesta","codesti","codeste",
    //declinazioni verbo avere
    "ho","hai","ha","abbiamo","avete","hanno","abbia","abbiate","abbiano","avrò","avrai","avrà","avremo","avrete","avranno","avrei","avresti",
    "avrebbe","avremmo","avreste","avrebbero","avevo","avevi","aveva","avevamo","avevate","avevano","ebbi","avesti","ebbe","avemmo","aveste",
    "ebbero","avessi","avesse","avessimo","avessero","avendo","avuto","avuta","avuti","avute",
    //declinazioni verbo essere
    "sono","sei","è","siamo","siete","sia","siate","siano","sarò","sarai","sarà","saremo","sarete","saranno","sarei","saresti","sarebbe","saremmo",
    "sareste","sarebbero","ero","eri","era","eravamo","eravate","erano","fui","fosti","fu","fummo","foste","furono","fossi","fosse","fossimo",
    "fossero","essendo",
    //declinazioni verbo fare
    "faccio","fai","facciamo","fanno","faccia","facciate","facciano","farò","farai","farà","faremo","farete","faranno","farei","faresti","farebbe",
    "faremmo","fareste","farebbero","facevo","facevi","faceva","facevamo","facevate","facevano","feci","facesti","fece","facemmo","faceste","fecero",
    "facessi","facesse","facessimo","facessero","facendo",
    //declinazioni verbo stare
    "sto","stai","sta","stiamo","stanno","stia","stiate","stiano","starò","starai","starà","staremo","starete","staranno","starei","staresti",
    "starebbe","staremmo","stareste","starebbero","stavo","stavi","stava","stavamo","stavate","stavano","stetti","stesti","stette","stemmo","steste",
    "stettero","stessi","stesse","stessimo","stessero","stando",
    //vari
    "n","t","pi","due","fatto","cento"
    };
    
    // pronouns and adjectives
    /*"all","another","any","anybody","anyone","anything","better","both","each",
    "eighth","either","else","every","everybody","everything","everyone","fifth",
    "first","fourth","he","her","hers","herself","him","himself","his","i","it",
    "its","itself","longer","me","most","my","myself","neither","ninth","nobody",
    "none","nothing","other","our","ours","ourselves","own","same","second",
    "seventh","she","sixth","some","somebody","someone","something","such","that",
    "their","theirs","them","themselves","tenth","these","they","third","this",
    "those","us","we","what","whatever","which","whichever","who","whoever","whole",
    "whom","whose","worst","you","your","yours","yourself","yourselves",

    // adverbs
    "ahead","also","always","anywere","badly","back","besides","early","even",
    "ever","everytime","everywhere","finally","forward","frequently","how",
    "however","instead","late","later","less","many","more","much","never","not",
    "now","nowhere","often","once","only","previously","quite","rarely","seldom",
    "so","soon","sooner","somewere","there","too","twice","very","when","whenever",
    "where","wherever","while","without","sometimes",

    // verbs
    "am","are","ask","asked","asks","be","been","being","call","called","calls",
    "came","can","can't","cannot","come","could","couldn't","decide","decided",
    "decides","did","didn't","do","does","doesn't","doing","don't","feel","feels",
    "felt","find","finds","finish","finishes","finished","found","gave","get",
    "gets","give","given","gives","go","goes","going","gone","got","had","happen",
    "happened","happens","has","hasn't","have","haven't","intended","intend",
    "intends","is","isn't","keep","keeps","kept","let","lets","made","make",
    "makes","may","might","must","mustn't","ought","return","returned","returns",
    "said","say","says","saw","see","seem","seems","seemed","seen","sees","select",
    "selected","selects","shall","shan't","should","start","started","starts",
    "take","taken","takes","tell","tells","think","thinks","thought","told","took",
    "use","uses","used","want","wants","was","wasn't","welcome","went","were",
    "weren't","will","won't","would","wouldn't",

    // articles, conjunctions and prepositions
    "about","above","across","after","against","along","among","an","and",
    "around","as","at","away","because","before","behind","beside","between","but",
    "by","down","during","for","from","front","here","if","in","inside","into",
    "near","next","nor","of","on","opposite","or","out","outside","over","than",
    "the","then","through","till","to","towards","under","until","up","with",
    "within",

    // numbers and dates
    "afternoon","april","august","days","dozen","december","dozens","eight",
    "evening","february","four","five","friday","hundred","hundreds","january",
    "july","june","march","may","midday","midnight","million","millions","monday",
    "month","months","morning","nine","noon","november","october","one","saturday",
    "september","seven","six","sunday","ten","thousand","thousands","three",
    "thursday","today","tomorrow","tuesday","two","wednesday","week","year",
    "years","yesterday","weeks","zero",

    // abbreviations
    "adm","aug","cap","cent","cg","ch","cit","cl","cm","col","com","con",
    "corp","cpl","cps","ctr","dec","cem","dep","dept","deg","dg","dir","dkg",
    "dkl","dkm","dl","dlit","dlitt","dm","dph","dphil","dpt","dr","ed","e g",
    "eq","et","al","etc","ev","ex","feb","f e","ff","fr","ft","gb",
    "gen","gent","gev","ha","hg","hl","hm","hz","ib","ibid","id","jan",
    "kb","kc","kg","khz","kl","km","kmph","kmps","kv","kw","lab","ld",
    "ma","mar","max","mb","mbps","me","mg","mh","mhz","mil","mf","mm","mon","mph",
    "mpm","mps","mr","mrs","ms","mt","nov","oct","ok","phd","pp","p p",
    "pres","prof","prox","san","sat","sep","sept","sr","st","suom","tel",
    "thur","thurs","tu","ult","univ","viz","vs","wh","wed","yd","p-m","co",
    "inc",

    // tags and attributes
    "a-length","absbottom","absmiddle","action","actual","address","align","alink",
    "alt","alternate","amp","applet","area","arg","array","available",
    "background","behavior","bgcolor","bgproperties","bgsound","base","baseline",
    "big","black","blink","blockquote","body","border","bordercolor",
    "bordercolordark","bordercolorlight","bot","bottom","br","caption","case",
    "cellspacing","cellpadding","center","changeimages arguments","circle","cite",
    "class","clear","close","code","color","colspan","cols","content","coords",
    "d-all","d-layers","d-mm","dd","dfn","dir","direction","div","document",
    "document-mm","document-images","dt","dynsrc","em","enctype","end","face",
    "false","fermin","fff6d3","findobj","font","font-family","font-size","form",
    "frame","frameset","function","get","h1","h2","h3","h4","hard","head",
    "height","hidden","hr","href","hspace","html","ie","if-msfphover",
    "if-msfphout","image","imageready","img","input","ismap","language",
    "language=-javascript","left","li","line","link","links","lowsrc","map",
    "marginheight","marginwidth","marquee","maxlength","menu","meta","method",
    "middle","msfphover","msfppreload","msnavigation","mstheme","msthemelist",
    "n-substring","name","navigator-appname","navigator-appversion","nbsp",
    "newimage","nobr","noframe","noresize","noshade","nowrap","off","ol","online",
    "onmouseout","onmouseover","option","p-1","param","parseint",
    "parseint-navigator-appversion","poly","post","pre","preload","preloadflag",
    "quot","rect","reset","rgb","right","rowspan","rows","rslt","samp","scr",
    "script","scroll","scrollamount","scrolldelay","scrolling","select","shape",
    "size","slide","small","span","src","start","startspan","strike","strong",
    "style","sub","submit","sup","table","target","td","text","textarea","texttop",
    "th","thrace","title","top","tr","true","tt","type","u-c","ul","unix",
    "usag-indexof","usemap","valign","value","var","ve","vlink","vspace","wbr",
    "webbot","white","width","wrap","x-osrc","x-src","&amp","&nbsp","&quot",

    // miscellaneous
    "associated","based","compared","considered","denoted","described","email",
    "event","events","facsimile","fax","following","help","including","incoming",
    "kind","kinds","lot","no","outgoing","point","presented","related","shit",
    "thing","things","thus","time","von","way","ways","word","words","world","yes",
    "concerned"
    
    // Pedersen Stop-List
	/*
    "I","a","an","as","at","by","he","his","me","or","thou","us","who",
    
    "against","amid","amidst","among","amongst","and","anybody","anyone","because",
    "beside","circa","despite","during","everybody","everyone","for","from","her",
    "hers","herself","him","himself","hisself","idem","if","into","it","its",
    "itself","myself","nor","of","oneself","onto","our","ourself","ourselves","per",
    "she","since","than","that","the","thee","theirs","them","themselves","they",
    "thine","this","thyself","to","tother","toward","towards","unless","until",
    "upon","versus","via","we","what","whatall","whereas","which","whichever",
    "whichsoever","whoever","whom","whomever","whomso","whomsoever","whose",
    "whosoever","with","without","ye","you","you-all","yours","yourself","yourselves",
    
    "aboard","about","above","across","after","all","along","alongside","although",
    "another","anti","any","anything","around","astride","aught","bar","barring",
    "before","behind","below","beneath","besides","beyond","both","but","concerning",
    "considering","down","each","either","enough","except","excepting","excluding",
    "few","fewer","following","ilk","in","including","inside","like","many","mine",
    "minus","more","most","naught","near","neither","nobody","none","nothing",
    "notwithstanding","off","on","opposite","other","otherwise","outside","over",
    "own","past","pending","plus","round","save","self","several","so","some",
    "somebody","someone","something","somewhat","such","suchlike","sundry","there",
    "though","through","throughout","till","twain","under","underneath","unlike",
    "up","various","vis-a-vis","whatever","whatsoever","when","wherewith",
    "wherewithal","while","within","worth","yet","yon","yonder",
   
    // XML key
    "newsitem","itemid","editdetail","&amp","&nbsp","&quot","xml:lang",
    
    // Added
    "4to","april","apr","are","august","aug","be","december","dec","february","feb",
    "friday","fri","have","january","jan","july","june","march","mar","may","monday","mon",
    "november","nov","october","oct","saturday","sat","september","sep","sept","sunday",
    "thursday","th","tuesday","tues","wednesday","wed"};

    // Also added
    /*"second","minute","hour","day","week","month","season","year","century","millennium"*/
    
       
    for(int i=0; i < reducedList.length; i++)
      set.add(reducedList[i]);
  }
  @SuppressWarnings("unchecked")
  public synchronized void load() throws IOException,ClassNotFoundException
  {
    File inFile = new File("C:\\Users\\luigi.lella\\Desktop\\news", "reducedstoplist.data");
    FileInputStream inFileStream = new FileInputStream(inFile);
    ObjectInputStream inObjectStream = new ObjectInputStream(inFileStream);
    set = (HashSet<String>) inObjectStream.readObject();
    inObjectStream.close();
  }
  
  public void save() throws IOException
  {
    File outFile = new File("C:\\Users\\luigi.lella\\Desktop\\news", "reducedstoplist.data");
    FileOutputStream outFileStream = new FileOutputStream(outFile);
    ObjectOutputStream outObjectStream = new ObjectOutputStream(outFileStream);
    outObjectStream.writeObject(set);
    outObjectStream.close();
  }
  
  public synchronized void add(String word)
  {
    set.add(word);
  }
  public synchronized boolean contains(String word)
  {
    if (set.contains(word) == false)
      return false;
    else
      return true;
  }
}
