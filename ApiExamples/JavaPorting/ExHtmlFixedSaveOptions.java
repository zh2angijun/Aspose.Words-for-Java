// Copyright (c) 2001-2019 Aspose Pty Ltd. All Rights Reserved.
//
// This file is part of Aspose.Words. The source code in this file
// is only intended as a supplement to the documentation, and is provided
// "as is", without warranty of any kind, either expressed or implied.
//////////////////////////////////////////////////////////////////////////

package ApiExamples;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import org.testng.annotations.Test;
import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.HtmlFixedSaveOptions;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.words.HtmlFixedPageHorizontalAlignment;
import org.testng.Assert;
import com.aspose.words.ExportFontFormat;
import com.aspose.words.IResourceSavingCallback;
import com.aspose.words.ResourceSavingArgs;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.Path;
import com.aspose.words.SaveFormat;
import com.aspose.ms.System.IO.Directory;
import com.aspose.ms.System.msConsole;
import com.aspose.ms.System.IO.FileStream;
import com.aspose.ms.System.IO.FileMode;


@Test
class ExHtmlFixedSaveOptions !Test class should be public in Java to run, please fix .Net source!  extends ApiExampleBase
{
    @Test
    public void useEncoding() throws Exception
    {
        //ExStart
        //ExFor:HtmlFixedSaveOptions.Encoding
        //ExSummary:Shows how to set encoding while exporting to HTML.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        
        builder.writeln("Hello World!");

        HtmlFixedSaveOptions htmlFixedSaveOptions = new HtmlFixedSaveOptions();
        {
            htmlFixedSaveOptions.setEncoding(new ASCIIEncoding());
        }

        doc.save(getArtifactsDir() + "UseEncoding.html", htmlFixedSaveOptions);
        //ExEnd
    }

    // Note: Test doesn't contain validation result, because it's may take a lot of time for assert result
    // For validation result, you can save the document to HTML file and check out with notepad++, that file encoding will be correctly displayed (Encoding tab in Notepad++)
    @Test
    public void encodingUsingGetEncoding() throws Exception
    {
        Document doc = DocumentHelper.createDocumentFillWithDummyText();

        HtmlFixedSaveOptions htmlFixedSaveOptions = new HtmlFixedSaveOptions();
        {
            htmlFixedSaveOptions.setEncoding(Encoding.getEncoding("utf-16"));
        }

        doc.save(getArtifactsDir() + "EncodingUsingGetEncoding.html", htmlFixedSaveOptions);
    }

    // Note: Test doesn't contain validation result, because it's may take a lot of time for assert result
    // For validation result, you can save the document to HTML file and check out with notepad++, that file encoding will be correctly displayed (Encoding tab in Notepad++)
    @Test
    public void exportEmbeddedObjects() throws Exception
    {
        //ExStart
        //ExFor:HtmlFixedSaveOptions.ExportEmbeddedCss
        //ExFor:HtmlFixedSaveOptions.ExportEmbeddedFonts
        //ExFor:HtmlFixedSaveOptions.ExportEmbeddedImages
        //ExFor:HtmlFixedSaveOptions.ExportEmbeddedSvg
        //ExSummary:Shows how to export embedded objects into HTML file.
        Document doc = DocumentHelper.createDocumentFillWithDummyText();

        HtmlFixedSaveOptions htmlFixedSaveOptions = new HtmlFixedSaveOptions();
        {
            htmlFixedSaveOptions.setExportEmbeddedCss(true);
            htmlFixedSaveOptions.setExportEmbeddedFonts(true);
            htmlFixedSaveOptions.setExportEmbeddedImages(true);
            htmlFixedSaveOptions.setExportEmbeddedSvg(true);
        }

        doc.save(getArtifactsDir() + "ExportEmbeddedObjects.html", htmlFixedSaveOptions);
        //ExEnd
    }

    @Test
    public void exportFormFields() throws Exception
    {
        //ExStart
        //ExFor:HtmlFixedSaveOptions.ExportFormFields
        //ExSummary:Show how to exporting form fields from a document into HTML file.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.insertCheckBox("CheckBox", false, 15);

        HtmlFixedSaveOptions htmlFixedSaveOptions = new HtmlFixedSaveOptions();
        {
            htmlFixedSaveOptions.setExportFormFields(true);
        }

        doc.save(getArtifactsDir() + "ExportFormFields.html", htmlFixedSaveOptions);
        //ExEnd
    }

    @Test
    public void addCssClassNamesPrefix() throws Exception
    {
        //ExStart
        //ExFor:HtmlFixedSaveOptions.CssClassNamesPrefix
        //ExFor:HtmlFixedSaveOptions.SaveFontFaceCssSeparately
        //ExSummary:Shows how to add prefix to all class names in css file.
        Document doc = new Document(getMyDir() + "Bookmark.doc");

        HtmlFixedSaveOptions htmlFixedSaveOptions = new HtmlFixedSaveOptions();
        {
            htmlFixedSaveOptions.setCssClassNamesPrefix("test");
            htmlFixedSaveOptions.setSaveFontFaceCssSeparately(true);
        }

        doc.save(getArtifactsDir() + "cssPrefix_Out.html", htmlFixedSaveOptions);
        //ExEnd

        DocumentHelper.findTextInFile(getArtifactsDir() + "cssPrefix_Out/styles.css", "test");
    }

    @Test
    public void horizontalAlignment() throws Exception
    {
        //ExStart
        //ExFor:HtmlFixedSaveOptions.PageHorizontalAlignment
        //ExFor:HtmlFixedPageHorizontalAlignment
        //ExSummary:Shows how to set the horizontal alignment of pages in HTML file.
        Document doc = new Document(getMyDir() + "Bookmark.doc");

        HtmlFixedSaveOptions htmlFixedSaveOptions = new HtmlFixedSaveOptions();
        {
            htmlFixedSaveOptions.setPageHorizontalAlignment(HtmlFixedPageHorizontalAlignment.LEFT);
        }

        doc.save(getArtifactsDir() + "HtmlFixedPageHorizontalAlignment.html", htmlFixedSaveOptions);
        //ExEnd
    }

    @Test
    public void pageMargins() throws Exception
    {
        //ExStart
        //ExFor:HtmlFixedSaveOptions.PageMargins
        //ExSummary:Shows how to set the margins around pages in HTML file.
        Document doc = new Document(getMyDir() + "Bookmark.doc");

        HtmlFixedSaveOptions saveOptions = new HtmlFixedSaveOptions();
        {
            saveOptions.setPageMargins(10.0);
        }

        doc.save(getArtifactsDir() + "HtmlFixedPageMargins.html", saveOptions);
        //ExEnd
    }

    @Test
    public void pageMarginsException()
    {
        HtmlFixedSaveOptions saveOptions = new HtmlFixedSaveOptions();
        Assert.That(() => saveOptions.setPageMargins(-1), Throws.<IllegalArgumentException>TypeOf());
    }

    @Test
    public void optimizeGraphicsOutput() throws Exception
    {
        //ExStart
        //ExFor:FixedPageSaveOptions.OptimizeOutput
        //ExSummary:Shows how to optimize document objects while saving to html.
        Document doc = new Document(getMyDir() + "HtmlFixedSaveOptions.OptimizeGraphicsOutput.doc");

        HtmlFixedSaveOptions saveOptions = new HtmlFixedSaveOptions(); { saveOptions.setOptimizeOutput(false); }

        doc.save(getArtifactsDir() + "HtmlFixedPageMargins.html", saveOptions);
        //ExEnd
    }

    //ExStart
    //ExFor:HtmlFixedSaveOptions.UseTargetMachineFonts
    //ExFor:IResourceSavingCallback
    //ExFor:IResourceSavingCallback.ResourceSaving(ResourceSavingArgs)
    //ExSummary: Shows how used target machine fonts to display the document.
    @Test //ExSkip
    public void usingMachineFonts() throws Exception
    {
        Document doc = new Document(getMyDir() + "Font.DisappearingBulletPoints.doc");

        HtmlFixedSaveOptions saveOptions = new HtmlFixedSaveOptions();
        {
            saveOptions.setUseTargetMachineFonts(true);
            saveOptions.setFontFormat(ExportFontFormat.TTF);
            saveOptions.setExportEmbeddedFonts(false);
            saveOptions.setResourceSavingCallback(new ResourceSavingCallback());
        }

        doc.save(getArtifactsDir() + "UseMachineFonts.html", saveOptions);
    }

    private static class ResourceSavingCallback implements IResourceSavingCallback
    {
        /// <summary>
        /// Called when Aspose.Words saves an external resource to fixed page HTML or SVG.
        /// </summary>
        public void resourceSaving(ResourceSavingArgs args) throws Exception
        {
            args.ResourceStream = new MemoryStream();
            args.setKeepResourceStreamOpen(true);

            String extension = Path.getExtension(args.getResourceFileName());
            switch (gStringSwitchMap.of(extension))
            {
                case /*".ttf"*/0:
                case /*".woff"*/1:
                {
                    Assert.fail(
                        "'ResourceSavingCallback' is not fired for fonts when 'UseTargetMachineFonts' is true");
                    break;
                }
            }
        }
    }
    //ExEnd

    //ExStart
    //ExFor:HtmlFixedSaveOptions
    //ExFor:HtmlFixedSaveOptions.ResourceSavingCallback
    //ExFor:HtmlFixedSaveOptions.ResourcesFolder
    //ExFor:HtmlFixedSaveOptions.ResourcesFolderAlias
    //ExFor:HtmlFixedSaveOptions.ShowPageBorder
    //ExSummary:Shows how to print the URIs of linked resources created during conversion of a document to fixed-form .html.
    @Test //ExSkip
    public void htmlFixedResourceFolder() throws Exception
    {
        // Open a document which contains images
        Document doc = new Document(getMyDir() + "Rendering.doc");

        HtmlFixedSaveOptions options = new HtmlFixedSaveOptions();
        {
            options.setSaveFormat(SaveFormat.HTML_FIXED);
            options.setExportEmbeddedImages(false);
            options.setResourcesFolder(getArtifactsDir() + "HtmlFixedResourceFolder");
            options.setResourcesFolderAlias(getArtifactsDir() + "HtmlFixedResourceFolderAlias");
            options.setShowPageBorder(false);
            options.setResourceSavingCallback(new ResourceUriPrinter());
        }

        // A folder specified by ResourcesFolderAlias will contain the resources instead of ResourcesFolder
        // We must ensure the folder exists before the streams can put their resources into it
        Directory.createDirectory(options.getResourcesFolderAlias());

        doc.save(getArtifactsDir() + "HtmlFixedResourceFolder.html", options);
    }

    /// <summary>
    /// Counts and prints URIs of resources contained by as they are converted to fixed .Html
    /// </summary>
    private static class ResourceUriPrinter implements IResourceSavingCallback
    {
        public void /*IResourceSavingCallback.*/resourceSaving(ResourceSavingArgs args) throws Exception
        {
            // If we set a folder alias in the SaveOptions object, it will be printed here
            msConsole.writeLine($"Resource #{++mSavedResourceCount} \"{args.ResourceFileName}\"");

            String extension = Path.getExtension(args.getResourceFileName());
            switch (gStringSwitchMap.of(extension))
            {
                case /*".ttf"*/0:
                case /*".woff"*/1:
                {
                    // By default 'ResourceFileUri' used system folder for fonts
                    // To avoid problems across platforms you must explicitly specify the path for the fonts
                    args.setResourceFileUri(getArtifactsDir() + Path.DirectorySeparatorChar + args.getResourceFileName());
                    break;
                }
            }
            msConsole.writeLine("\t" + args.getResourceFileUri());

            // If we specified a ResourcesFolderAlias we will also need to redirect each stream to put its resource in that folder
            args.ResourceStream = new FileStream(args.getResourceFileUri(), FileMode.CREATE);
            args.setKeepResourceStreamOpen(false);
        }

        private int mSavedResourceCount;
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		".ttf",
		".woff"
	);

    //ExEnd
}
