package Examples;

//////////////////////////////////////////////////////////////////////////
// Copyright (c) 2001-2019 Aspose Pty Ltd. All Rights Reserved.
//
// This file is part of Aspose.Words. The source code in this file
// is only intended as a supplement to the documentation, and is provided
// "as is", without warranty of any kind, either expressed or implied.
//////////////////////////////////////////////////////////////////////////

import com.aspose.words.Document;
import com.aspose.words.PclSaveOptions;
import com.aspose.words.Section;
import org.testng.annotations.Test;

public class ExPclSaveOptions extends ApiExampleBase {
    @Test
    public void rasterizeElements() throws Exception {
        //ExStart
        //ExFor:PclSaveOptions
        //ExFor:PclSaveOptions.RasterizeTransformedElements
        //ExSummary:Shows how rasterized or not transformed elements before saving.
        Document doc = new Document(getMyDir() + "Document.EpubConversion.doc");

        PclSaveOptions saveOptions = new PclSaveOptions();
        saveOptions.setRasterizeTransformedElements(true);

        doc.save(getArtifactsDir() + "Document.EpubConversion.pcl", saveOptions);
        //ExEnd
    }

    @Test
    public void setPrinterFont() throws Exception {
        //ExStart
        //ExFor:PclSaveOptions.AddPrinterFont(string, string)
        //ExFor:PclSaveOptions.FallbackFontName
        //ExSummary:Shows how to add information about font that is uploaded to the printer and set the font that will be used if no expected font is found in printer and built-in fonts collections.
        Document doc = new Document(getMyDir() + "Document.EpubConversion.doc");

        PclSaveOptions saveOptions = new PclSaveOptions();
        saveOptions.addPrinterFont("Courier", "Courier");
        saveOptions.setFallbackFontName("Times New Roman");

        doc.save(getArtifactsDir() + "Document.EpubConversion.pcl", saveOptions);
        //ExEnd
    }

    @Test(enabled = false, description = "This test is manual check that PaperTray information are preserved in pcl document.")
    public void getPreservedPaperTrayInformation() throws Exception {
        Document doc = new Document(getMyDir() + "Document.EpubConversion.doc");

        // Paper tray information is now preserved when saving document to PCL format.
        // Following information is transferred from document's model to PCL file.
        for (Section section : doc.getSections()) {
            section.getPageSetup().setFirstPageTray(15);
            section.getPageSetup().setOtherPagesTray(12);
        }

        doc.save(getArtifactsDir() + "Document.EpubConversion.pcl");
    }
}
