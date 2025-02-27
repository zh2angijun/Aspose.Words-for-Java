package Examples;

//////////////////////////////////////////////////////////////////////////
// Copyright (c) 2001-2019 Aspose Pty Ltd. All Rights Reserved.
//
// This file is part of Aspose.Words. The source code in this file
// is only intended as a supplement to the documentation, and is provided
// "as is", without warranty of any kind, either expressed or implied.
//////////////////////////////////////////////////////////////////////////

import com.aspose.words.*;
import com.aspose.words.Font;
import com.aspose.words.Shape;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class ExDocumentBuilder extends ApiExampleBase {
    @Test
    public void writeAndFont() throws Exception {
        //ExStart
        //ExFor:Font.Size
        //ExFor:Font.Bold
        //ExFor:Font.Name
        //ExFor:Font.Color
        //ExFor:Font.Underline
        //ExFor:DocumentBuilder.#ctor
        //ExId:DocumentBuilderInsertText
        //ExSummary:Inserts formatted text using DocumentBuilder.
        DocumentBuilder builder = new DocumentBuilder();

        // Specify font formatting before adding text.
        Font font = builder.getFont();
        font.setSize(16);
        font.setBold(true);
        font.setColor(Color.BLUE);
        font.setName("Arial");
        font.setUnderline(Underline.DASH);

        builder.write("Sample text.");
        //ExEnd
    }

    @Test
    public void headersAndFooters() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.#ctor(Document)
        //ExFor:DocumentBuilder.MoveToHeaderFooter
        //ExFor:DocumentBuilder.MoveToSection
        //ExFor:DocumentBuilder.InsertBreak
        //ExFor:DocumentBuilder.Writeln
        //ExFor:HeaderFooterType
        //ExFor:PageSetup.DifferentFirstPageHeaderFooter
        //ExFor:PageSetup.OddAndEvenPagesHeaderFooter
        //ExFor:BreakType
        //ExId:DocumentBuilderMoveToHeaderFooter
        //ExSummary:Creates headers and footers in a document using DocumentBuilder.
        // Create a blank document.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Specify that we want headers and footers different for first, even and odd pages.
        builder.getPageSetup().setDifferentFirstPageHeaderFooter(true);
        builder.getPageSetup().setOddAndEvenPagesHeaderFooter(true);

        // Create the headers.
        builder.moveToHeaderFooter(HeaderFooterType.HEADER_FIRST);
        builder.write("Header First");
        builder.moveToHeaderFooter(HeaderFooterType.HEADER_EVEN);
        builder.write("Header Even");
        builder.moveToHeaderFooter(HeaderFooterType.HEADER_PRIMARY);
        builder.write("Header Odd");

        // Create three pages in the document.
        builder.moveToSection(0);
        builder.writeln("Page1");
        builder.insertBreak(BreakType.PAGE_BREAK);
        builder.writeln("Page2");
        builder.insertBreak(BreakType.PAGE_BREAK);
        builder.writeln("Page3");

        doc.save(getArtifactsDir() + "DocumentBuilder.HeadersAndFooters.doc");
        //ExEnd
    }

    @Test
    public void insertMergeField() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.InsertField(String)
        //ExFor:DocumentBuilder.MoveToMergeField(String, Boolean, Boolean)
        //ExId:DocumentBuilderInsertField
        //ExSummary:Shows how to insert merge fields and move between them.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.insertField("MERGEFIELD MyMergeField1 \\* MERGEFORMAT");
        builder.insertField("MERGEFIELD MyMergeField2 \\* MERGEFORMAT");

        Assert.assertEquals(doc.getRange().getFields().getCount(), 2);

        // The second merge field starts immediately after the end of the first
        // We'll move the builder's cursor to the end of the first so we can split them by text
        builder.moveToMergeField("MyMergeField1", true, false);

        builder.write(" Text between our two merge fields. ");

        doc.save(getArtifactsDir() + "DocumentBuilder.MergeFields.docx");
        //ExEnd
    }

    @Test
    public void insertFieldFieldCode() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.InsertField(String)
        //ExFor:Field
        //ExFor:Field.Update
        //ExFor:Field.Result
        //ExFor:Field.GetFieldCode
        //ExFor:Field.Type
        //ExFor:Field.Remove
        //ExFor:FieldType
        //ExSummary:Inserts a field into a document using DocumentBuilder and FieldCode.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Insert a simple Date field into the document.
        // When we insert a field through the DocumentBuilder class we can get the
        // special Field object which contains information about the field.
        Field dateField = builder.insertField("DATE \\* MERGEFORMAT");

        // Update this particular field in the document so we can get the FieldResult.
        dateField.update();

        // Display some information from this field.
        // The field result is where the last evaluated value is stored. This is what is displayed in the document
        // When field codes are not showing.
        System.out.println(MessageFormat.format("FieldResult: {0}", dateField.getResult()));

        // Display the field code which defines the behaviour of the field. This can been seen in Microsoft Word by pressing ALT+F9.
        System.out.println(MessageFormat.format("FieldCode: {0}", dateField.getFieldCode()));

        // The field type defines what type of field in the Document this is. In this case the type is "FieldDate"
        System.out.println(MessageFormat.format("FieldType: {0}", dateField.getType()));

        // Finally let's completely remove the field from the document. This can easily be done by invoking the Remove method on the object.
        dateField.remove();
        //ExEnd
    }

    @Test
    public void insertHorizontalRule() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.InsertHorizontalRule
        //ExFor:ShapeBase.IsHorizontalRule
        //ExSummary:Shows how to insert horizontal rule shape in a document.
        // Use a document builder to insert a horizontal rule
        DocumentBuilder builder = new DocumentBuilder();
        builder.insertHorizontalRule();

        // Get the rule from the document's shape collection and verify it
        Shape horizontalRule = (Shape) builder.getDocument().getChild(NodeType.SHAPE, 0, true);
        Assert.assertTrue(horizontalRule.isHorizontalRule());
        //ExEnd
    }

    @Test
    public void fieldLocale() throws Exception {
        //ExStart
        //ExFor:Field.LocaleId
        //ExSummary: Get or sets locale for fields
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Field field = builder.insertField("DATE \\* MERGEFORMAT");
        field.setLocaleId(2064);

        ByteArrayOutputStream dstStream = new ByteArrayOutputStream();
        doc.save(dstStream, SaveFormat.DOCX);

        Field newField = doc.getRange().getFields().get(0);
        Assert.assertEquals(newField.getLocaleId(), 2064);
        //ExEnd
    }

    @Test(dataProvider = "getFieldCodeDataProvider")
    public void getFieldCode(final boolean containsNestedFields) throws Exception {
        //ExStart
        //ExFor:Field.GetFieldCode
        //ExFor:Field.GetFieldCode(bool)
        //ExSummary:Shows how to get text between field start and field separator (or field end if there is no separator)
        Document doc = new Document(getMyDir() + "Field.FieldCode.docx");

        for (Field field : doc.getRange().getFields()) {
            if (field.getType() == FieldType.FIELD_IF) {
                FieldIf fieldIf = (FieldIf) field;

                String fieldCode = fieldIf.getFieldCode();
                Assert.assertEquals(fieldCode, " IF  MERGEFIELD Q223  > 0 \" (and additionally London Weighting of   MERGEFIELD  Q223 \\f £  per hour) \" \"\" "); //ExSkip

                if (containsNestedFields) {
                    fieldCode = fieldIf.getFieldCode(true);
                    Assert.assertEquals(fieldCode, " IF  MERGEFIELD Q223  > 0 \" (and additionally London Weighting of   MERGEFIELD  Q223 \\f £  per hour) \" \"\" "); //ExSkip
                } else {
                    fieldCode = fieldIf.getFieldCode(false);
                    Assert.assertEquals(fieldCode, " IF  > 0 \" (and additionally London Weighting of   per hour) \" \"\" "); //ExSkip
                }
            }
        }
        //ExEnd
    }

    //JAVA-added data provider for test method
    @DataProvider(name = "getFieldCodeDataProvider")
    public static Object[][] getFieldCodeDataProvider() {
        return new Object[][]{
                {true},
                {false}};
    }

    @Test
    public void documentBuilderAndSave() throws Exception {
        //ExStart
        //ExId:DocumentBuilderAndSave
        //ExSummary:Shows how to create build a document using a document builder.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.writeln("Hello World!");

        doc.save(getArtifactsDir() + "DocumentBuilderAndSave.docx");
        //ExEnd
    }

    @Test
    public void insertHyperlink() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.InsertHyperlink
        //ExFor:Font.ClearFormatting
        //ExFor:Font.Color
        //ExFor:Font.Underline
        //ExFor:Underline
        //ExId:DocumentBuilderInsertHyperlink
        //ExSummary:Inserts a hyperlink into a document using DocumentBuilder.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.write("Please make sure to visit ");

        // Specify font formatting for the hyperlink.
        builder.getFont().setColor(Color.BLUE);
        builder.getFont().setUnderline(Underline.SINGLE);
        // Insert the link.
        builder.insertHyperlink("Aspose Website", "http://www.aspose.com", false);

        // Revert to default formatting.
        builder.getFont().clearFormatting();

        builder.write(" for more information.");

        doc.save(getArtifactsDir() + "DocumentBuilder.InsertHyperlink.doc");
        //ExEnd
    }

    @Test
    public void pushPopFont() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.PushFont
        //ExFor:DocumentBuilder.PopFont
        //ExFor:DocumentBuilder.InsertHyperlink
        //ExSummary:Shows how to use temporarily save and restore character formatting when building a document with DocumentBuilder.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Set up font formatting and write text that goes before the hyperlink.
        builder.getFont().setName("Arial");
        builder.getFont().setSize(24);
        builder.getFont().setBold(true);
        builder.write("To go to an important location, click ");

        // Save the font formatting so we use different formatting for hyperlink and restore old formatting later.
        builder.pushFont();

        // Set new font formatting for the hyperlink and insert the hyperlink.
        // The "Hyperlink" style is a Microsoft Word built-in style so we don't have to worry to
        // create it, it will be created automatically if it does not yet exist in the document.
        builder.getFont().setStyleIdentifier(StyleIdentifier.HYPERLINK);
        builder.insertHyperlink("here", "http://www.google.com", false);

        // Restore the formatting that was before the hyperlink.
        builder.popFont();

        builder.writeln(". We hope you enjoyed the example.");

        doc.save(getArtifactsDir() + "DocumentBuilder.PushPopFont.doc");
        //ExEnd
    }

    @Test
    public void insertWatermark() throws Exception {
        //ExStart
        //ExFor:HeaderFooterType
        //ExFor:DocumentBuilder.MoveToHeaderFooter
        //ExFor:PageSetup.PageWidth
        //ExFor:PageSetup.PageHeight
        //ExFor:DocumentBuilder.InsertImage(Image)
        //ExFor:WrapType
        //ExFor:RelativeHorizontalPosition
        //ExFor:RelativeVerticalPosition
        //ExSummary:Inserts a watermark image into a document using DocumentBuilder.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // The best place for the watermark image is in the header or footer so it is shown on every page.
        builder.moveToHeaderFooter(HeaderFooterType.HEADER_PRIMARY);

        BufferedImage image = ImageIO.read(new File(getImageDir() + "Watermark.png"));

        // Insert a floating picture.
        Shape shape = builder.insertImage(image);
        shape.setWrapType(WrapType.NONE);
        shape.setBehindText(true);

        shape.setRelativeHorizontalPosition(RelativeHorizontalPosition.PAGE);
        shape.setRelativeVerticalPosition(RelativeVerticalPosition.PAGE);

        // Calculate image left and top position so it appears in the center of the page.
        shape.setLeft((builder.getPageSetup().getPageWidth() - shape.getWidth()) / 2);
        shape.setTop((builder.getPageSetup().getPageHeight() - shape.getHeight()) / 2);

        doc.save(getArtifactsDir() + "DocumentBuilder.InsertWatermark.doc");
        //ExEnd
    }

    @Test
    public void insertHtml() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder
        //ExFor:DocumentBuilder.InsertHtml(String)
        //ExId:DocumentBuilderInsertHtml
        //ExSummary:Inserts HTML into a document. The formatting specified in the HTML is applied.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.insertHtml("<P align='right'>Paragraph right</P>" + "<b>Implicit paragraph left</b>" + "<div align='center'>Div center</div>" + "<h1 align='left'>Heading 1 left.</h1>");

        doc.save(getArtifactsDir() + "DocumentBuilder.InsertHtml.doc");
        //ExEnd
    }

    @Test
    public void insertHtmlWithCurrentDocumentFormatting() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.InsertHtml(String, Boolean)
        //ExSummary:Inserts HTML into a document using. The current document formatting at the insertion position is applied to the inserted text. 
        Document doc = new Document();

        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.insertHtml(
                "<P align='right'>Paragraph right</P>" + "<b>Implicit paragraph left</b>"
                        + "<div align='center'>Div center</div>" + "<h1 align='left'>Heading 1 left.</h1>", true);

        doc.save(getArtifactsDir() + "DocumentBuilder.InsertHtml.doc");
        //ExEnd
    }

    @Test
    public void insertMathMl() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.InsertHtml(String)
        //ExSummary:Inserts MathMl into a document using.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        final String mathML =
                "<math xmlns=\"http://www.w3.org/1998/Math/MathML\"><mrow><msub><mi>a</mi><mrow><mn>1</mn></mrow></msub><mo>+</mo><msub><mi>b</mi><mrow><mn>1</mn></mrow></msub></mrow></math>";

        builder.insertHtml(mathML);
        //ExEnd

        doc.save(getArtifactsDir() + "MathML.docx");
        doc.save(getArtifactsDir() + "MathML.pdf");

        Assert.assertTrue(DocumentHelper.compareDocs(getArtifactsDir() + "MathML.docx", getGoldsDir() + "MathML Gold.docx"));
    }

    @Test
    public void insertTextAndBookmark() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder
        //ExFor:DocumentBuilder.StartBookmark
        //ExFor:DocumentBuilder.EndBookmark
        //ExSummary:Adds some text into the document and encloses the text in a bookmark using DocumentBuilder.
        DocumentBuilder builder = new DocumentBuilder();

        builder.startBookmark("MyBookmark");
        builder.writeln("Text inside a bookmark.");
        builder.endBookmark("MyBookmark");
        //ExEnd
    }

    @Test
    public void createForm() throws Exception {
        //ExStart
        //ExFor:TextFormFieldType
        //ExFor:DocumentBuilder.InsertTextInput
        //ExFor:DocumentBuilder.InsertComboBox
        //ExSummary:Builds a sample form to fill.
        DocumentBuilder builder = new DocumentBuilder();

        // Insert a text form field for input a name.
        builder.insertTextInput("", TextFormFieldType.REGULAR, "", "Enter your name here", 30);

        // Insert two blank lines.
        builder.writeln("");
        builder.writeln("");

        String[] items = new String[]{"-- Select your favorite footwear --", "Sneakers", "Oxfords", "Flip-flops", "Other", "I prefer to be barefoot"};

        // Insert a combo box to select a footwear type.
        builder.insertComboBox("", items, 0);

        // Insert two blank lines.
        builder.writeln("");
        builder.writeln("");

        builder.getDocument().save(getArtifactsDir() + "DocumentBuilder.CreateForm.doc");
        //ExEnd
    }

    @Test(enabled = false, description = "WORDSNET-16190")
    public void insertCheckBox() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.InsertCheckBox(string, bool, bool, int)
        //ExFor:DocumentBuilder.InsertCheckBox(String, bool, int)
        //ExSummary:Shows how to insert checkboxes to the document
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.insertCheckBox("", false, false, 0);
        builder.insertCheckBox("CheckBox_Default", true, true, 50);
        builder.insertCheckBox("CheckBox_OnlyCheckedValue", true, 100);
        //ExEnd

        ByteArrayOutputStream dstStream = new ByteArrayOutputStream();
        doc.save(dstStream, SaveFormat.DOCX);

        //Get checkboxes from the document
        FormFieldCollection formFields = doc.getRange().getFormFields();

        //Check that is the right checkbox
        Assert.assertEquals(formFields.get(0).getName(), "");

        //Assert that parameters sets correctly
        Assert.assertEquals(formFields.get(0).getChecked(), false);
        Assert.assertEquals(formFields.get(0).getDefault(), false);
        Assert.assertEquals(formFields.get(0).getCheckBoxSize(), 10);

        //Check that is the right checkbox
        // Please pay attention that MS Word allows strings with at most 20 characters
        Assert.assertEquals(formFields.get(1).getName(), "CheckBox_Default");

        //Assert that parameters sets correctly
        Assert.assertEquals(true, formFields.get(1).getChecked());
        Assert.assertEquals(true, formFields.get(1).getDefault());
        Assert.assertEquals(50, formFields.get(1).getCheckBoxSize());

        //Check that is the right checkbox
        // Please pay attention that MS Word allows strings with at most 20 characters
        Assert.assertEquals(formFields.get(2).getName(), "CheckBox_OnlyCheckedValue");

        //Assert that parameters sets correctly
        Assert.assertEquals(formFields.get(2).getChecked(), true);
        Assert.assertEquals(formFields.get(2).getDefault(), true);
        Assert.assertEquals(formFields.get(2).getCheckBoxSize(), 100);
    }

    @Test
    public void insertCheckBoxEmptyName() throws Exception {
        Document doc = new Document();

        DocumentBuilder builder = new DocumentBuilder(doc);

        //Assert that empty String name working correctly
        builder.insertCheckBox("", true, false, 1);
        builder.insertCheckBox("", false, 1);
    }

    @Test
    public void workingWithNodes() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.MoveTo(Node)
        //ExFor:DocumentBuilder.MoveToBookmark(String)
        //ExFor:DocumentBuilder.CurrentParagraph
        //ExFor:DocumentBuilder.CurrentNode
        //ExFor:DocumentBuilder.MoveToDocumentStart
        //ExFor:DocumentBuilder.MoveToDocumentEnd
        //ExFor:DocumentBuilder.IsAtEndOfParagraph
        //ExFor:DocumentBuilder.IsAtStartOfParagraph
        //ExSummary:Shows how to move between nodes and manipulate current ones.
        Document doc = new Document(getMyDir() + "DocumentBuilder.WorkingWithNodes.doc");
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Move to a bookmark and delete the parent paragraph.
        builder.moveToBookmark("ParaToDelete");
        builder.getCurrentParagraph().remove();

        FindReplaceOptions options = new FindReplaceOptions();
        options.setMatchCase(false);
        options.setFindWholeWordsOnly(true);

        // Move to a particular paragraph's run and replace all occurrences of "bad" with "good" within this run.
        builder.moveTo(doc.getLastSection().getBody().getParagraphs().get(0).getRuns().get(0));
        Assert.assertTrue(builder.isAtStartOfParagraph());
        Assert.assertFalse(builder.isAtEndOfParagraph());
        builder.getCurrentNode().getRange().replace("bad", "good", options);

        // Mark the beginning of the document.
        builder.moveToDocumentStart();
        builder.writeln("Start of document.");

        // builder.WriteLn puts an end to its current paragraph after writing the text and starts a new one
        Assert.assertEquals(doc.getFirstSection().getBody().getParagraphs().getCount(), 2);
        Assert.assertTrue(builder.isAtStartOfParagraph());
        Assert.assertTrue(builder.isAtEndOfParagraph());

        // builder.Write doesn't end the paragraph
        builder.write("Second paragraph.");

        Assert.assertEquals(doc.getFirstSection().getBody().getParagraphs().getCount(), 2);
        Assert.assertFalse(builder.isAtStartOfParagraph());
        Assert.assertTrue(builder.isAtEndOfParagraph());

        // Mark the ending of the document.
        builder.moveToDocumentEnd();
        builder.writeln("End of document.");

        doc.save(getArtifactsDir() + "DocumentBuilder.WorkingWithNodes.doc");
        //ExEnd
    }

    @Test
    public void fillingDocument() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.MoveToMergeField(String)
        //ExFor:DocumentBuilder.Bold
        //ExFor:DocumentBuilder.Italic
        //ExSummary:Fills document merge fields with some data.
        Document doc = new Document(getMyDir() + "DocumentBuilder.FillingDocument.doc");
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.moveToMergeField("TeamLeaderName");
        builder.setBold(true);
        builder.writeln("Roman Korchagin");

        builder.moveToMergeField("SoftwareDeveloper1Name");
        builder.setItalic(true);
        builder.writeln("Dmitry Vorobyev");

        builder.moveToMergeField("SoftwareDeveloper2Name");
        builder.setItalic(true);
        builder.writeln("Vladimir Averkin");

        doc.save(getArtifactsDir() + "DocumentBuilder.FillingDocument.doc");
        //ExEnd
    }

    @Test
    public void insertToc() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.InsertTableOfContents
        //ExFor:Document.UpdateFields
        //ExFor:DocumentBuilder.#ctor(Document)
        //ExFor:ParagraphFormat.StyleIdentifier
        //ExFor:DocumentBuilder.InsertBreak
        //ExFor:BreakType
        //ExId:InsertTableOfContents
        //ExSummary:Demonstrates how to insert a Table of contents (TOC) into a document using heading styles as entries.
        // Use a blank document
        Document doc = new Document();
        // Create a document builder to insert content with into document.
        DocumentBuilder builder = new DocumentBuilder(doc);
        // Insert a table of contents at the beginning of the document.
        builder.insertTableOfContents("\\o \"1-3\" \\h \\z \\u");
        // Start the actual document content on the second page.
        builder.insertBreak(BreakType.PAGE_BREAK);
        // Build a document with complex structure by applying different heading styles thus creating TOC entries.
        builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADING_1);

        builder.writeln("Heading 1");

        builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADING_2);

        builder.writeln("Heading 1.1");
        builder.writeln("Heading 1.2");

        builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADING_1);

        builder.writeln("Heading 2");
        builder.writeln("Heading 3");

        builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADING_2);

        builder.writeln("Heading 3.1");

        builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADING_3);

        builder.writeln("Heading 3.1.1");
        builder.writeln("Heading 3.1.2");
        builder.writeln("Heading 3.1.3");

        builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADING_2);

        builder.writeln("Heading 3.2");
        builder.writeln("Heading 3.3");

        // Call the method below to update the TOC.
        doc.updateFields();
        //ExEnd

        doc.save(getArtifactsDir() + "DocumentBuilder.InsertToc.docx");
    }

    @Test
    public void insertTable() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder
        //ExFor:DocumentBuilder.StartTable
        //ExFor:DocumentBuilder.InsertCell
        //ExFor:DocumentBuilder.EndRow
        //ExFor:DocumentBuilder.EndTable
        //ExFor:DocumentBuilder.CellFormat
        //ExFor:DocumentBuilder.RowFormat
        //ExFor:CellFormat
        //ExFor:CellFormat.FitText
        //ExFor:CellFormat.Width
        //ExFor:CellFormat.VerticalAlignment
        //ExFor:CellFormat.Shading
        //ExFor:CellFormat.Orientation
        //ExFor:CellFormat.WrapText
        //ExFor:RowFormat
        //ExFor:RowFormat.Borders
        //ExFor:RowFormat.ClearFormatting
        //ExFor:RowFormat.HeightRule
        //ExFor:RowFormat.Height
        //ExFor:HeightRule
        //ExFor:Shading.BackgroundPatternColor
        //ExFor:Shading.ClearFormatting
        //ExSummary:Shows how to build a nice bordered table.
        DocumentBuilder builder = new DocumentBuilder();

        // Start building a table.
        builder.startTable();

        // Set the appropriate paragraph, cell, and row formatting. The formatting properties are preserved
        // until they are explicitly modified so there's no need to set them for each row or cell.

        builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);

        builder.getCellFormat().clearFormatting();
        builder.getCellFormat().setWidth(300);
        builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
        builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(173, 255, 47)); //"green-yellow"
        builder.getCellFormat().setWrapText(false);
        builder.getCellFormat().setFitText(true);

        builder.getRowFormat().clearFormatting();
        builder.getRowFormat().setHeightRule(HeightRule.EXACTLY);
        builder.getRowFormat().setHeight(50);
        builder.getRowFormat().getBorders().setLineStyle(LineStyle.ENGRAVE_3_D);
        builder.getRowFormat().getBorders().setColor(new Color(255, 165, 0)); // "orange"

        builder.insertCell();
        builder.write("Row 1, Col 1");

        builder.insertCell();
        builder.write("Row 1, Col 2");

        builder.endRow();

        // Remove the shading (clear background).
        builder.getCellFormat().getShading().clearFormatting();

        builder.insertCell();
        builder.write("Row 2, Col 1");

        builder.insertCell();
        builder.write("Row 2, Col 2");

        builder.endRow();

        builder.insertCell();

        // Make the row height bigger so that a vertically oriented text could fit into cells.
        builder.getRowFormat().setHeight(150);
        builder.getCellFormat().setOrientation(TextOrientation.UPWARD);
        builder.write("Row 3, Col 1");

        builder.insertCell();
        builder.getCellFormat().setOrientation(TextOrientation.DOWNWARD);
        builder.write("Row 3, Col 2");

        builder.endRow();

        builder.endTable();

        builder.getDocument().save(getArtifactsDir() + "DocumentBuilder.InsertTable.doc");
        //ExEnd
    }

    @Test
    public void insertTableWithTableStyle() throws Exception {
        //ExStart
        //ExFor:Table.StyleIdentifier
        //ExFor:Table.StyleOptions
        //ExFor:TableStyleOptions
        //ExFor:Table.AutoFit
        //ExFor:AutoFitBehavior
        //ExId:InsertTableWithTableStyle
        //ExSummary:Shows how to build a new table with a table style applied.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Table table = builder.startTable();
        // We must insert at least one row first before setting any table formatting.
        builder.insertCell();
        // Set the table style used based of the unique style identifier.
        // Note that not all table styles are available when saving as .doc format.
        table.setStyleIdentifier(StyleIdentifier.MEDIUM_SHADING_1_ACCENT_1);
        // Apply which features should be formatted by the style.
        table.setStyleOptions(TableStyleOptions.FIRST_COLUMN | TableStyleOptions.ROW_BANDS | TableStyleOptions.FIRST_ROW);
        table.autoFit(AutoFitBehavior.AUTO_FIT_TO_CONTENTS);

        // Continue with building the table as normal.
        builder.writeln("Item");
        builder.getCellFormat().setRightPadding(40);
        builder.insertCell();
        builder.writeln("Quantity (kg)");
        builder.endRow();

        builder.insertCell();
        builder.writeln("Apples");
        builder.insertCell();
        builder.writeln("20");
        builder.endRow();

        builder.insertCell();
        builder.writeln("Bananas");
        builder.insertCell();
        builder.writeln("40");
        builder.endRow();

        builder.insertCell();
        builder.writeln("Carrots");
        builder.insertCell();
        builder.writeln("50");
        builder.endRow();

        doc.save(getArtifactsDir() + "DocumentBuilder.SetTableStyle.docx");
        //ExEnd

        // Verify that the style was set by expanding to direct formatting.
        doc.expandTableStylesToDirectFormatting();
        Assert.assertEquals(table.getStyle().getName(), "Medium Shading 1 Accent 1");
        Assert.assertEquals(table.getStyleOptions(), TableStyleOptions.FIRST_COLUMN | TableStyleOptions.ROW_BANDS | TableStyleOptions.FIRST_ROW);
        Assert.assertEquals(table.getFirstRow().getFirstCell().getCellFormat().getShading().getBackgroundPatternColor().getBlue(), 189);
        Assert.assertEquals(table.getFirstRow().getFirstCell().getFirstParagraph().getRuns().get(0).getFont().getColor().getRGB(), Color.WHITE.getRGB());
        Assert.assertNotSame(table.getLastRow().getFirstCell().getCellFormat().getShading().getBackgroundPatternColor().getBlue(), Color.BLUE.getRGB());
        Assert.assertEquals(table.getLastRow().getFirstCell().getFirstParagraph().getRuns().get(0).getFont().getColor().getRGB(), 0);
    }

    @Test
    public void insertTableSetHeadingRow() throws Exception {
        //ExStart
        //ExFor:RowFormat.HeadingFormat
        //ExId:InsertTableWithHeadingFormat
        //ExSummary:Shows how to build a table which include heading rows that repeat on subsequent pages.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Table table = builder.startTable();
        builder.getRowFormat().setHeadingFormat(true);
        builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
        builder.getCellFormat().setWidth(100);
        builder.insertCell();
        builder.writeln("Heading row 1");
        builder.endRow();
        builder.insertCell();
        builder.writeln("Heading row 2");
        builder.endRow();

        builder.getCellFormat().setWidth(50);
        builder.getParagraphFormat().clearFormatting();

        // Insert some content so the table is long enough to continue onto the next page.
        for (int i = 0; i < 50; i++) {
            builder.insertCell();
            builder.getRowFormat().setHeadingFormat(false);
            builder.write("Column 1 Text");
            builder.insertCell();
            builder.write("Column 2 Text");
            builder.endRow();
        }

        doc.save(getArtifactsDir() + "Table.HeadingRow.doc");
        //ExEnd

        Assert.assertTrue(table.getFirstRow().getRowFormat().getHeadingFormat());
        Assert.assertTrue(table.getRows().get(1).getRowFormat().getHeadingFormat());
        Assert.assertFalse(table.getRows().get(2).getRowFormat().getHeadingFormat());
    }

    @Test
    public void insertTableWithPreferredWidth() throws Exception {
        //ExStart
        //ExFor:Table.PreferredWidth
        //ExFor:PreferredWidth.FromPercent
        //ExFor:PreferredWidth
        //ExId:TablePreferredWidth
        //ExSummary:Shows how to set a table to auto fit to 50% of the page width.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Insert a table with a width that takes up half the page width.
        Table table = builder.startTable();

        // Insert a few cells
        builder.insertCell();
        table.setPreferredWidth(PreferredWidth.fromPercent(50));
        builder.writeln("Cell #1");

        builder.insertCell();
        builder.writeln("Cell #2");

        builder.insertCell();
        builder.writeln("Cell #3");

        doc.save(getArtifactsDir() + "Table.PreferredWidth.doc");
        //ExEnd

        // Verify the correct settings were applied.
        Assert.assertEquals(table.getPreferredWidth().getType(), PreferredWidthType.PERCENT);
        Assert.assertEquals(table.getPreferredWidth().getValue(), 50.0);
    }

    @Test
    public void insertCellsWithDifferentPreferredCellWidths() throws Exception {
        //ExStart
        //ExFor:CellFormat.PreferredWidth
        //ExFor:PreferredWidth
        //ExFor:PreferredWidth.Auto
        //ExFor:PreferredWidth.Equals(PreferredWidth)
        //ExFor:PreferredWidth.Equals(System.Object)
        //ExFor:PreferredWidth.FromPoints
        //ExFor:PreferredWidth.FromPercent
        //ExFor:PreferredWidth.GetHashCode
        //ExFor:PreferredWidth.ToString
        //ExId:CellPreferredWidths
        //ExSummary:Shows how to set the different preferred width settings.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Insert a table row made up of three cells which have different preferred widths.
        Table table = builder.startTable();

        // Insert an absolute sized cell.
        builder.insertCell();
        builder.getCellFormat().setPreferredWidth(PreferredWidth.fromPoints(40));
        builder.getCellFormat().getShading().setBackgroundPatternColor(Color.RED);
        builder.writeln("Cell at 40 points width");

        PreferredWidth width = builder.getCellFormat().getPreferredWidth();
        System.out.println(MessageFormat.format("Width \"{0}\": {1}", width.hashCode(), width.toString()));

        // Insert a relative (percent) sized cell.
        builder.insertCell();
        builder.getCellFormat().setPreferredWidth(PreferredWidth.fromPercent(20));
        builder.getCellFormat().getShading().setBackgroundPatternColor(Color.BLUE);
        builder.writeln("Cell at 20% width");

        // Each cell had its own PreferredWidth
        Assert.assertFalse(builder.getCellFormat().getPreferredWidth().equals(width));

        width = builder.getCellFormat().getPreferredWidth();
        System.out.println(MessageFormat.format("Width \"{0}\": {1}", width.hashCode(), width.toString()));

        // Insert a auto sized cell.
        builder.insertCell();
        builder.getCellFormat().setPreferredWidth(PreferredWidth.AUTO);
        builder.getCellFormat().getShading().setBackgroundPatternColor(Color.GREEN);
        builder.writeln("Cell automatically sized. The size of this cell is calculated from the table preferred width.");
        builder.writeln("In this case the cell will fill up the rest of the available space.");

        doc.save(getArtifactsDir() + "Table.CellPreferredWidths.docx");
        //ExEnd

        // Verify the correct settings were applied.
        Assert.assertEquals(table.getFirstRow().getFirstCell().getCellFormat().getPreferredWidth().getType(), PreferredWidthType.POINTS);
        Assert.assertEquals(table.getFirstRow().getCells().get(1).getCellFormat().getPreferredWidth().getType(), PreferredWidthType.PERCENT);
        Assert.assertEquals(table.getFirstRow().getCells().get(2).getCellFormat().getPreferredWidth().getType(), PreferredWidthType.AUTO);
    }

    @Test
    public void insertTableFromHtml() throws Exception {
        //ExStart
        //ExId:InsertTableFromHtml
        //ExSummary:Shows how to insert a table in a document from a string containing HTML tags.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Insert the table from HTML. Note that AutoFitSettings does not apply to tables
        // inserted from HTML.
        builder.insertHtml("<table>" + "<tr>" + "<td>Row 1, Cell 1</td>" + "<td>Row 1, Cell 2</td>" + "</tr>" + "<tr>" + "<td>Row 2, Cell 2</td>" + "<td>Row 2, Cell 2</td>" + "</tr>" + "</table>");

        doc.save(getArtifactsDir() + "DocumentBuilder.InsertTableFromHtml.doc");
        //ExEnd

        // Verify the table was constructed properly.
        Assert.assertEquals(doc.getChildNodes(NodeType.TABLE, true).getCount(), 1);
        Assert.assertEquals(doc.getChildNodes(NodeType.ROW, true).getCount(), 2);
        Assert.assertEquals(doc.getChildNodes(NodeType.CELL, true).getCount(), 4);
    }

    @Test
    public void buildNestedTableUsingDocumentBuilder() throws Exception {
        //ExStart
        //ExFor:Cell.FirstParagraph
        //ExId:BuildNestedTableUsingDocumentBuilder
        //ExSummary:Shows how to insert a nested table using DocumentBuilder.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Build the outer table.
        Cell cell = builder.insertCell();
        builder.writeln("Outer Table Cell 1");

        builder.insertCell();
        builder.writeln("Outer Table Cell 2");

        // This call is important in order to create a nested table within the first table
        // Without this call the cells inserted below will be appended to the outer table.
        builder.endTable();

        // Move to the first cell of the outer table.
        builder.moveTo(cell.getFirstParagraph());

        // Build the inner table.
        builder.insertCell();
        builder.writeln("Inner Table Cell 1");
        builder.insertCell();
        builder.writeln("Inner Table Cell 2");

        builder.endTable();

        doc.save(getArtifactsDir() + "DocumentBuilder.InsertNestedTable.doc");
        //ExEnd

        Assert.assertEquals(doc.getChildNodes(NodeType.TABLE, true).getCount(), 2);
        Assert.assertEquals(doc.getChildNodes(NodeType.CELL, true).getCount(), 4);
        Assert.assertEquals(cell.getTables().get(0).getCount(), 1);
        Assert.assertEquals(cell.getTables().get(0).getFirstRow().getCells().getCount(), 2);
    }

    @Test
    public void buildSimpleTable() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder
        //ExFor:DocumentBuilder.Write
        //ExFor:DocumentBuilder.InsertCell
        //ExId:BuildSimpleTable
        //ExSummary:Shows how to create a simple table using DocumentBuilder with default formatting.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // We call this method to start building the table.
        builder.startTable();
        builder.insertCell();
        builder.write("Row 1, Cell 1 Content.");

        // Build the second cell
        builder.insertCell();
        builder.write("Row 1, Cell 2 Content.");
        // Call the following method to end the row and start a new row.
        builder.endRow();

        // Build the first cell of the second row.
        builder.insertCell();
        builder.write("Row 2, Cell 1 Content");

        // Build the second cell.
        builder.insertCell();
        builder.write("Row 2, Cell 2 Content.");
        builder.endRow();

        // Signal that we have finished building the table.
        builder.endTable();

        // Save the document to disk.
        doc.save(getArtifactsDir() + "DocumentBuilder.CreateSimpleTable.doc");
        //ExEnd

        // Verify that the cell count of the table is four.
        Table table = (Table) doc.getChild(NodeType.TABLE, 0, true);
        Assert.assertNotNull(table);
        Assert.assertEquals(table.getChildNodes(NodeType.CELL, true).getCount(), 4);
    }

    @Test
    public void buildFormattedTable() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder
        //ExFor:DocumentBuilder.Write
        //ExFor:DocumentBuilder.InsertCell
        //ExFor:RowFormat.Height
        //ExFor:RowFormat.HeightRule
        //ExFor:Table.LeftIndent
        //ExFor:Shading.BackgroundPatternColor
        //ExFor:DocumentBuilder.ParagraphFormat
        //ExFor:DocumentBuilder.Font
        //ExId:BuildFormattedTable
        //ExSummary:Shows how to create a formatted table using DocumentBuilder
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Table table = builder.startTable();

        // Make the header row.
        builder.insertCell();

        // Set the left indent for the table. Table wide formatting must be applied after
        // at least one row is present in the table.
        table.setLeftIndent(20.0);

        // Set height and define the height rule for the header row.
        builder.getRowFormat().setHeight(40.0);
        builder.getRowFormat().setHeightRule(HeightRule.AT_LEAST);

        // Some special features for the header row.
        builder.getCellFormat().getShading().setBackgroundPatternColor(new Color(198, 217, 241));
        builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
        builder.getFont().setSize(16);
        builder.getFont().setName("Arial");
        builder.getFont().setBold(true);

        builder.getCellFormat().setWidth(100.0);
        builder.write("Header Row,\n Cell 1");

        // We don't need to specify the width of this cell because it's inherited from the previous cell.
        builder.insertCell();
        builder.write("Header Row,\n Cell 2");

        builder.insertCell();
        builder.getCellFormat().setWidth(200.0);
        builder.write("Header Row,\n Cell 3");
        builder.endRow();

        // Set features for the other rows and cells.
        builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
        builder.getCellFormat().setWidth(100.0);
        builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);

        // Reset height and define a different height rule for table body
        builder.getRowFormat().setHeight(30.0);
        builder.getRowFormat().setHeightRule(HeightRule.AUTO);
        builder.insertCell();
        // Reset font formatting.
        builder.getFont().setSize(12);
        builder.getFont().setBold(false);

        // Build the other cells.
        builder.write("Row 1, Cell 1 Content");
        builder.insertCell();
        builder.write("Row 1, Cell 2 Content");

        builder.insertCell();
        builder.getCellFormat().setWidth(200.0);
        builder.write("Row 1, Cell 3 Content");
        builder.endRow();

        builder.insertCell();
        builder.getCellFormat().setWidth(100.0);
        builder.write("Row 2, Cell 1 Content");

        builder.insertCell();
        builder.write("Row 2, Cell 2 Content");

        builder.insertCell();
        builder.getCellFormat().setWidth(200.0);
        builder.write("Row 2, Cell 3 Content.");
        builder.endRow();
        builder.endTable();

        doc.save(getArtifactsDir() + "DocumentBuilder.CreateFormattedTable.doc");
        //ExEnd

        // Verify that the cell style is different compared to default.
        Assert.assertNotSame(table.getLeftIndent(), 0.0);
        Assert.assertNotSame(table.getFirstRow().getRowFormat().getHeightRule(), HeightRule.AUTO);
        Assert.assertNotSame(table.getFirstRow().getFirstCell().getCellFormat().getShading().getBackgroundPatternColor().getRGB(), 0);
        Assert.assertNotSame(table.getFirstRow().getFirstCell().getFirstParagraph().getParagraphFormat().getAlignment(), ParagraphAlignment.LEFT);
    }

    @Test
    public void setCellShadingAndBorders() throws Exception {
        //ExStart
        //ExFor:Shading
        //ExFor:Shading.BackgroundPatternColor
        //ExFor:Table.SetBorders
        //ExFor:BorderCollection.Left
        //ExFor:BorderCollection.Right
        //ExFor:BorderCollection.Top
        //ExFor:BorderCollection.Bottom
        //ExId:TableBordersAndShading
        //ExSummary:Shows how to format table and cell with different borders and shadings
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Table table = builder.startTable();
        builder.insertCell();

        // Set the borders for the entire table.
        table.setBorders(LineStyle.SINGLE, 2.0, Color.BLACK);
        // Set the cell shading for this cell.
        builder.getCellFormat().getShading().setBackgroundPatternColor(Color.RED);
        builder.writeln("Cell #1");

        builder.insertCell();
        // Specify a different cell shading for the second cell.
        builder.getCellFormat().getShading().setBackgroundPatternColor(Color.GREEN);
        builder.writeln("Cell #2");

        // End this row.
        builder.endRow();

        // Clear the cell formatting from previous operations.
        builder.getCellFormat().clearFormatting();

        // Create the second row.
        builder.insertCell();

        // Create larger borders for the first cell of this row. This will be different
        // compared to the borders set for the table.
        builder.getCellFormat().getBorders().getLeft().setLineWidth(4.0);
        builder.getCellFormat().getBorders().getRight().setLineWidth(4.0);
        builder.getCellFormat().getBorders().getTop().setLineWidth(4.0);
        builder.getCellFormat().getBorders().getBottom().setLineWidth(4.0);
        builder.writeln("Cell #3");

        builder.insertCell();
        // Clear the cell formatting from the previous cell.
        builder.getCellFormat().clearFormatting();
        builder.writeln("Cell #4");

        doc.save(getArtifactsDir() + "Table.SetBordersAndShading.doc");
        //ExEnd

        // Verify the table was created correctly.
        Assert.assertEquals(table.getFirstRow().getFirstCell().getCellFormat().getShading().getBackgroundPatternColor().getRGB(), Color.RED.getRGB());
        Assert.assertEquals(table.getFirstRow().getCells().get(1).getCellFormat().getShading().getBackgroundPatternColor().getRGB(), Color.GREEN.getRGB());
        Assert.assertEquals(table.getFirstRow().getCells().get(1).getCellFormat().getShading().getBackgroundPatternColor().getRGB(), Color.GREEN.getRGB());
        Assert.assertEquals(table.getLastRow().getFirstCell().getCellFormat().getShading().getBackgroundPatternColor().getRGB(), 0);

        Assert.assertEquals(table.getFirstRow().getFirstCell().getCellFormat().getBorders().getLeft().getColor().getRGB(), Color.BLACK.getRGB());
        Assert.assertEquals(table.getFirstRow().getFirstCell().getCellFormat().getBorders().getLeft().getColor().getRGB(), Color.BLACK.getRGB());
        Assert.assertEquals(table.getFirstRow().getFirstCell().getCellFormat().getBorders().getLeft().getLineStyle(), LineStyle.SINGLE);
        Assert.assertEquals(table.getFirstRow().getFirstCell().getCellFormat().getBorders().getLeft().getLineWidth(), 2.0);
        Assert.assertEquals(table.getLastRow().getFirstCell().getCellFormat().getBorders().getLeft().getLineWidth(), 4.0);
    }

    @Test
    public void SetPreferredTypeConvertUtil() throws Exception {
        //ExStart
        //ExFor:PreferredWidth.FromPoints
        //ExSummary:Shows how to specify a cell preferred width by converting inches to points.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Table table = builder.startTable();
        builder.getCellFormat().setPreferredWidth(PreferredWidth.fromPoints(ConvertUtil.inchToPoint(3)));
        builder.insertCell();
        //ExEnd

        Assert.assertEquals(table.getFirstRow().getFirstCell().getCellFormat().getPreferredWidth().getValue(), 216.0);
    }

    @Test
    public void insertHyperlinkToLocalBookmark() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.StartBookmark
        //ExFor:DocumentBuilder.EndBookmark
        //ExFor:DocumentBuilder.InsertHyperlink
        //ExSummary:Inserts a hyperlink referencing local bookmark.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.startBookmark("Bookmark1");
        builder.write("Bookmarked text.");
        builder.endBookmark("Bookmark1");

        builder.writeln("Some other text");

        // Specify font formatting for the hyperlink.
        builder.getFont().setColor(Color.BLUE);
        builder.getFont().setUnderline(Underline.SINGLE);

        // Insert hyperlink.
        // Switch \o is used to provide hyperlink tip text.
        builder.insertHyperlink("ApiExamples.Tests.Hyperlink Text", "Bookmark1\" \\o \"ApiExamples.Tests.Hyperlink Tip", true);

        // Clear hyperlink formatting.
        builder.getFont().clearFormatting();

        doc.save(getArtifactsDir() + "DocumentBuilder.InsertHyperlinkToLocalBookmark.doc");
        //ExEnd
    }

    @Test
    public void documentBuilderCtor() throws Exception {
        //ExStart
        //ExId:DocumentBuilderCtor
        //ExSummary:Shows how to create a simple document using a document builder.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.write("Hello World!");
        //ExEnd
    }

    @Test
    public void documentBuilderCursorPosition() throws Exception {
        //ExStart
        //ExId:DocumentBuilderCursorPosition
        //ExSummary:Shows how to access the current node in a document builder.
        Document doc = new Document(getMyDir() + "DocumentBuilder.doc");
        DocumentBuilder builder = new DocumentBuilder(doc);

        Node curNode = builder.getCurrentNode();
        Paragraph curParagraph = builder.getCurrentParagraph();
        //ExEnd
    }

    @Test
    public void documentBuilderMoveToNode() throws Exception {
        //ExStart
        //ExFor:Story.LastParagraph
        //ExFor:DocumentBuilder.MoveTo(Node)
        //ExId:DocumentBuilderMoveToNode
        //ExSummary:Shows how to move a cursor position to a specified node.
        Document doc = new Document(getMyDir() + "DocumentBuilder.doc");
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.moveTo(doc.getFirstSection().getBody().getLastParagraph());
        //ExEnd
    }

    @Test
    public void documentBuilderMoveToDocumentStartEnd() throws Exception {
        //ExStart
        //ExId:DocumentBuilderMoveToDocumentStartEnd
        //ExSummary:Shows how to move a cursor position to the beginning or end of a document.
        Document doc = new Document(getMyDir() + "DocumentBuilder.doc");
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.moveToDocumentEnd();
        builder.writeln("This is the end of the document.");

        builder.moveToDocumentStart();
        builder.writeln("This is the beginning of the document.");
        //ExEnd
    }

    @Test
    public void documentBuilderMoveToSection() throws Exception {
        //ExStart
        //ExId:DocumentBuilderMoveToSection
        //ExSummary:Shows how to move a cursor position to the specified section.
        Document doc = new Document(getMyDir() + "DocumentBuilder.doc");
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Parameters are 0-index. Moves to third section.
        builder.moveToSection(2);
        builder.writeln("This is the 3rd section.");
        //ExEnd
    }

    @Test
    public void documentBuilderMoveToParagraph() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.MoveToParagraph
        //ExId:DocumentBuilderMoveToParagraph
        //ExSummary:Shows how to move a cursor position to the specified paragraph.
        Document doc = new Document(getMyDir() + "DocumentBuilder.doc");
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Parameters are 0-index. Moves to third paragraph.
        builder.moveToParagraph(2, 0);
        builder.writeln("This is the 3rd paragraph.");
        //ExEnd
    }

    @Test
    public void documentBuilderMoveToTableCell() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.MoveToCell
        //ExId:DocumentBuilderMoveToTableCell
        //ExSummary:Shows how to move a cursor position to the specified table cell.
        Document doc = new Document(getMyDir() + "DocumentBuilder.doc");
        DocumentBuilder builder = new DocumentBuilder(doc);

        // All parameters are 0-index. Moves to the 2nd table, 3rd row, 5th cell.
        builder.moveToCell(1, 2, 4, 0);
        builder.writeln("Hello World!");
        //ExEnd
    }

    @Test
    public void documentBuilderMoveToBookmark() throws Exception {
        //ExStart
        //ExId:DocumentBuilderMoveToBookmark
        //ExSummary:Shows how to move a cursor position to a bookmark.
        Document doc = new Document(getMyDir() + "DocumentBuilder.doc");
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.moveToBookmark("CoolBookmark");
        builder.writeln("This is a very cool bookmark.");
        //ExEnd
    }

    @Test
    public void documentBuilderMoveToBookmarkEnd() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.MoveToBookmark(String, Boolean, Boolean)
        //ExId:DocumentBuilderMoveToBookmarkEnd
        //ExSummary:Shows how to move a cursor position to just after the bookmark end.
        Document doc = new Document(getMyDir() + "DocumentBuilder.doc");
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.moveToBookmark("CoolBookmark", false, true);
        builder.writeln("This is a very cool bookmark.");
        //ExEnd
    }

    @Test
    public void documentBuilderMoveToMergeField() throws Exception {
        //ExStart
        //ExId:DocumentBuilderMoveToMergeField
        //ExSummary:Shows how to move the cursor to a position just beyond the specified merge field.
        Document doc = new Document(getMyDir() + "DocumentBuilder.doc");
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.moveToMergeField("NiceMergeField");
        builder.writeln("This is a very nice merge field.");
        //ExEnd
    }

    @Test
    public void documentBuilderInsertParagraph() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.InsertParagraph
        //ExFor:ParagraphFormat.FirstLineIndent
        //ExFor:ParagraphFormat.Alignment
        //ExFor:ParagraphFormat.KeepTogether
        //ExFor:ParagraphFormat.AddSpaceBetweenFarEastAndAlpha
        //ExFor:ParagraphFormat.AddSpaceBetweenFarEastAndDigit
        //ExFor:Paragraph.IsEndOfDocument
        //ExId:DocumentBuilderInsertParagraph
        //ExSummary:Shows how to insert a paragraph into the document.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Specify font formatting
        Font font = builder.getFont();
        font.setSize(16);
        font.setBold(true);
        font.setColor(Color.BLUE);
        font.setName("Arial");
        font.setUnderline(Underline.DASH);

        // Specify paragraph formatting
        ParagraphFormat paragraphFormat = builder.getParagraphFormat();
        paragraphFormat.setFirstLineIndent(8);
        paragraphFormat.setAlignment(ParagraphAlignment.JUSTIFY);
        paragraphFormat.setAddSpaceBetweenFarEastAndAlpha(true);
        paragraphFormat.setAddSpaceBetweenFarEastAndDigit(true);
        paragraphFormat.setKeepTogether(true);

        builder.writeln("A whole paragraph.");

        // We can use this flag to ensure that we're at the end of the document
        Assert.assertTrue(builder.getCurrentParagraph().isEndOfDocument());
        //ExEnd
    }

    @Test
    public void documentBuilderBuildTable() throws Exception {
        //ExStart
        //ExFor:Table
        //ExFor:DocumentBuilder.StartTable
        //ExFor:DocumentBuilder.InsertCell
        //ExFor:DocumentBuilder.EndRow
        //ExFor:DocumentBuilder.EndTable
        //ExFor:DocumentBuilder.CellFormat
        //ExFor:DocumentBuilder.RowFormat
        //ExFor:DocumentBuilder.Write
        //ExFor:DocumentBuilder.Writeln(String)
        //ExFor:RowFormat.Height
        //ExFor:RowFormat.HeightRule
        //ExFor:CellVerticalAlignment
        //ExFor:CellFormat.Orientation
        //ExFor:TextOrientation
        //ExFor:Table.AutoFit
        //ExFor:AutoFitBehavior
        //ExId:DocumentBuilderBuildTable
        //ExSummary:Shows how to build a formatted table that contains 2 rows and 2 columns.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Table table = builder.startTable();

        // Insert a cell
        builder.insertCell();
        // Use fixed column widths.
        table.autoFit(AutoFitBehavior.FIXED_COLUMN_WIDTHS);

        builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
        builder.write("This is row 1 cell 1");

        // Insert a cell
        builder.insertCell();
        builder.write("This is row 1 cell 2");

        builder.endRow();

        // Insert a cell
        builder.insertCell();

        // Apply new row formatting
        builder.getRowFormat().setHeight(100);
        builder.getRowFormat().setHeightRule(HeightRule.EXACTLY);

        builder.getCellFormat().setOrientation(TextOrientation.UPWARD);
        builder.writeln("This is row 2 cell 1");

        // Insert a cell
        builder.insertCell();
        builder.getCellFormat().setOrientation(TextOrientation.DOWNWARD);
        builder.writeln("This is row 2 cell 2");

        builder.endRow();

        builder.endTable();
        //ExEnd
    }

    @Test
    public void tableCellVerticalRotatedFarEastTextOrientation() throws Exception {
        Document doc = new Document(getMyDir() + "DocumentBuilder.TableCellVerticalRotatedFarEastTextOrientation.docx");

        Table table = (Table) doc.getChild(NodeType.TABLE, 0, true);
        Cell cell = table.getFirstRow().getFirstCell();

        Assert.assertEquals(cell.getCellFormat().getOrientation(), TextOrientation.VERTICAL_ROTATED_FAR_EAST);

        ByteArrayOutputStream dstStream = new ByteArrayOutputStream();
        doc.save(dstStream, SaveFormat.DOCX);

        table = (Table) doc.getChild(NodeType.TABLE, 0, true);
        cell = table.getFirstRow().getFirstCell();

        Assert.assertEquals(cell.getCellFormat().getOrientation(), TextOrientation.VERTICAL_ROTATED_FAR_EAST);
    }

    @Test
    public void documentBuilderInsertBreak() throws Exception {
        //ExStart
        //ExId:DocumentBuilderInsertBreak
        //ExSummary:Shows how to insert page breaks into a document.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.writeln("This is page 1.");
        builder.insertBreak(BreakType.PAGE_BREAK);

        builder.writeln("This is page 2.");
        builder.insertBreak(BreakType.PAGE_BREAK);

        builder.writeln("This is page 3.");
        //ExEnd
    }

    @Test
    public void documentBuilderInsertInlineImage() throws Exception {
        //ExStart
        //ExId:DocumentBuilderInsertInlineImage
        //ExSummary:Shows how to insert an inline image at the cursor position into a document.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.insertImage(getImageDir() + "Watermark.png");
        //ExEnd
    }

    @Test
    public void documentBuilderInsertFloatingImage() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.InsertImage(String, RelativeHorizontalPosition, Double, RelativeVerticalPosition, Double, Double, Double, WrapType)
        //ExId:DocumentBuilderInsertFloatingImage
        //ExSummary:Shows how to insert a floating image from a file or URL.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.insertImage(getImageDir() + "Watermark.png", RelativeHorizontalPosition.MARGIN, 100.0, RelativeVerticalPosition.MARGIN, 100.0, 200.0, 100.0, WrapType.SQUARE);
        //ExEnd
    }

    @Test
    public void insertImageFromUrl() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.InsertImage(String)
        //ExSummary:Shows how to insert an image into a document from a web address.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.insertImage(getAsposelogoUri().toURL().openStream());

        doc.save(getArtifactsDir() + "DocumentBuilder.InsertImageFromUrl.doc");
        //ExEnd

        // Verify that the image was inserted into the document.
        Shape shape = (Shape) doc.getChild(NodeType.SHAPE, 0, true);
        Assert.assertNotNull(shape);
        Assert.assertTrue(shape.hasImage());
    }

    @Test
    public void documentBuilderInsertImageSourceSize() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.InsertImage(String, RelativeHorizontalPosition, Double, RelativeVerticalPosition, Double, Double, Double, WrapType)
        //ExId:DocumentBuilderInsertFloatingImageSourceSize
        //ExSummary:Shows how to insert a floating image from a file or URL and retain the original image size in the document.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Pass a negative value to the width and height values to specify using the size of the source image.
        builder.insertImage(getImageDir() + "LogoSmall.png", RelativeHorizontalPosition.MARGIN, 200.0, RelativeVerticalPosition.MARGIN, 100.0, -1, -1, WrapType.SQUARE);
        //ExEnd

        doc.save(getArtifactsDir() + "DocumentBuilder.InsertImageOriginalSize.doc");
    }

    @Test
    public void documentBuilderInsertBookmark() throws Exception {
        //ExStart
        //ExId:DocumentBuilderInsertBookmark
        //ExSummary:Shows how to insert a bookmark into a document using a document builder.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.startBookmark("FineBookmark");
        builder.writeln("This is just a fine bookmark.");
        builder.endBookmark("FineBookmark");
        //ExEnd
    }

    @Test
    public void documentBuilderInsertTextInputFormField() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.InsertTextInput
        //ExId:DocumentBuilderInsertTextInputFormField
        //ExSummary:Shows how to insert a text input form field into a document.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.insertTextInput("TextInput", TextFormFieldType.REGULAR, "", "Hello", 0);
        //ExEnd
    }

    @Test
    public void documentBuilderInsertComboBoxFormField() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.InsertComboBox
        //ExId:DocumentBuilderInsertComboBoxFormField
        //ExSummary:Shows how to insert a combobox form field into a document.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        String[] items = {"One", "Two", "Three"};
        builder.insertComboBox("DropDown", items, 0);
        //ExEnd
    }

    @Test
    public void documentBuilderInsertTOC() throws Exception {
        //ExStart
        //ExId:DocumentBuilderInsertTOC
        //ExSummary:Shows how to insert a Table of Contents field into a document.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Insert a table of contents at the beginning of the document.
        builder.insertTableOfContents("\\o \"1-3\" \\h \\z \\u");

        // The newly inserted table of contents will be initially empty.
        // It needs to be populated by updating the fields in the document.
        doc.updateFields();
        //ExEnd
    }

    @Test(description = "WORDSNET-16868")
    public void createAndSignSignatureLineUsingProviderId() throws Exception {
        //ExStart
        //ExFor:SignatureLine.ProviderId
        //ExFor:SignatureLineOptions.ShowDate
        //ExFor:SignatureLineOptions.Email
        //ExFor:SignatureLineOptions.DefaultInstructions
        //ExFor:SignatureLineOptions.Instructions
        //ExFor:SignatureLineOptions.AllowComments
        //ExFor:DocumentBuilder.InsertSignatureLine(SignatureLineOptions)
        //ExFor:SignOptions.ProviderId
        //ExSummary:Shows how to sign document with personal certificate and specific signatire line.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        SignatureLineOptions signatureLineOptions = new SignatureLineOptions();

        signatureLineOptions.setSigner("vderyushev");
        signatureLineOptions.setSignerTitle("QA");
        signatureLineOptions.setEmail("vderyushev@aspose.com");
        signatureLineOptions.setShowDate(true);
        signatureLineOptions.setDefaultInstructions(false);
        signatureLineOptions.setInstructions("You need more info about signature line");
        signatureLineOptions.setAllowComments(true);

        SignatureLine signatureLine = builder.insertSignatureLine(signatureLineOptions).getSignatureLine();
        signatureLine.setProviderId(UUID.fromString("CF5A7BB4-8F3C-4756-9DF6-BEF7F13259A2"));

        doc.save(getArtifactsDir() + "DocumentBuilder.SignatureLineProviderId In.docx");

        SignOptions signOptions = new SignOptions();
        signOptions.setSignatureLineId(signatureLine.getId());
        signOptions.setProviderId(signatureLine.getProviderId());
        signOptions.setComments("Document was signed by vderyushev");
        signOptions.setSignTime(new Date());

        CertificateHolder certHolder = CertificateHolder.create(getMyDir() + "morzal.pfx", "aw");

        DigitalSignatureUtil.sign(getArtifactsDir() + "DocumentBuilder.SignatureLineProviderId In.docx", getArtifactsDir() + "DocumentBuilder.SignatureLineProviderId Out.docx", certHolder, signOptions);
        //ExEnd

        Assert.assertTrue(DocumentHelper.compareDocs(getArtifactsDir() + "DocumentBuilder.SignatureLineProviderId Out.docx", getGoldsDir() + "DocumentBuilder.SignatureLineProviderId Gold.docx"));
    }

    @Test
    public void insertSignatureLineCurrentPozition() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.InsertSignatureLine(SignatureLineOptions, RelativeHorizontalPosition, Double, RelativeVerticalPosition, Double, WrapType)
        //ExSummary:Shows how to insert signature line at the specified position.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        SignatureLineOptions options = new SignatureLineOptions();
        options.setSigner("John Doe");
        options.setSignerTitle("Manager");
        options.setEmail("johndoe@aspose.com");
        options.setShowDate(true);
        options.setDefaultInstructions(false);
        options.setInstructions("You need more info about signature line");
        options.setAllowComments(true);

        builder.insertSignatureLine(options, RelativeHorizontalPosition.RIGHT_MARGIN, 2.0, RelativeVerticalPosition.PAGE, 3.0, WrapType.INLINE);
        //ExEnd

        ByteArrayOutputStream dstStream = new ByteArrayOutputStream();
        doc.save(dstStream, SaveFormat.DOCX);

        Shape shape = (Shape) doc.getChild(NodeType.SHAPE, 0, true);

        SignatureLine signatureLine = shape.getSignatureLine();

        Assert.assertEquals(signatureLine.getSigner(), "John Doe");
        Assert.assertEquals(signatureLine.getSignerTitle(), "Manager");
        Assert.assertEquals(signatureLine.getEmail(), "johndoe@aspose.com");
        Assert.assertEquals(signatureLine.getShowDate(), true);
        Assert.assertEquals(signatureLine.getDefaultInstructions(), false);
        Assert.assertEquals(signatureLine.getInstructions(), "You need more info about signature line");
        Assert.assertEquals(signatureLine.getAllowComments(), true);
        Assert.assertEquals(signatureLine.isSigned(), false);
        Assert.assertEquals(signatureLine.isValid(), false);
    }

    @Test
    public void documentBuilderSetFontFormatting() throws Exception {
        //ExStart
        //ExId:DocumentBuilderSetFontFormatting
        //ExSummary:Shows how to set font formatting.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Set font formatting properties
        Font font = builder.getFont();
        font.setBold(true);
        font.setColor(Color.BLUE);
        font.setItalic(true);
        font.setName("Arial");
        font.setSize(24);
        font.setSpacing(5);
        font.setUnderline(Underline.DOUBLE);

        // Output formatted text
        builder.writeln("I'm a very nice formatted string.");
        //ExEnd
    }

    @Test
    public void documentBuilderSetParagraphFormatting() throws Exception {
        //ExStart
        //ExFor:ParagraphFormat.RightIndent
        //ExFor:ParagraphFormat.LeftIndent
        //ExId:DocumentBuilderSetParagraphFormatting
        //ExSummary:Shows how to set paragraph formatting.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Set paragraph formatting properties
        ParagraphFormat paragraphFormat = builder.getParagraphFormat();
        paragraphFormat.setAlignment(ParagraphAlignment.CENTER);
        paragraphFormat.setLeftIndent(50);
        paragraphFormat.setRightIndent(50);
        paragraphFormat.setSpaceAfter(25);

        // Output text
        builder.writeln("I'm a very nice formatted paragraph. I'm intended to demonstrate how the left and right indents affect word wrapping.");
        builder.writeln("I'm another nice formatted paragraph. I'm intended to demonstrate how the space after paragraph looks like.");
        //ExEnd
    }

    @Test
    public void documentBuilderSetCellFormatting() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.CellFormat
        //ExFor:CellFormat.Width
        //ExFor:CellFormat.LeftPadding
        //ExFor:CellFormat.RightPadding
        //ExFor:CellFormat.TopPadding
        //ExFor:CellFormat.BottomPadding
        //ExFor:DocumentBuilder.StartTable
        //ExFor:DocumentBuilder.EndTable
        //ExId:DocumentBuilderSetCellFormatting
        //ExSummary:Shows how to create a table that contains a single formatted cell.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.startTable();
        builder.insertCell();

        // Set the cell formatting
        CellFormat cellFormat = builder.getCellFormat();
        cellFormat.setWidth(250);
        cellFormat.setLeftPadding(30);
        cellFormat.setRightPadding(30);
        cellFormat.setTopPadding(30);
        cellFormat.setBottomPadding(30);

        builder.writeln("I'm a wonderful formatted cell.");

        builder.endRow();
        builder.endTable();
        //ExEnd
    }

    @Test
    public void documentBuilderSetRowFormatting() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.RowFormat
        //ExFor:RowFormat.Height
        //ExFor:RowFormat.HeightRule
        //ExFor:Table.LeftPadding
        //ExFor:Table.RightPadding
        //ExFor:Table.TopPadding
        //ExFor:Table.BottomPadding
        //ExId:DocumentBuilderSetRowFormatting
        //ExSummary:Shows how to create a table that contains a single cell and apply row formatting.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Table table = builder.startTable();
        builder.insertCell();

        // Set the row formatting
        RowFormat rowFormat = builder.getRowFormat();
        rowFormat.setHeight(100);
        rowFormat.setHeightRule(HeightRule.EXACTLY);
        // These formatting properties are set on the table and are applied to all rows in the table.
        table.setLeftPadding(30);
        table.setRightPadding(30);
        table.setTopPadding(30);
        table.setBottomPadding(30);

        builder.writeln("I'm a wonderful formatted row.");

        builder.endRow();
        builder.endTable();
        //ExEnd
    }

    @Test
    public void documentBuilderSetListFormatting() throws Exception {
        //ExStart
        //ExId:DocumentBuilderSetListFormatting
        //ExSummary:Shows how to build a multilevel list.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.getListFormat().applyNumberDefault();

        builder.writeln("Item 1");
        builder.writeln("Item 2");

        builder.getListFormat().listIndent();

        builder.writeln("Item 2.1");
        builder.writeln("Item 2.2");

        builder.getListFormat().listIndent();

        builder.writeln("Item 2.2.1");
        builder.writeln("Item 2.2.2");

        builder.getListFormat().listOutdent();

        builder.writeln("Item 2.3");

        builder.getListFormat().listOutdent();

        builder.writeln("Item 3");

        builder.getListFormat().removeNumbers();
        //ExEnd
    }

    @Test
    public void documentBuilderSetSectionFormatting() throws Exception {
        //ExStart
        //ExId:DocumentBuilderSetSectionFormatting
        //ExSummary:Shows how to set such properties as page size and orientation for the current section.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Set page properties
        builder.getPageSetup().setOrientation(Orientation.LANDSCAPE);
        builder.getPageSetup().setLeftMargin(50);
        builder.getPageSetup().setPaperSize(PaperSize.PAPER_10_X_14);
        //ExEnd
    }

    @Test
    public void insertFootnote() throws Exception {
        //ExStart
        //ExFor:FootnoteType
        //ExFor:Document.FootnoteOptions
        //ExFor:DocumentBuilder.InsertFootnote(FootnoteType,String)
        //ExFor:DocumentBuilder.InsertFootnote(FootnoteType,String,String)
        //ExSummary:Shows how to add a footnote to a paragraph in the document using DocumentBuilder.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        for (int i = 0; i <= 100; i++) {
            builder.write("Some text " + i);

            builder.insertFootnote(FootnoteType.FOOTNOTE, "Footnote text " + i);
            builder.insertFootnote(FootnoteType.FOOTNOTE, "Footnote text " + i, "242");
        }
        //ExEnd

        Assert.assertEquals(doc.getChildNodes(NodeType.FOOTNOTE, true).
                get(0).toString(SaveFormat.TEXT).trim(), "Footnote text 0");

        doc.getFootnoteOptions().setNumberStyle(NumberStyle.ARABIC);
        doc.getFootnoteOptions().setStartNumber(1);
        doc.getFootnoteOptions().setRestartRule(FootnoteNumberingRule.RESTART_PAGE);

        doc.save(getArtifactsDir() + "DocumentBuilder.InsertFootnote.docx");

        Assert.assertTrue(DocumentHelper.compareDocs(getArtifactsDir() + "DocumentBuilder.InsertFootnote.docx", getGoldsDir() + "DocumentBuilder.InsertFootnote Gold.docx"));
    }

    @Test
    public void documentBuilderApplyParagraphStyle() throws Exception {
        //ExStart
        //ExId:DocumentBuilderApplyParagraphStyle
        //ExSummary:Shows how to apply a paragraph style.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Set paragraph style
        builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.TITLE);

        builder.write("Hello");
        //ExEnd
    }

    @Test
    public void documentBuilderApplyBordersAndShading() throws Exception {
        //ExStart
        //ExFor:BorderCollection.Item(BorderType)
        //ExFor:Shading
        //ExFor:TextureIndex
        //ExFor:ParagraphFormat.Shading
        //ExFor:Shading.Texture
        //ExFor:Shading.BackgroundPatternColor
        //ExFor:Shading.ForegroundPatternColor
        //ExId:DocumentBuilderApplyBordersAndShading
        //ExSummary:Shows how to apply borders and shading to a paragraph.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Set paragraph borders
        BorderCollection borders = builder.getParagraphFormat().getBorders();
        borders.setDistanceFromText(20);
        borders.getByBorderType(BorderType.LEFT).setLineStyle(LineStyle.DOUBLE);
        borders.getByBorderType(BorderType.RIGHT).setLineStyle(LineStyle.DOUBLE);
        borders.getByBorderType(BorderType.TOP).setLineStyle(LineStyle.DOUBLE);
        borders.getByBorderType(BorderType.BOTTOM).setLineStyle(LineStyle.DOUBLE);

        // Set paragraph shading
        Shading shading = builder.getParagraphFormat().getShading();
        shading.setTexture(TextureIndex.TEXTURE_DIAGONAL_CROSS);
        shading.setBackgroundPatternColor(new Color(240, 128, 128));  // Light Coral
        shading.setForegroundPatternColor(new Color(255, 160, 122));  // Light Salmon

        builder.write("I'm a formatted paragraph with double border and nice shading.");
        //ExEnd
    }

    @Test
    public void deleteRow() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.DeleteRow
        //ExSummary:Shows how to delete a row from a table.
        Document doc = new Document(getMyDir() + "DocumentBuilder.DocWithTable.doc");
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Delete the first row of the first table in the document.
        builder.deleteRow(0, 0);
        //ExEnd
    }

    @Test(enabled = false, description = "Bug: does not insert headers and footers, all lists (bullets, numbering, multilevel) breaks")
    public void insertDocument() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.InsertDocument(Document, ImportFormatMode)
        //ExFor:ImportFormatMode
        //ExSummary:Shows how to insert a document content into another document keep formatting of inserted document.
        Document doc = new Document(getMyDir() + "Document.docx");

        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.moveToDocumentEnd();
        builder.insertBreak(BreakType.PAGE_BREAK);

        Document docToInsert = new Document(getMyDir() + "DocumentBuilder.KeepSourceFormatting.docx");

        builder.insertDocument(docToInsert, ImportFormatMode.KEEP_SOURCE_FORMATTING);
        builder.getDocument().save(getArtifactsDir() + "DocumentBuilder.InsertDocument.docx");
        //ExEnd

        Assert.assertTrue(DocumentHelper.compareDocs(getArtifactsDir() + "DocumentBuilder.InsertDocument.docx", getGoldsDir() + "DocumentBuilder.InsertDocument Gold.docx"));
    }

    @Test
    public void keepSourceNumbering() throws Exception {
        //ExStart
        //ExFor:ImportFormatOptions.KeepSourceNumbering
        //ExFor:NodeImporter.#ctor(DocumentBase, DocumentBase, ImportFormatMode, ImportFormatOptions)
        //ExSummary:Shows how the numbering will be imported when it clashes in source and destination documents.
        Document dstDoc = new Document(getMyDir() + "DocumentBuilder.KeepSourceNumbering.DestinationDocument.docx");
        Document srcDoc = new Document(getMyDir() + "DocumentBuilder.KeepSourceNumbering.SourceDocument.docx");

        ImportFormatOptions importFormatOptions = new ImportFormatOptions();
        // Keep source list formatting when importing numbered paragraphs
        importFormatOptions.setKeepSourceNumbering(true);

        NodeImporter importer = new NodeImporter(srcDoc, dstDoc, ImportFormatMode.KEEP_SOURCE_FORMATTING, importFormatOptions);

        ParagraphCollection paragraphs = srcDoc.getFirstSection().getBody().getParagraphs();
        for (int i = 0; i < paragraphs.getCount(); i++) {
            Paragraph srcPara = paragraphs.get(i);
            Node importedNode = importer.importNode(srcPara, true);
            dstDoc.getFirstSection().getBody().appendChild(importedNode);
        }

        dstDoc.save(getArtifactsDir() + "DocumentBuilder.KeepSourceNumbering.ResultDocument.docx");
        //ExEnd
    }

    @Test
    public void ignoreTextBoxes() throws Exception {
        //ExStart
        //ExFor:ImportFormatOptions.IgnoreTextBoxes
        //ExSummary:Shows how to manage formatting in the text boxes of the source destination during the import.
        Document dstDoc = new Document(getMyDir() + "DocumentBuilder.IgnoreTextBoxes.DestinationDocument.docx");
        Document srcDoc = new Document(getMyDir() + "DocumentBuilder.IgnoreTextBoxes.SourceDocument.docx");

        ImportFormatOptions importFormatOptions = new ImportFormatOptions();
        // Keep the source text boxes formatting when importing
        importFormatOptions.setIgnoreTextBoxes(false);

        NodeImporter importer = new NodeImporter(srcDoc, dstDoc, ImportFormatMode.KEEP_SOURCE_FORMATTING, importFormatOptions);

        ParagraphCollection paragraphs = srcDoc.getFirstSection().getBody().getParagraphs();
        for (int i = 0; i < paragraphs.getCount(); i++) {
            Paragraph srcPara = paragraphs.get(i);
            Node importedNode = importer.importNode(srcPara, true);
            dstDoc.getFirstSection().getBody().appendChild(importedNode);
        }

        dstDoc.save(getArtifactsDir() + "DocumentBuilder.IgnoreTextBoxes.ResultDocument.docx");
        //ExEnd
    }

    @Test
    public void moveToFieldEx() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.MoveToField
        //ExSummary:Shows how to move document builder's cursor to a specific field.
        Document doc = new Document(getMyDir() + "Document.doc");
        DocumentBuilder builder = new DocumentBuilder(doc);

        Field field = builder.insertField("MERGEFIELD field");

        builder.moveToField(field, true);
        //ExEnd
    }

    @Test
    public void insertOleObject() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.InsertOleObject(String, Boolean, Boolean, Image)
        //ExFor:DocumentBuilder.InsertOleObject(String, String, Boolean, Boolean, Image)
        //ExSummary:Shows how to insert an OLE object into a document.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        BufferedImage representingImage = ImageIO.read(new File(getImageDir() + "Aspose.Words.gif"));

        Shape oleObject = builder.insertOleObject(getMyDir() + "Document.Spreadsheet.xlsx", false, false, representingImage);
        Shape oleObjectWithProgId = builder.insertOleObject(getMyDir() + "Document.Spreadsheet.xlsx", "Excel.Sheet", false, false, representingImage);

        doc.save(getArtifactsDir() + "Document.InsertedOleObject.docx");
        //ExEnd
    }

    @Test
    public void insertOleObjectException() throws Exception {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        try {
            builder.insertOleObject("", "checkbox", false, true, null);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void insertChartDouble() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.InsertChart(ChartType, Double, Double)
        //ExSummary:Shows how to insert a chart into a document.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.insertChart(ChartType.PIE, ConvertUtil.pixelToPoint(300.0), ConvertUtil.pixelToPoint(300.0));

        doc.save(getArtifactsDir() + "Document.InsertedChartDouble.doc");
        //ExEnd
    }

    @Test
    public void insertChartRelativePosition() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.InsertChart(ChartType, RelativeHorizontalPosition, Double, RelativeVerticalPosition, Double, Double, Double, WrapType)
        //ExSummary:Shows how to insert a chart into a document and specify position and size.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.insertChart(ChartType.PIE, RelativeHorizontalPosition.MARGIN, 100.0, RelativeVerticalPosition.MARGIN, 100.0, 200.0, 100.0, WrapType.SQUARE);

        doc.save(getArtifactsDir() + "Document.InsertedChartRelativePosition.doc");
        //ExEnd
    }

    @Test
    public void insertFieldFieldType() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.InsertField(FieldType, Boolean)
        //ExSummary:Shows how to insert a field into a document using FieldType
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.write("This field was inserted/updated at ");
        builder.insertField(FieldType.FIELD_TIME, true);

        doc.save(getArtifactsDir() + "Document.InsertedField.doc");
        //ExEnd
    }

    //ExStart
    //ExFor:IFieldResultFormatter
    //ExFor:IFieldResultFormatter.Format(Double, GeneralFormat)
    //ExFor:IFieldResultFormatter.Format(String, GeneralFormat)
    //ExFor:IFieldResultFormatter.FormatDateTime(DateTime, String, CalendarType)
    //ExFor:IFieldResultFormatter.FormatNumeric(Double, String)
    //ExFor:FieldOptions.ResultFormatter
    //ExFor:CalendarType
    //ExSummary:Shows how to control how the field result is formatted.
    @Test //ExSkip
    public void insertCustomFormattingField() throws Exception {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        doc.getFieldOptions().setResultFormatter(new FieldResultFormatter("${0}", "Date: {0}", "Item # {0}:"));

        // Insert a field with a numeric format
        builder.insertField(" = 2 + 3 \\# $###", null);

        // Insert a field with a date/time format
        builder.insertField("DATE \\@ \"d MMMM yyyy\"", null);

        // Insert a field with a general format
        builder.insertField("QUOTE \"2\" \\* Ordinal", null);

        // Formats will be applied and recorded by the formatter during the field update
        doc.updateFields();
        ((FieldResultFormatter) doc.getFieldOptions().getResultFormatter()).printInvocations();

        // Our formatter has also overridden the formats that were originally applied in the fields
        Assert.assertEquals(doc.getRange().getFields().get(0).getResult(), "$5");
        Assert.assertTrue(doc.getRange().getFields().get(1).getResult().startsWith("Date: "));
        Assert.assertEquals(doc.getRange().getFields().get(2).getResult(), "Item # 2:");
    }

    /// <summary>
    /// Custom IFieldResult implementation that applies formats and tracks format invocations
    /// </summary>
    private static class FieldResultFormatter implements IFieldResultFormatter {
        public FieldResultFormatter(final String numberFormat, final String dateFormat, final String generalFormat) {
            mNumberFormat = numberFormat;
            mDateFormat = dateFormat;
            mGeneralFormat = generalFormat;
        }

        public String formatNumeric(final double value, final String format) {
            mNumberFormatInvocations.add(new Object[]{value, format});

            return (mNumberFormat == null || "".equals(mNumberFormat)) ? null : MessageFormat.format(mNumberFormat, value);
        }

        public String formatDateTime(final Date value, final String format, final int calendarType) {
            mDateFormatInvocations.add(new Object[]{value, format, calendarType});

            return (mDateFormat == null || "".equals(mDateFormat)) ? null : MessageFormat.format(mDateFormat, value);
        }

        public String format(final String value, final int format) {
            return format((Object) value, format);
        }

        public String format(final double value, final int format) {
            return format((Object) value, format);
        }

        private String format(final Object value, final int format) {
            mGeneralFormatInvocations.add(new Object[]{value, format});

            return (mGeneralFormat == null || "".equals(mGeneralFormat)) ? null : MessageFormat.format(mGeneralFormat, value);
        }

        public void printInvocations() {
            System.out.println(MessageFormat.format("Number format invocations ({0}):", mNumberFormatInvocations.size()));
            for (Object[] s : (Iterable<Object[]>) mNumberFormatInvocations) {
                System.out.println("\tValue: " + s[0] + ", original format: " + s[1]);
            }

            System.out.println(MessageFormat.format("Date format invocations ({0}):", mDateFormatInvocations.size()));
            for (Object[] s : (Iterable<Object[]>) mDateFormatInvocations) {
                System.out.println("\tValue: " + s[0] + ", original format: " + s[1] + ", calendar type: " + s[2]);
            }

            System.out.println(MessageFormat.format("General format invocations ({0}):", mGeneralFormatInvocations.size()));
            for (Object[] s : (Iterable<Object[]>) mGeneralFormatInvocations) {
                System.out.println("\tValue: " + s[0] + ", original format: " + s[1]);
            }
        }

        private String mNumberFormat;
        private String mDateFormat;
        private String mGeneralFormat;

        private ArrayList mNumberFormatInvocations = new ArrayList();
        private ArrayList mDateFormatInvocations = new ArrayList();
        private ArrayList mGeneralFormatInvocations = new ArrayList();

    }
    //ExEnd

    @Test
    public void insertVideoWithUrl() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.InsertOnlineVideo(String, Double, Double)
        //ExSummary:Show how to insert online video into a document using video url
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Pass direct url from youtu.be.
        String url = "https://youtu.be/t_1LYZ102RA";

        double width = 360.0;
        double height = 270.0;

        builder.insertOnlineVideo(url, width, height);
        //ExEnd
    }

    @Test
    public void insertVideoWithHtmlCode() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.InsertOnlineVideo(String, String, Byte[], Double, Double)
        //ExFor:DocumentBuilder.InsertOnlineVideo(String, RelativeHorizontalPosition, Double, RelativeVerticalPosition, Double, Double, Double, WrapType)
        //ExFor:DocumentBuilder.InsertOnlineVideo(String, String, Byte[], RelativeHorizontalPosition, Double, RelativeVerticalPosition, Double, Double, Double, WrapType)
        //ExSummary:Show how to insert online video into a document using html code
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Visible url
        String vimeoVideoUrl = "https://vimeo.com/52477838";

        // Embed Html code
        String vimeoEmbedCode =
                "<iframe src=\"https://player.vimeo.com/video/52477838\" width=\"640\" height=\"360\" frameborder=\"0\" title=\"Aspose\" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe>";

        // This video will have an automatically generated thumbnail, and we are setting the size according to its 16:9 aspect ratio
        builder.writeln("Video with an automatically generated thumbnail at the top left corner of the page:");
        builder.insertOnlineVideo(vimeoVideoUrl, RelativeHorizontalPosition.LEFT_MARGIN, 0.0,
                RelativeVerticalPosition.TOP_MARGIN, 0.0, 320.0, 180.0, WrapType.SQUARE);
        builder.insertBreak(BreakType.PAGE_BREAK);

        // We can get an image to use as a custom thumbnail
        byte[] imageBytes = DocumentHelper.getBytesFromStream(getAsposelogoUri().toURL().openStream());
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
        // This puts the video where we are with our document builder, with a custom thumbnail and size depending on the size of the image
        builder.writeln("Custom thumbnail at document builder's cursor:");
        builder.insertOnlineVideo(vimeoVideoUrl, vimeoEmbedCode, imageBytes, image.getWidth(), image.getHeight());
        builder.insertBreak(BreakType.PAGE_BREAK);

        // We can put the video at the bottom right edge of the page too, but we'll have to take the page margins into account
        double left = builder.getPageSetup().getRightMargin() - image.getWidth();
        double top = builder.getPageSetup().getBottomMargin() - image.getHeight();

        // Here we use a custom thumbnail and relative positioning to put it and the bottom right of tha page
        builder.writeln("Bottom right of page with custom thumbnail:");

        builder.insertOnlineVideo(vimeoVideoUrl, vimeoEmbedCode, imageBytes,
                RelativeHorizontalPosition.RIGHT_MARGIN, left, RelativeVerticalPosition.BOTTOM_MARGIN, top,
                image.getWidth(), image.getHeight(), WrapType.SQUARE);


        doc.save(getArtifactsDir() + "DocumentBuilder.InsertOnlineVideo.docx");
        //ExEnd
    }

    @Test
    public void insertUnderline() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.Underline
        //ExSummary:Shows how to set and edit a document builder's underline.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Set a new style for our underline
        builder.setUnderline(Underline.DASH);

        // Same object as DocumentBuilder.Font.Underline
        Assert.assertEquals(builder.getFont().getUnderline(), builder.getUnderline());
        Assert.assertEquals(builder.getFont().getUnderline(), Underline.DASH);

        // These properties will be applied to the underline as well
        builder.getFont().setColor(Color.BLUE);
        builder.getFont().setSize(32.0);

        builder.writeln("Underlined text.");

        doc.save(getArtifactsDir() + "DocumentBuilder.Underline.docx");
        //ExEnd
    }

    @Test
    public void addTextToCurrentStory() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.CurrentStory
        //ExSummary:Shows how to work with a document builder's current story.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // The body of the current section is the same object as the current story
        Assert.assertEquals(doc.getFirstSection().getBody(), builder.getCurrentStory());
        Assert.assertEquals(builder.getCurrentParagraph().getParentNode(), builder.getCurrentStory());

        Assert.assertEquals(builder.getCurrentStory().getStoryType(), StoryType.MAIN_TEXT);

        builder.getCurrentStory().appendParagraph("Text added to current Story.");

        // A story can contain tables too
        Table table = builder.startTable();

        builder.insertCell();
        builder.write("This is row 1 cell 1");
        builder.insertCell();
        builder.write("This is row 1 cell 2");

        builder.endRow();

        builder.insertCell();
        builder.writeln("This is row 2 cell 1");
        builder.insertCell();
        builder.writeln("This is row 2 cell 2");

        builder.endRow();
        builder.endTable();

        // The table we just made is automatically placed in the story
        Assert.assertTrue(builder.getCurrentStory().getTables().contains(table));

        doc.save(getArtifactsDir() + "DocumentBuilder.CurrentStory.docx");
        //ExEnd
    }

    @Test
    public void builderInsertOleObject() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.InsertOleObject(Stream, String, Boolean, Image)
        //ExSummary:Shows how to use document builder to embed Ole objects in a document.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Let's take a spreadsheet from our system and insert it into the document
        InputStream spreadsheetStream = new FileInputStream(getMyDir() + "MySpreadsheet.xlsx");

        // The spreadsheet can be activated by double clicking the panel that you'll see in the document immediately under the text we will add
        // We did not set the area to double click as an icon nor did we change its appearance so it looks like a simple panel
        builder.writeln("Spreadsheet Ole object:");
        builder.insertOleObject(spreadsheetStream, "MyOleObject.xlsx", false, null);

        // A powerpoint presentation is another type of object we can embed in our document
        // This time we'll also exercise some control over how it looks
        InputStream powerpointStream = new FileInputStream(getMyDir() + "MyPresentation.pptx");
        byte[] imageBytes = DocumentHelper.getBytesFromStream(getAsposelogoUri().toURL().openStream());
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));

        // If we double click the image, the powerpoint presentation will open
        builder.insertParagraph();
        builder.writeln("Powerpoint Ole object:");
        builder.insertOleObject(powerpointStream, "MyOleObject.pptx", true, image);

        doc.save(getArtifactsDir() + "DocumentBuilder.InsertOleObject.docx");
        //ExEnd
    }

    @Test
    public void builderInsertStyleSeparator() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.InsertStyleSeparator
        //ExSummary:Shows how to use and separate multiple styles in a paragraph
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.write("This text is in the default style. ");

        builder.insertStyleSeparator();

        // Create a custom style
        Style myStyle = builder.getDocument().getStyles().add(StyleType.PARAGRAPH, "MyStyle");
        myStyle.getFont().setSize(14.0);
        myStyle.getFont().setName("Courier New");
        myStyle.getFont().setColor(Color.BLUE);

        // Append text with custom style
        builder.getParagraphFormat().setStyleName(myStyle.getName());
        builder.write("This is text in the same paragraph but with my custom style.");

        doc.save(getArtifactsDir() + "DocumentBuilder.StyleSeparator.docx");
        //ExEnd
    }

    @Test
    public void insertStyleSeparator() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.InsertStyleSeparator
        //ExSummary:Shows how to separate styles from two different paragraphs used in one logical printed paragraph.
        DocumentBuilder builder = new DocumentBuilder(new Document());

        Style paraStyle = builder.getDocument().getStyles().add(StyleType.PARAGRAPH, "MyParaStyle");
        paraStyle.getFont().setBold(false);
        paraStyle.getFont().setSize(8.0);
        paraStyle.getFont().setName("Arial");

        // Append text with "Heading 1" style.
        builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADING_1);
        builder.write("Heading 1");
        builder.insertStyleSeparator();

        // Append text with another style.
        builder.getParagraphFormat().setStyleName(paraStyle.getName());
        builder.write("This is text with some other formatting ");
        //ExEnd

        builder.getDocument().save(getArtifactsDir() + "DocumentBuilder.InsertStyleSeparator.docx");
    }

    @Test
    public void withoutStyleSeparator() throws Exception {
        DocumentBuilder builder = new DocumentBuilder(new Document());

        Style paraStyle = builder.getDocument().getStyles().add(StyleType.PARAGRAPH, "MyParaStyle");
        paraStyle.getFont().setBold(false);
        paraStyle.getFont().setSize(8.0);
        paraStyle.getFont().setName("Arial");

        // Append text with "Heading 1" style.
        builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADING_1);
        builder.write("Heading 1");

        // Append text with another style.
        builder.getParagraphFormat().setStyleName(paraStyle.getName());
        builder.write("This is text with some other formatting ");

        builder.getDocument().save(getArtifactsDir() + "DocumentBuilder.InsertTextWithoutStyleSeparator.docx");
    }

    @Test
    public void resolveStyleBehaviorWhileInsertDocument() throws Exception {
        //ExStart
        //ExFor:ImportFormatOptions
        //ExFor:ImportFormatOptions.SmartStyleBehavior
        //ExFor:DocumentBuilder.InsertDocument(Document, ImportFormatMode, ImportFormatOptions)
        //ExSummary:Shows how to resolve styles behavior while inserting documents.
        Document destDoc = new Document(getMyDir() + "DocumentBuilder.SmartStyleBehavior.DestinationDocument.docx");
        Document sourceDoc1 = new Document(getMyDir() + "DocumentBuilder.SmartStyleBehavior.SourceDocument01.docx");
        Document sourceDoc2 = new Document(getMyDir() + "DocumentBuilder.SmartStyleBehavior.SourceDocument02.docx");

        DocumentBuilder builder = new DocumentBuilder(destDoc);

        builder.moveToDocumentEnd();
        builder.insertBreak(BreakType.PAGE_BREAK);
        builder.moveToDocumentEnd();

        ImportFormatOptions importFormatOptions = new ImportFormatOptions();
        importFormatOptions.setSmartStyleBehavior(true);

        // When SmartStyleBehavior is enabled,
        // a source style will be expanded into a direct attributes inside a destination document,
        // if KeepSourceFormatting importing mode is used.
        builder.insertDocument(sourceDoc1, ImportFormatMode.KEEP_SOURCE_FORMATTING, importFormatOptions);

        builder.moveToDocumentEnd();
        builder.insertBreak(BreakType.PAGE_BREAK);

        // When SmartStyleBehavior is disabled,
        // a source style will be expanded only if it is numbered.
        // Existing destination attributes will not be overridden, including lists.
        builder.insertDocument(sourceDoc2, ImportFormatMode.USE_DESTINATION_STYLES);

        destDoc.save(getArtifactsDir() + "DocumentBuilder.SmartStyleBehavior.ResultDocument.docx");
        //ExEnd
    }

    @Test
    public void resolveStyleBehaviorWhileAppendDocument() throws Exception {
        //ExStart
        //ExFor:Document.AppendDocument(Document, ImportFormatMode, ImportFormatOptions)
        //ExSummary:Shows how to resolve styles behavior while append document.
        Document srcDoc = new Document(getMyDir() + "DocumentBuilder.ResolveStyleBehaviorWhileAppendDocument.Source.docx");
        Document dstDoc = new Document(getMyDir() + "DocumentBuilder.ResolveStyleBehaviorWhileAppendDocument.Destination.docx");

        ImportFormatOptions options = new ImportFormatOptions();
        // Specify that if numbering clashes in source and destination documents
        // then a numbering from the source document will be used.
        options.setKeepSourceNumbering(true);
        dstDoc.appendDocument(srcDoc, ImportFormatMode.USE_DESTINATION_STYLES, options);
        dstDoc.updateListLabels();
        //ExEnd

        Paragraph para = dstDoc.getSections().get(1).getBody().getLastParagraph();
        String paraText = para.getText();

        Assert.assertEquals(para.getListLabel().getLabelString(), "1.");
        Assert.assertTrue(paraText.startsWith("13->13"), paraText);
    }
}
