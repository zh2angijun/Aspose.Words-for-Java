package ApiExamples;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.ControlChar;
import com.aspose.ms.NUnit.Framework.msAssert;
import org.testng.Assert;
import com.aspose.words.NodeType;
import com.aspose.words.Section;
import com.aspose.ms.System.Convert;


@Test
public class ExControlChar extends ApiExampleBase
{
    @Test
    public void insertControlChars() throws Exception
    {
        //ExStart
        //ExFor:ControlChar.Cell
        //ExFor:ControlChar.ColumnBreak
        //ExFor:ControlChar.Lf
        //ExFor:ControlChar.LineBreak
        //ExFor:ControlChar.LineFeed
        //ExFor:ControlChar.NonBreakingSpace
        //ExFor:ControlChar.PageBreak
        //ExFor:ControlChar.ParagraphBreak
        //ExFor:ControlChar.SectionBreak
        //ExFor:ControlChar.CellChar
        //ExFor:ControlChar.ColumnBreakChar
        //ExFor:ControlChar.DefaultTextInputChar
        //ExFor:ControlChar.FieldEndChar
        //ExFor:ControlChar.FieldStartChar
        //ExFor:ControlChar.FieldSeparatorChar
        //ExFor:ControlChar.LineBreakChar
        //ExFor:ControlChar.LineFeedChar
        //ExFor:ControlChar.NonBreakingHyphenChar
        //ExFor:ControlChar.NonBreakingSpaceChar
        //ExFor:ControlChar.OptionalHyphenChar
        //ExFor:ControlChar.PageBreakChar
        //ExFor:ControlChar.ParagraphBreakChar
        //ExFor:ControlChar.SectionBreakChar
        //ExFor:ControlChar.SpaceChar
        //ExSummary:Shows how to use various control characters.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Add a regular space
        builder.write("Before space." + ControlChar.SPACE_CHAR + "After space.");

        // Add a NBSP, or non-breaking space
        // Unlike the regular space, this space can't have an automatic line break at its position 
        builder.write("Before space." + ControlChar.NON_BREAKING_SPACE + "After space.");

        // Add a tab character
        builder.write("Before tab." + ControlChar.TAB + "After tab.");

        // Add a line break
        builder.write("Before line break." + ControlChar.LINE_BREAK + "After line break.");

        // This adds a new line and starts a new paragraph
        // Same value as ControlChar.Lf
        msAssert.areEqual(1, doc.getFirstSection().getBody().getChildNodes(NodeType.PARAGRAPH, true).getCount());
        builder.write("Before line feed." + ControlChar.LINE_FEED + "After line feed.");
        msAssert.areEqual(2, doc.getFirstSection().getBody().getChildNodes(NodeType.PARAGRAPH, true).getCount());

        // The line feed character has two versions
        msAssert.areEqual(ControlChar.LINE_FEED, ControlChar.LF);

        // Add a paragraph break, also adding a new paragraph
        builder.write("Before paragraph break." + ControlChar.PARAGRAPH_BREAK + "After paragraph break.");
        msAssert.areEqual(3, doc.getFirstSection().getBody().getChildNodes(NodeType.PARAGRAPH, true).getCount());

        // Add a section break. Note that this does not make a new section or paragraph
        msAssert.areEqual(1, doc.getSections().getCount());
        builder.write("Before section break." + ControlChar.SECTION_BREAK + "After section break.");
        msAssert.areEqual(1, doc.getSections().getCount());

        // A page break is the same value as a section break
        builder.write("Before page break." + ControlChar.PAGE_BREAK + "After page break.");

        // We can add a new section like this
        doc.appendChild(new Section(doc));
        builder.moveToSection(1);

        // If you have a section with more than one column, you can use a column break to make following text start on a new column
        builder.getCurrentSection().getPageSetup().getTextColumns().setCount(2);
        builder.write("Text at end of column 1." + ControlChar.COLUMN_BREAK + "Text at beginning of column 2.");

        // Save document to see the characters we added
        doc.save(getArtifactsDir() + "ControlChar.Misc.docx");

        // There are char and string counterparts for most characters
        msAssert.areEqual(Convert.toChar(ControlChar.CELL), ControlChar.CELL_CHAR);
        msAssert.areEqual(Convert.toChar(ControlChar.NON_BREAKING_SPACE), ControlChar.NON_BREAKING_SPACE_CHAR);
        msAssert.areEqual(Convert.toChar(ControlChar.TAB), ControlChar.TAB_CHAR);
        msAssert.areEqual(Convert.toChar(ControlChar.LINE_BREAK), ControlChar.LINE_BREAK_CHAR);
        msAssert.areEqual(Convert.toChar(ControlChar.LINE_FEED), ControlChar.LINE_FEED_CHAR);
        msAssert.areEqual(Convert.toChar(ControlChar.PARAGRAPH_BREAK), ControlChar.PARAGRAPH_BREAK_CHAR);
        msAssert.areEqual(Convert.toChar(ControlChar.SECTION_BREAK), ControlChar.SECTION_BREAK_CHAR);
        msAssert.areEqual(Convert.toChar(ControlChar.PAGE_BREAK), ControlChar.SECTION_BREAK_CHAR);
        msAssert.areEqual(Convert.toChar(ControlChar.COLUMN_BREAK), ControlChar.COLUMN_BREAK_CHAR);
        //ExEnd
    }
}
