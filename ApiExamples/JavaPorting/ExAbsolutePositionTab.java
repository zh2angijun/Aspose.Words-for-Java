package ApiExamples;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.words.Document;
import com.aspose.words.Paragraph;
import com.aspose.words.AbsolutePositionTab;
import com.aspose.words.NodeType;
import com.aspose.ms.NUnit.Framework.msAssert;
import org.testng.Assert;
import com.aspose.words.DocumentVisitor;
import com.aspose.words.VisitorAction;
import com.aspose.words.Run;
import com.aspose.ms.System.Text.msStringBuilder;


@Test
public class ExAbsolutePositionTab extends ApiExampleBase
{
    //ExStart
    //ExFor:AbsolutePositionTab
    //ExFor:AbsolutePositionTab.Accept(DocumentVisitor)
    //ExFor:DocumentVisitor.VisitAbsolutePositionTab
    //ExSummary:Shows how to use AbsolutePositionTab.
    @Test //ExSkip
    public void documentToTxt() throws Exception
    {
        // This document contains two sentences separated by an absolute position tab.
        Document doc = new Document(getMyDir() + "AbsolutePositionTab.docx");

        // An AbsolutePositionTab is a child node of a paragraph. 
        // AbsolutePositionTabs get picked up when looking for nodes of the SpecialChar type.
        Paragraph para = doc.getFirstSection().getBody().getFirstParagraph();
        AbsolutePositionTab absPositionTab = (AbsolutePositionTab)para.getChild(NodeType.SPECIAL_CHAR, 0, true);

        // This implementation of the DocumentVisitor pattern converts the document to plain text.
        MyDocToTxtWriter myDocToTxtWriter = new MyDocToTxtWriter();

        // We can run the DocumentVisitor over the whole first paragraph.
        para.accept(myDocToTxtWriter);

        // A tab character is placed where the AbsolutePositionTab was found.
        msAssert.areEqual("Before AbsolutePositionTab\tAfter AbsolutePositionTab", myDocToTxtWriter.getText());

        // An AbsolutePositionTab can accept a DocumentVisitor by itself too.
        myDocToTxtWriter = new MyDocToTxtWriter();
        absPositionTab.accept(myDocToTxtWriter);

        msAssert.areEqual("\t", myDocToTxtWriter.getText());
    }

    /// <summary>
    /// Visitor implementation that simply collects the Runs and AbsolutePositionTabs of a document as plain text. 
    /// </summary>
    public static class MyDocToTxtWriter extends DocumentVisitor
    {
        public MyDocToTxtWriter()
        {
            mBuilder = new StringBuilder();
        }

        /// <summary>
        /// Called when a Run node is encountered in the document.
        /// </summary>
        public /*override*/ /*VisitorAction*/int visitRun(Run run)
        {
            appendText(run.getText());

            // Let the visitor continue visiting other nodes.
            return VisitorAction.CONTINUE;
        }

        /// <summary>
        /// Called when an AbsolutePositionTab node is encountered in the document.
        /// </summary>
        public /*override*/ /*VisitorAction*/int visitAbsolutePositionTab(AbsolutePositionTab tab)
        {
            // We'll treat the AbsolutePositionTab as a regular tab in this case.
            msStringBuilder.append(mBuilder, "\t");

            return VisitorAction.CONTINUE;
        }

        /// <summary>
        /// Adds text to the current output. Honors the enabled/disabled output flag.
        /// </summary>
        private void appendText(String text)
        {
            msStringBuilder.append(mBuilder, text);
        }

        /// <summary>
        /// Gets the plain text of the document that was accumulated by the visitor.
        /// </summary>
        public String getText()
        {
            return mBuilder.toString();
        }

        private /*final*/ StringBuilder mBuilder;
    }
    //ExEnd
}
