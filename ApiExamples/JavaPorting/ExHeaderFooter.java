// Copyright (c) 2001-2019 Aspose Pty Ltd. All Rights Reserved.
//
// This file is part of Aspose.Words. The source code in this file
// is only intended as a supplement to the documentation, and is provided
// "as is", without warranty of any kind, either expressed or implied.
//////////////////////////////////////////////////////////////////////////

package ApiExamples;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.words.Document;
import com.aspose.words.HeaderFooter;
import com.aspose.words.HeaderFooterType;
import com.aspose.words.Paragraph;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.BreakType;
import com.aspose.words.Section;
import com.aspose.words.HtmlSaveOptions;
import com.aspose.words.SaveFormat;
import com.aspose.words.ExportHeadersFootersMode;
import com.aspose.words.HeaderFooterCollection;
import com.aspose.words.FindReplaceOptions;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.words.IReplacingCallback;
import com.aspose.words.ReplaceAction;
import com.aspose.words.ReplacingArgs;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.words.PageSetup;
import com.aspose.words.ParagraphAlignment;
import com.aspose.words.RelativeHorizontalPosition;
import com.aspose.words.RelativeVerticalPosition;
import com.aspose.words.WrapType;
import com.aspose.words.PreferredWidth;
import com.aspose.words.Orientation;
import com.aspose.words.Row;


@Test
public class ExHeaderFooter extends ApiExampleBase
{
    @Test
    public void headerFooterCreate() throws Exception
    {
        //ExStart
        //ExFor:HeaderFooter
        //ExFor:HeaderFooter.#ctor(DocumentBase, HeaderFooterType)
        //ExFor:HeaderFooter.HeaderFooterType
        //ExFor:HeaderFooter.IsHeader
        //ExFor:HeaderFooterCollection
        //ExFor:Paragraph.IsEndOfHeaderFooter
        //ExFor:Paragraph.ParentSection
        //ExFor:Paragraph.ParentStory
        //ExFor:Story.AppendParagraph
        //ExSummary:Creates a header and footer using the document object model and insert them into a section.
        Document doc = new Document();
        
        HeaderFooter header = new HeaderFooter(doc, HeaderFooterType.HEADER_PRIMARY);
        doc.getFirstSection().getHeadersFooters().add(header);

        // Add a paragraph with text to the footer.
        Paragraph para = header.appendParagraph("My header");

        Assert.assertTrue(header.isHeader());
        Assert.assertTrue(para.isEndOfHeaderFooter());

        HeaderFooter footer = new HeaderFooter(doc, HeaderFooterType.FOOTER_PRIMARY);
        doc.getFirstSection().getHeadersFooters().add(footer);

        // Add a paragraph with text to the footer.
        para = footer.appendParagraph("My footer");

        Assert.assertFalse(footer.isHeader());
        Assert.assertTrue(para.isEndOfHeaderFooter());

        msAssert.areEqual(footer, para.getParentStory());
        msAssert.areEqual(footer.getParentSection(), para.getParentSection());
        msAssert.areEqual(footer.getParentSection(), header.getParentSection());
        
        doc.save(getArtifactsDir() + "HeaderFooter.HeaderFooterCreate.docx");
        //ExEnd
        doc = new Document(getArtifactsDir() + "HeaderFooter.HeaderFooterCreate.docx");

        Assert.assertTrue(doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY).getRange().getText()
            .contains("My header"));
        Assert.assertTrue(doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.FOOTER_PRIMARY).getRange().getText()
            .contains("My footer"));
    }

    @Test
    public void headerFooterLink() throws Exception
    {
        //ExStart
        //ExFor:HeaderFooter.IsLinkedToPrevious
        //ExFor:HeaderFooterCollection.Item(System.Int32)
        //ExFor:HeaderFooterCollection.LinkToPrevious(Aspose.Words.HeaderFooterType,System.Boolean)
        //ExFor:HeaderFooterCollection.LinkToPrevious(System.Boolean)
        //ExFor:HeaderFooter.ParentSection
        //ExSummary:Shows how to link header/footers between sections.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Create three sections
        builder.write("Section 1");
        builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);
        builder.write("Section 2");
        builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);
        builder.write("Section 3");

        // Create a header and footer in the first section and give them text
        builder.moveToSection(0);

        builder.moveToHeaderFooter(HeaderFooterType.HEADER_PRIMARY);
        builder.write("This is the header, which will be displayed in sections 1 and 2.");

        builder.moveToHeaderFooter(HeaderFooterType.FOOTER_PRIMARY);
        builder.write("This is the footer, which will be displayed in sections 1, 2 and 3.");

        // If headers/footers are linked by the next section, they appear in that section also
        // The second section will display the header/footers of the first
        doc.getSections().get(1).getHeadersFooters().linkToPrevious(true);

        // However, the underlying headers/footers in the respective header/footer collections of the sections still remain different
        // Linking just overrides the existing headers/footers from the latter section
        msAssert.areEqual(doc.getSections().get(0).getHeadersFooters().get(0).getHeaderFooterType(), doc.getSections().get(1).getHeadersFooters().get(0).getHeaderFooterType());
        msAssert.areNotEqual(doc.getSections().get(0).getHeadersFooters().get(0).getParentSection(), doc.getSections().get(1).getHeadersFooters().get(0).getParentSection());
        msAssert.areNotEqual(doc.getSections().get(0).getHeadersFooters().get(0).getText(), doc.getSections().get(1).getHeadersFooters().get(0).getText());

        // Likewise, unlinking headers/footers makes them not appear
        doc.getSections().get(2).getHeadersFooters().linkToPrevious(false);

        // We can also choose only certain header/footer types to get linked, like the footer in this case
        // The 3rd section now won't have the same header but will have the same footer as the 2nd and 1st sections
        doc.getSections().get(2).getHeadersFooters().linkToPrevious(HeaderFooterType.FOOTER_PRIMARY, true);
        
        // The first section's header/footers can't link themselves to anything because there is no previous section
        msAssert.areEqual(2, doc.getSections().get(0).getHeadersFooters().getCount());
        Assert.assertFalse(doc.getSections().get(0).getHeadersFooters().get(0).isLinkedToPrevious());
        Assert.assertFalse(doc.getSections().get(0).getHeadersFooters().get(1).isLinkedToPrevious());

        // All of the second section's header/footers are linked to those of the first
        msAssert.areEqual(6, doc.getSections().get(1).getHeadersFooters().getCount());
        Assert.assertTrue(doc.getSections().get(1).getHeadersFooters().get(0).isLinkedToPrevious());
        Assert.assertTrue(doc.getSections().get(1).getHeadersFooters().get(1).isLinkedToPrevious());
        Assert.assertTrue(doc.getSections().get(1).getHeadersFooters().get(2).isLinkedToPrevious());
        Assert.assertTrue(doc.getSections().get(1).getHeadersFooters().get(3).isLinkedToPrevious());
        Assert.assertTrue(doc.getSections().get(1).getHeadersFooters().get(4).isLinkedToPrevious());
        Assert.assertTrue(doc.getSections().get(1).getHeadersFooters().get(5).isLinkedToPrevious());

        // In the third section, only the footer we explicitly linked is linked to that of the second, and consequently the first section
        msAssert.areEqual(6, doc.getSections().get(2).getHeadersFooters().getCount());
        Assert.assertFalse(doc.getSections().get(2).getHeadersFooters().get(0).isLinkedToPrevious());
        Assert.assertFalse(doc.getSections().get(2).getHeadersFooters().get(1).isLinkedToPrevious());
        Assert.assertFalse(doc.getSections().get(2).getHeadersFooters().get(2).isLinkedToPrevious());
        Assert.assertTrue(doc.getSections().get(2).getHeadersFooters().get(3).isLinkedToPrevious());
        Assert.assertFalse(doc.getSections().get(2).getHeadersFooters().get(4).isLinkedToPrevious());
        Assert.assertFalse(doc.getSections().get(2).getHeadersFooters().get(5).isLinkedToPrevious());

        doc.save(getArtifactsDir() + "HeaderFooter.HeaderFooterLink.docx");
        //ExEnd
    }

    @Test
    public void removeFooters() throws Exception
    {
        //ExStart
        //ExFor:Section.HeadersFooters
        //ExFor:HeaderFooterCollection
        //ExFor:HeaderFooterCollection.Item(HeaderFooterType)
        //ExFor:HeaderFooter
        //ExFor:HeaderFooterType
        //ExId:RemoveFooters
        //ExSummary:Deletes all footers from all sections, but leaves headers intact.
        Document doc = new Document(getMyDir() + "HeaderFooter.RemoveFooters.doc");

        for (Section section : doc.<Section>OfType() !!Autoporter error: Undefined expression type )
        {
            // Up to three different footers are possible in a section (for first, even and odd pages).
            // We check and delete all of them.
            HeaderFooter footer = section.getHeadersFooters().getByHeaderFooterType(HeaderFooterType.FOOTER_FIRST);
            footer?.Remove();

            // Primary footer is the footer used for odd pages.
            footer = section.getHeadersFooters().getByHeaderFooterType(HeaderFooterType.FOOTER_PRIMARY);
            footer?.Remove();

            footer = section.getHeadersFooters().getByHeaderFooterType(HeaderFooterType.FOOTER_EVEN);
            footer?.Remove();
        }

        doc.save(getArtifactsDir() + "HeaderFooter.RemoveFooters.doc");
        //ExEnd
    }

    @Test
    public void setExportHeadersFootersMode() throws Exception
    {
        //ExStart
        //ExFor:HtmlSaveOptions.ExportHeadersFootersMode
        //ExFor:ExportHeadersFootersMode
        //ExSummary:Demonstrates how to disable the export of headers and footers when saving to HTML based formats.
        Document doc = new Document(getMyDir() + "HeaderFooter.RemoveFooters.doc");

        // Disables exporting headers and footers.
        HtmlSaveOptions saveOptions =
            new HtmlSaveOptions(SaveFormat.HTML); { saveOptions.setExportHeadersFootersMode(ExportHeadersFootersMode.NONE); }

        doc.save(getArtifactsDir() + "HeaderFooter.DisableHeadersFooters.html", saveOptions);
        //ExEnd

        // Verify that the output document is correct.
        doc = new Document(getArtifactsDir() + "HeaderFooter.DisableHeadersFooters.html");
        Assert.assertFalse(doc.getRange().getText().contains("DYNAMIC TEMPLATE"));
    }

    @Test
    public void replaceText() throws Exception
    {
        //ExStart
        //ExFor:Document.FirstSection
        //ExFor:Section.HeadersFooters
        //ExFor:HeaderFooterCollection.Item(HeaderFooterType)
        //ExFor:HeaderFooter
        //ExFor:Range.Replace(String, String, FindReplaceOptions)
        //ExSummary:Shows how to replace text in the document footer.
        // Open the template document, containing obsolete copyright information in the footer.
        Document doc = new Document(getMyDir() + "HeaderFooter.ReplaceText.doc");

        HeaderFooterCollection headersFooters = doc.getFirstSection().getHeadersFooters();
        HeaderFooter footer = headersFooters.getByHeaderFooterType(HeaderFooterType.FOOTER_PRIMARY);

        FindReplaceOptions options = new FindReplaceOptions();
        {
            options.setMatchCase(false);
            options.setFindWholeWordsOnly(false);
        }

        footer.getRange().replace("(C) 2006 Aspose Pty Ltd.", "Copyright (C) 2011 by Aspose Pty Ltd.", options);

        doc.save(getArtifactsDir() + "HeaderFooter.ReplaceText.doc");
        //ExEnd

        // Verify that the appropriate changes were made to the output document.
        doc = new Document(getArtifactsDir() + "HeaderFooter.ReplaceText.doc");
        Assert.assertTrue(doc.getRange().getText().contains("Copyright (C) 2011 by Aspose Pty Ltd."));
    }

    @Test
    public void headerFooterOrder() throws Exception
    {
        //ExStart
        //ExFor:IReplacingCallback
        //ExFor:Range.Replace(String, String, FindReplaceOptions)
        //ExSummary: Show changes for headers and footers order
        Document doc = new Document(getMyDir() + "HeaderFooter.HeaderFooterOrder.docx");

        // Assert that we use special header and footer for the first page
        // The order for this: first header\footer, even header\footer, primary header\footer
        Section firstPageSection = doc.getFirstSection();
        msAssert.areEqual(true, firstPageSection.getPageSetup().getDifferentFirstPageHeaderFooter());

        ReplaceLog logger = new ReplaceLog();
        FindReplaceOptions options = new FindReplaceOptions(); { options.setReplacingCallback(logger); }

        doc.getRange().replaceInternal(new Regex("(header|footer)"), "", options);

        doc.save(getArtifactsDir() + "HeaderFooter.HeaderFooterOrder.docx");
        msAssert.areEqual("First header\nFirst footer\nSecond header\nSecond footer\nThird header\n" +
                        "Third footer\n", logger.getText().replace("\r", ""));
        // Prepare our string builder for assert results without "DifferentFirstPageHeaderFooter"
        logger.clearText();

        // Remove special first page
        // The order for this: primary header, default header, primary footer, default footer, even header\footer
        firstPageSection.getPageSetup().setDifferentFirstPageHeaderFooter(false);

        doc.getRange().replaceInternal(new Regex("(header|footer)"), "", options);
        msAssert.areEqual("Third header\nFirst header\nThird footer\nFirst footer\nSecond header\nSecond footer\n", logger.getText().replace("\r", ""));
    }

    private static class ReplaceLog implements IReplacingCallback
    {
        public /*ReplaceAction*/int replacing(ReplacingArgs args)
        {
            msStringBuilder.appendLine(_textBuilder, args.getMatchNode().getText());
            return ReplaceAction.SKIP;
        }

        void clearText()
        {
            _textBuilder.Clear();
        }

        String getText() { return _textBuilder.toString(); }

        private /*final*/ StringBuilder _textBuilder = new StringBuilder();
    }
    //ExEnd

    @Test
    public void primer() throws Exception
    {
        //ExStart
        //ExId:HeaderFooterPrimer
        //ExSummary:Maybe a bit complicated example, but demonstrates many things that can be done with headers/footers.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Section currentSection = builder.getCurrentSection();
        PageSetup pageSetup = currentSection.getPageSetup();

        // Specify if we want headers/footers of the first page to be different from other pages.
        // You can also use PageSetup.OddAndEvenPagesHeaderFooter property to specify
        // different headers/footers for odd and even pages.
        pageSetup.setDifferentFirstPageHeaderFooter(true);

        // --- Create header for the first page. ---
        pageSetup.setHeaderDistance(20.0);
        builder.moveToHeaderFooter(HeaderFooterType.HEADER_FIRST);
        builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);

        // Set font properties for header text.
        builder.getFont().setName("Arial");
        builder.getFont().setBold(true);
        builder.getFont().setSize(14.0);
        // Specify header title for the first page.
        builder.write("Aspose.Words Header/Footer Creation Primer - Title Page.");

        // --- Create header for pages other than first. ---
        pageSetup.setHeaderDistance(20.0);
        builder.moveToHeaderFooter(HeaderFooterType.HEADER_PRIMARY);

        // Insert absolutely positioned image into the top/left corner of the header.
        // Distance from the top/left edges of the page is set to 10 points.
        String imageFileName = getImageDir() + "Aspose.Words.gif";
        builder.insertImage(imageFileName, RelativeHorizontalPosition.PAGE, 10.0, RelativeVerticalPosition.PAGE, 10.0,
            50.0, 50.0, WrapType.THROUGH);

        builder.getParagraphFormat().setAlignment(ParagraphAlignment.RIGHT);
        // Specify another header title for other pages.
        builder.write("Aspose.Words Header/Footer Creation Primer.");

        // --- Create footer for pages other than first. ---
        builder.moveToHeaderFooter(HeaderFooterType.FOOTER_PRIMARY);

        // We use table with two cells to make one part of the text on the line (with page numbering)
        // to be aligned left, and the other part of the text (with copyright) to be aligned right.
        builder.startTable();

        // Clear table borders.
        builder.getCellFormat().clearFormatting();

        builder.insertCell();

        // Set first cell to 1/3 of the page width.
        builder.getCellFormat().setPreferredWidth(PreferredWidth.fromPercent(100.0F / 3f));

        // Insert page numbering text here.
        // It uses PAGE and NUMPAGES fields to auto calculate current page number and total number of pages.
        builder.write("Page ");
        builder.insertField("PAGE", "");
        builder.write(" of ");
        builder.insertField("NUMPAGES", "");

        // Align this text to the left.
        builder.getCurrentParagraph().getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);

        builder.insertCell();
        // Set the second cell to 2/3 of the page width.
        builder.getCellFormat().setPreferredWidth(PreferredWidth.fromPercent(100.0F * 2f / 3f));

        builder.write("(C) 2001 Aspose Pty Ltd. All rights reserved.");

        // Align this text to the right.
        builder.getCurrentParagraph().getParagraphFormat().setAlignment(ParagraphAlignment.RIGHT);

        builder.endRow();
        builder.endTable();

        builder.moveToDocumentEnd();
        // Make page break to create a second page on which the primary headers/footers will be seen.
        builder.insertBreak(BreakType.PAGE_BREAK);

        // Make section break to create a third page with different page orientation.
        builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);

        // Get the new section and its page setup.
        currentSection = builder.getCurrentSection();
        pageSetup = currentSection.getPageSetup();

        // Set page orientation of the new section to landscape.
        pageSetup.setOrientation(Orientation.LANDSCAPE);

        // This section does not need different first page header/footer.
        // We need only one title page in the document and the header/footer for this page
        // has already been defined in the previous section
        pageSetup.setDifferentFirstPageHeaderFooter(false);

        // This section displays headers/footers from the previous section by default.
        // Call currentSection.HeadersFooters.LinkToPrevious(false) to cancel this.
        // Page width is different for the new section and therefore we need to set 
        // a different cell widths for a footer table.
        currentSection.getHeadersFooters().linkToPrevious(false);

        // If we want to use the already existing header/footer set for this section 
        // but with some minor modifications then it may be expedient to copy headers/footers
        // from the previous section and apply the necessary modifications where we want them.
        copyHeadersFootersFromPreviousSection(currentSection);

        // Find the footer that we want to change.
        HeaderFooter primaryFooter = currentSection.getHeadersFooters().getByHeaderFooterType(HeaderFooterType.FOOTER_PRIMARY);

        Row row = primaryFooter.getTables().get(0).getFirstRow();
        row.getFirstCell().getCellFormat().setPreferredWidth(PreferredWidth.fromPercent(100.0F / 3f));
        row.getLastCell().getCellFormat().setPreferredWidth(PreferredWidth.fromPercent(100.0F * 2f / 3f));

        // Save the resulting document.
        doc.save(getArtifactsDir() + "HeaderFooter.Primer.doc");
    }

    /// <summary>
    /// Clones and copies headers/footers form the previous section to the specified section.
    /// </summary>
    private static void copyHeadersFootersFromPreviousSection(Section section)
    {
        Section previousSection = (Section) section.getPreviousSibling();

        if (previousSection == null)
            return;

        section.getHeadersFooters().clear();

        for (HeaderFooter headerFooter : previousSection.getHeadersFooters().<HeaderFooter>OfType() !!Autoporter error: Undefined expression type )
        {
            section.getHeadersFooters().add(headerFooter.deepClone(true));
        }
    }

    //ExEnd
}
