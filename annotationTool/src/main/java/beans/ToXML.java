package beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

public class ToXML {

//////////////////////////++++++++++++++++++++++++++++++++++++++++++ 
 static String ar;
 static ArrayList<Element> spanElem ;
 static int indexOfChildSpans ;
 static int indexOfEvenSpans ; 					//if 0 then reached end of first
 static int indexOfNextListElem;
 static Map<Element,ArrayList<Element>> map = new HashMap<>();	
 static ArrayList<String> al = new ArrayList<>();
 static ArrayList<String> ak = new ArrayList<>();


//	
//	public ToXML(String report){
//		ar = report;
//	}
//	
	 public static void addOs(){
		 int index = 0;
		 for(String i : al){
			 String pattern = "(.*(<[^>]*>))";
		     Pattern r = Pattern.compile(pattern);
		     Matcher m = r.matcher(i);
		     if(m.find()){
		    	// System.out.println("took " + m.group(0));
		    	 String full = i;
		    	 String brackets = m.group(0);
		    	 String lastWord = full.replace(brackets,"");
		    	 
		    	 if(!lastWord.isEmpty()){
		    		 //System.out.println("last " + lastWord);
		    		 String oWord = "<O>"+lastWord+"</O>";
		    		 al.set(index, brackets +oWord);
		    	 }
		     }
		     index++;
		 }
		 
		 
	 }
	 
	 public static   void built(String pr) throws InterruptedException{
	    	String pattern = "(^<span.*?>)";
	        Pattern r = Pattern.compile(pattern);
	        Matcher m = r.matcher(pr);
	        String pattern1 = "(^</span>)";
	        Pattern r1 = Pattern.compile(pattern1);
	        Matcher m1 = r1.matcher(pr);
	        String sentence = "";
	        String addedWord = "";
	        String details;
	        Element x ;
	        Element y ;
	        
	        //System.out.println("all " + pr);
	        
	        if(m.find()){
	        //System.out.println("first " +m.group(0));
	        pr = pr.replace(m.group(0),"");							// Remove the first <span class="" ...> found from the sentence so to left with: text<>or</>..
	        //System.out.println(pr);
	        addedWord = getString(pr);
	    	pr = pr.replaceFirst(addedWord,"");

	        if(indexOfEvenSpans ==0){
	        	String name = "<"+extractDetails(m.group(0)) + ">";
	        	ak = new ArrayList<>();
	        	ak.add(name);
	        	indexOfChildSpans  =0;
	        	String a =  name + addedWord;
	        	al.add(a);
	        	//x.setName(extractDetails(m.group(0)));
	        	indexOfNextListElem++;
	        }else{
	        	String name = "<"+extractDetails(m.group(0)) + ">";
	        	ak.add(name);
	        	String a = al.get(indexOfNextListElem-1) + name + addedWord;
	        	al.set(indexOfNextListElem-1, a);
	        }
	        indexOfChildSpans++;
	    	indexOfEvenSpans++;
	    	//System.out.println("adding "+ ak );
	    	
	    	built(pr);
	        //System.out.println(pr);
	        
	        }else if(m1.find()){
	        	pr = pr.replaceFirst("</span>", "");	
	        	if(indexOfEvenSpans != 0){
	        		String name = "</"+constr(ak.get(ak.size()-1))+">";
	        			
	        		String a = al.get(indexOfNextListElem-1) + name  + getString(pr);
		        	al.set(indexOfNextListElem-1, a);
		        	//System.out.println("span to get " + ak.size() + " " +(indexOfChildSpans-1));
		        	if(!ak.isEmpty() ){
		        		ak.remove(ak.size()-1);
		        	}
	        	}
	    		
	        	
	        	
	    		pr = pr.replaceFirst(getString(pr),"");
	    		indexOfEvenSpans--;
	    		built(pr);
	        }
	        
	        
	    }
	 
	 
	 public static  String getString(String s){			// gets string like : caucasian <span ----->
	    	String pattern = "(.*?<)";
	        Pattern r = Pattern.compile(pattern);
	        Matcher m = r.matcher(s);
	        String word ="";
	        if(m.find()){
	        	word = m.group(0);
	        }
	        
	        word = word.replaceAll("<", ""); 
	       // word = "+"+word+"+";
	    	//System.out.println("word =" +word+"=");
	        return word;
	    }
	
	 public static  String constr(String str){
		 String pr = str.replaceFirst("<", "");
		 pr = pr.replaceFirst(">", "");
		// System.out.println("PR " + pr + "  " + str);
		 return pr;
	 }
	 
	 public static  String extractDetails(String str){
	    	//System.out.println(" Sentence " + str);
	    	
	    	String pattern = "(title=\".*?\")";
	        Pattern r = Pattern.compile(pattern);
	        Matcher m = r.matcher(str);
	        String cl = "";
	        if(m.find()){
	        	//System.out.println("class " + m.group(0));
	        	String patternC = "(\".*?\")";
	            Pattern rc = Pattern.compile(patternC);
	            Matcher mc = rc.matcher(m.group(0));
	            if(mc.find()){
	            	cl=mc.group(0);
	            	//System.out.println("class " + mc.group(0));
	            	cl = cl.replaceAll(Pattern.quote("\""), "");
	            }
	        }  	
	        //System.out.println(cl);
	       return cl;
	    }
	    
	 public static String begin(String report) throws InterruptedException{
		ar = report;
 		String arr[] = ar.split("<span");							
	    	String arr1[] = ar.split("</span>");		
	    	String a0 = "";
	    	String a1 = "";

	    	char checkLast = ar.charAt(ar.length() - 1);
	    	
	    	if(!arr[0].isEmpty()){
	    		//System.out.println("NOT EMPTY FIRST");
	    		ar = ar.replaceFirst(arr[0], "");							//at arr[0] it gets the part before the FRIST <span>
	        	a0 = "<O>"+arr[0]+"</O>";
	    	}
	
	    	
	    	if(!arr1[arr1.length-1].contains("<span") && checkLast != '>'){
	    		//System.out.println(arr1[arr1.length-1]);
	    		ar = ar.replaceFirst(arr1[arr1.length-1], "");
	    		//System.out.println(ar);
	    	}
	    	
	    	
	    	built(ar); 
	    	
	    	if(!arr1[arr1.length-1].contains("<span") && checkLast != '>'){
	    		//System.out.println("NOT EMPTY Last : " + arr1[arr1.length-1]);
		    	a1 = "<O>"+arr1[arr1.length-1]+"</O>";
	    	}
	    	
	    	String finalS = "";
	    	
	    	addOs(); 

	    	for(String i : al){
	    		//System.out.println("Ls parent :" + i);
	    		finalS = finalS + i;
	    	}
	    	System.out.println(a0+finalS+a1);
	    	return a0+finalS+a1;
	    	
	    	
//	    	for(String i : al){
//	    		System.out.println("Ls parent :" + i);
//	    	}
	    	
	    	
	 }
	   
	 
	
	 
	 
	    
	    
//	public static void main(String[] args) throws InterruptedException {
//		ar  =  "III <span title=\"one\"> hello <span title=\"two\"> mother <span title=\"three\"> fucker </span> how <span title=\"four\">are</span> you</span> today </span> and <span title=\"oneone\">fuck you</span> KKK .";
//		//ar = "<span style=\"background: rgba(214, 219, 229, 0.5) none repeat scroll 0% 0%; border: medium none;\" title=\"positive_finding\" class=\"j2_2\" id=\"0\">The <span style=\"background: rgba(154, 73, 135, 0.5) none repeat scroll 0% 0%;\" title=\"breast_composition\" class=\"j2_3\" id=\"1\">tall <span style=\"background: rgba(154, 73, 135, 0.5) none repeat scroll 0% 0%;\" title=\"breast_composition\" class=\"j2_3\" id=\"2\">tree has</span> no</span> leaves</span>";
//		begin();
//		System.out.println("parent " + indexOfNextListElem);
//		System.out.println("child " + indexOfEvenSpans);
//		for(String i : al){
//		System.out.println("Ls parent :" + i);
//		}
//		for(String i : ak){
//			System.out.println("spans :" + i);
//		}
//	}

}
