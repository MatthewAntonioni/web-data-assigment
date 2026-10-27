import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

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

System.out.println("PARSING COMPLETE");

Element root = doc.selectFirst("html");

String hypertree = convert(root,0);

System.out.println("\nweboql hypertree: ");
System.out.println(hypertree);

scan.close();
}



public static String convert(Element element, int depth) {

    StringBuilder tree = new StringBuilder();

    String indent = "  ".repeat(depth);

    // Create the WebOQL arc
    tree.append(indent)
        .append("[Tag: \"")
        .append(element.tagName().toUpperCase())
        .append("\"");

    // Handle hyperlinks
    if (element.tagName().equalsIgnoreCase("a")
            && element.hasAttr("href")) {

        tree.append(", Url: \"")
            .append(escape(element.attr("href")))
            .append("\"");
    }

    // Create child elements
    StringBuilder children = new StringBuilder();

    for (Node child : element.childNodes()) {

        if (child instanceof Element) {

            if (children.length() > 0) {
                children.append(" +\n");
            }

            children.append(
                convert((Element) child, depth + 1)
            );
        }

        else if (child instanceof TextNode) {

            TextNode textNode = (TextNode) child;

            String text = textNode.text().trim();

            if (!text.isEmpty()) {

                if (children.length() > 0) {
                    children.append(" +\n");
                }

                children.append("  ".repeat(depth + 1))
                    .append("[Tag: \"NOTAG\", Text: \"")
                    .append(escape(text))
                    .append("\"]");
            }
        }
    } // FOR LOOP ENDS HERE

    // Attach the completed subtree
    if (children.length() > 0) {

        tree.append(" /\n");
        tree.append(children);
        tree.append("\n").append(indent);
    }

    tree.append("]");

    return tree.toString();
}




public static String escape(String value){

    return value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", " ");

}

}
