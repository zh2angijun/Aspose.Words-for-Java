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
import com.aspose.words.DocumentBuilder;
import com.aspose.words.MsWordVersion;
import com.aspose.words.Shape;
import com.aspose.words.NodeType;
import com.aspose.ms.System.msConsole;
import com.aspose.ms.NUnit.Framework.msAssert;
import org.testng.Assert;
import com.aspose.words.ShapeMarkupLanguage;
import com.aspose.words.OoxmlSaveOptions;
import com.aspose.words.OoxmlCompliance;
import com.aspose.words.SaveFormat;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.words.ListTemplate;
import com.aspose.words.List;
import com.aspose.words.BreakType;
import com.aspose.ms.System.DateTime;


@Test
class ExOoxmlSaveOptions !Test class should be public in Java to run, please fix .Net source!  extends ApiExampleBase
{
    @Test
    public void iso29500Strict() throws Exception
    {
        //ExStart
        //ExFor:OoxmlCompliance
        //ExFor:OoxmlSaveOptions.Compliance
        //ExFor:ShapeMarkupLanguage
        //ExSummary:Shows conversion VML shapes to DML using ISO/IEC 29500:2008 Strict compliance level
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Set Word2003 version for document, for inserting image as VML shape
        doc.getCompatibilityOptions().optimizeFor(MsWordVersion.WORD_2003);

        builder.insertImage(getImageDir() + "dotnet-logo.png");

        // Loop through all single shapes inside document.
        for (Shape shape : doc.getChildNodes(NodeType.SHAPE, true).<Shape>OfType() !!Autoporter error: Undefined expression type )
        {
            msConsole.writeLine(shape.getMarkupLanguage());
            msAssert.areEqual(ShapeMarkupLanguage.VML, shape.getMarkupLanguage()); //ExSkip
        }

        // Iso29500_2008 does not allow VML shapes
        // You need to use OoxmlCompliance.Iso29500_2008_Strict for converting VML to DML shapes
        OoxmlSaveOptions saveOptions = new OoxmlSaveOptions();
        {
            saveOptions.setCompliance(OoxmlCompliance.ISO_29500_2008_STRICT);
            saveOptions.setSaveFormat(SaveFormat.DOCX);
        }
        //ExEnd

        MemoryStream dstStream = new MemoryStream();
        doc.save(dstStream, saveOptions);

        // Assert that image have drawingML markup language
        for (Shape shape : doc.getChildNodes(NodeType.SHAPE, true).<Shape>OfType() !!Autoporter error: Undefined expression type )
        {
            msAssert.areEqual(ShapeMarkupLanguage.DML, shape.getMarkupLanguage());
        }
    }

    @Test
    public void restartingDocumentList() throws Exception
    {
        //ExStart
        //ExFor:List.IsRestartAtEachSection
        //ExSummary:Shows how to specify that the list has to be restarted at each section.
        Document doc = new Document();

        doc.getLists().add(ListTemplate.NUMBER_DEFAULT);

        List list = doc.getLists().get(0);

        // Set true to specify that the list has to be restarted at each section.
        list.isRestartAtEachSection(true);

        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.getListFormat().setList(list);

        for (int i = 1; i <= 45; i++)
        {
            builder.write($"List Item {i}\n");

            // Insert section break.
            if (i == 15 || i == 30)
                builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);
        }

        // IsRestartAtEachSection will be written only if compliance is higher then OoxmlComplianceCore.Ecma376
        OoxmlSaveOptions options = new OoxmlSaveOptions();
        {
            options.setCompliance(OoxmlCompliance.ISO_29500_2008_TRANSITIONAL);
        }

        doc.save(getArtifactsDir() + "RestartingDocumentList.docx", options);
        //ExEnd
    }

    @Test
    public void updatingLastSavedTimeDocument() throws Exception
    {
        //ExStart
        //ExFor:SaveOptions.UpdateLastSavedTimeProperty
        //ExSummary:Shows how to update a document time property when you want to save it
        Document doc = new Document(getMyDir() + "Document.doc");

        // Get last saved time
        DateTime documentTimeBeforeSave = doc.getBuiltInDocumentProperties().getLastSavedTimeInternal();

        OoxmlSaveOptions saveOptions = new OoxmlSaveOptions();
        {
            saveOptions.setUpdateLastSavedTimeProperty(true);
        }
        //ExEnd

        MemoryStream dstStream = new MemoryStream();
        doc.save(dstStream, saveOptions);

        DateTime documentTimeAfterSave = doc.getBuiltInDocumentProperties().getLastSavedTimeInternal();
        msAssert.areNotEqual(documentTimeBeforeSave, documentTimeAfterSave);
    }

    @Test
    public void keepLegacyControlChars() throws Exception
    {
        //ExStart
        //ExFor:OoxmlSaveOptions.KeepLegacyControlChars
        //ExSummary:Shows how to support legacy control characters when converting to .docx
        Document doc = new Document(getMyDir() + "OoxmlSaveOptions.KeepLegacyControlChars.doc");
 
        // Note that only one legacy character (ShortDateTime) is supported which declared in the "DOC" format
        OoxmlSaveOptions so = new OoxmlSaveOptions();
        so.setKeepLegacyControlChars(true);
 
        doc.save(getArtifactsDir() + "OoxmlSaveOptions.KeepLegacyControlChars.docx", so);
        //ExEnd
    }
}
