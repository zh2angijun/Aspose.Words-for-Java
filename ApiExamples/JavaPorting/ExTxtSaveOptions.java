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
import com.aspose.words.TxtSaveOptions;
import com.aspose.words.TxtExportHeadersFootersMode;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.SaveFormat;
import com.aspose.ms.System.Text.Encoding;
import org.testng.annotations.DataProvider;


@Test
public class ExTxtSaveOptions extends ApiExampleBase
{
    @Test
    public void pageBreaks() throws Exception
    {
        //ExStart
        //ExFor:TxtSaveOptionsBase.ForcePageBreaks
        //ExSummary:Shows how to specify whether the page breaks should be preserved during export.
        Document doc = new Document(getMyDir() + "SaveOptions.PageBreaks.docx");

        TxtSaveOptions saveOptions = new TxtSaveOptions(); { saveOptions.setForcePageBreaks(false); }

        doc.save(getArtifactsDir() + "SaveOptions.PageBreaks.txt", saveOptions);
        //ExEnd
    }

    @Test
    public void addBidiMarks() throws Exception
    {
        //ExStart
        //ExFor:TxtSaveOptions.AddBidiMarks
        //ExSummary:Shows how to insert Unicode Character 'RIGHT-TO-LEFT MARK' (U+200F) before each bi-directional Run in text.
        Document doc = new Document(getMyDir() + "Document.docx");
        
        TxtSaveOptions saveOptions = new TxtSaveOptions(); { saveOptions.setAddBidiMarks(true); }

        doc.save(getArtifactsDir() + "AddBidiMarks.txt", saveOptions);
        //ExEnd
    }

    @Test (dataProvider = "exportHeadersFootersDataProvider")
    public void exportHeadersFooters(/*TxtExportHeadersFootersMode*/int txtExportHeadersFootersMode) throws Exception
    {
        //ExStart
        //ExFor:TxtSaveOptionsBase.ExportHeadersFootersMode
        //ExFor:TxtExportHeadersFootersMode
        //ExSummary:Shows how to specifies the way headers and footers are exported to plain text format.
        Document doc = new Document(getMyDir() + "HeaderFooter.HeaderFooterOrder.docx");

        // Three values are available in TxtExportHeadersFootersMode enum:
        // "None" - No headers and footers are exported
        // "AllAtEnd" - All headers and footers are placed after all section bodies at the very end of a document
        // "PrimaryOnly" - Only primary headers and footers are exported at the beginning and end of each section (default value)
        TxtSaveOptions saveOptions = new TxtSaveOptions(); { saveOptions.setExportHeadersFootersMode(txtExportHeadersFootersMode); }

        doc.save(getArtifactsDir() + "ExportHeadersFooters.txt", saveOptions);
        //ExEnd
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "exportHeadersFootersDataProvider")
	public static Object[][] exportHeadersFootersDataProvider() throws Exception
	{
		return new Object[][]
		{
			{TxtExportHeadersFootersMode.NONE},
			{TxtExportHeadersFootersMode.ALL_AT_END},
			{TxtExportHeadersFootersMode.PRIMARY_ONLY},
		};
	}

    @Test
    public void txtListIndentation() throws Exception
    {
        //ExStart
        //ExFor:TxtListIndentation
        //ExFor:TxtListIndentation.Count
        //ExFor:TxtListIndentation.Character
        //ExFor:TxtSaveOptions.ListIndentation
        //ExSummary:Shows how list levels are displayed when the document is converting to plain text format.
        Document doc = new Document(getMyDir() + "TxtSaveOptions.TxtListIndentation.docx");
 
        TxtSaveOptions txtSaveOptions = new TxtSaveOptions();
        txtSaveOptions.getListIndentation().setCount(3);
        txtSaveOptions.getListIndentation().setCharacter(' ');
        txtSaveOptions.setPreserveTableLayout(true);

        doc.save(getArtifactsDir() + "TxtSaveOptions.TxtListIndentation.txt", txtSaveOptions);
        //ExEnd
    }

    @Test
    public void paragraphBreak() throws Exception
    {
        //ExStart
        //ExFor:TxtSaveOptions
        //ExFor:TxtSaveOptions.SaveFormat
        //ExFor:TxtSaveOptionsBase
        //ExFor:TxtSaveOptionsBase.ParagraphBreak
        //ExSummary:Shows how to save a .txt document with a custom paragraph break.
        // Create a new document and add some paragraphs
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.writeln("Paragraph 1.");
        builder.writeln("Paragraph 2.");
        builder.write("Paragraph 3.");

        // When saved to plain text, the paragraphs we created can be separated by a custom string
        TxtSaveOptions txtSaveOptions = new TxtSaveOptions(); { txtSaveOptions.setSaveFormat(SaveFormat.TEXT); txtSaveOptions.setParagraphBreak(" End of paragraph.\n\n\t"); }
        
        doc.save(getArtifactsDir() + "TxtSaveOptions.ParagraphBreak.txt", txtSaveOptions);
        //ExEnd
    }

    @Test
    public void encoding() throws Exception
    {
        //ExStart
        //ExFor:TxtSaveOptionsBase.Encoding
        //ExSummary:Shows how to set encoding for a .txt output document.
        // Create a new document and add some text from outside the ASCII character set
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.writeln("À È Ì Ò Ù.");

        // We can use a SaveOptions object to make sure the encoding we save the .txt document in supports our content
        TxtSaveOptions txtSaveOptions = new TxtSaveOptions(); { txtSaveOptions.setEncoding(com.aspose.ms.System.Text.Encoding.getUTF8()); }

        doc.save(getArtifactsDir() + "TxtSaveOptions.Encoding.txt", txtSaveOptions);
        //ExEnd
    }

    @Test
    public void appearance() throws Exception
    {
        //ExStart
        //ExFor:TxtSaveOptionsBase.PreserveTableLayout
        //ExFor:TxtSaveOptions.SimplifyListLabels
        //ExSummary:Shows how to change the appearance of tables and lists during conversion to a txt document output.
        // Open a document with a table
        Document doc = new Document(getMyDir() + "Lists.PrintOutAllLists.doc");

        // Due to the nature of text documents, table grids and text wrapping will be lost during conversion
        // from a file type that supports tables
        // We can preserve some of the table layout in the appearance of our content with the PreserveTableLayout flag
        // The SimplifyListLabels flag will convert some list symbols
        // into ASCII characters such as *, o, +, > etc, depending on list level
        TxtSaveOptions txtSaveOptions = new TxtSaveOptions(); { txtSaveOptions.setSimplifyListLabels(true); txtSaveOptions.setPreserveTableLayout(true);}

        doc.save(getArtifactsDir() + "TxtSaveOptions.Appearance.txt", txtSaveOptions);
        //ExEnd
    }
}
