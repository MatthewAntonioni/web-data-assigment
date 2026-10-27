import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class main{
public static void main(String[] args){

Scanner scan = new Scanner(System.in);

StringBuilder htmlCode = new StringBuilder();

System.out.println("enter the html code: enter [end] to finssh ");

while(scan.hasNextLine()){

    String line = scan.nextLine();

    if(line.equals("end")){

        break;

    }

    htmlCode.append(line).append("\n");

}



Document doc = Jsoup.parse(htmlCode.toString());


System.out.println(doc +"");
}
}
