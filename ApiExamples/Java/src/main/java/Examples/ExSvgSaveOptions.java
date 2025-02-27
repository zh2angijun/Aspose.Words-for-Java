// Copyright (c) 2001-2019 Aspose Pty Ltd. All Rights Reserved.
//
// This file is part of Aspose.Words. The source code in this file
// is only intended as a supplement to the documentation, and is provided
// "as is", without warranty of any kind, either expressed or implied.
//////////////////////////////////////////////////////////////////////////

package Examples;

import com.aspose.words.*;
import org.testng.annotations.Test;

import java.io.File;
import java.text.MessageFormat;

@Test
public class ExSvgSaveOptions extends ApiExampleBase {
    @Test
    public void saveLikeImage() throws Exception {
        //ExStart
        //ExFor:SvgSaveOptions.FitToViewPort
        //ExFor:SvgSaveOptions.ShowPageBorder
        //ExFor:SvgSaveOptions.TextOutputMode
        //ExFor:SvgTextOutputMode
        //ExSummary:Shows how to mimic the properties of images when converting a .docx document to .svg.
        Document doc = new Document(getMyDir() + "Document.docx");

        // Configure the SvgSaveOptions object to save with no page borders or selectable text
        SvgSaveOptions options = new SvgSaveOptions();
        {
            options.setFitToViewPort(true);
            options.setShowPageBorder(false);
            options.setTextOutputMode(SvgTextOutputMode.USE_PLACED_GLYPHS);
        }

        doc.save(getArtifactsDir() + "SaveLikeImage.svg", options);
        //ExEnd
    }

    //ExStart
    //ExFor:SvgSaveOptions
    //ExFor:SvgSaveOptions.ExportEmbeddedImages
    //ExFor:SvgSaveOptions.ResourceSavingCallback
    //ExFor:SvgSaveOptions.ResourcesFolder
    //ExFor:SvgSaveOptions.ResourcesFolderAlias
    //ExFor:SvgSaveOptions.SaveFormat
    //ExSummary:Shows how to manipulate and print the URIs of linked resources created during conversion of a document to .svg.
    @Test //ExSkip
    public void svgResourceFolder() throws Exception {
        // Open a document which contains images
        Document doc = new Document(getMyDir() + "Rendering.doc");

        SvgSaveOptions options = new SvgSaveOptions();
        {
            options.setSaveFormat(SaveFormat.SVG);
            options.setExportEmbeddedImages(false);
            options.setResourcesFolder(getArtifactsDir() + "SvgResourceFolder");
            options.setResourcesFolderAlias(getArtifactsDir() + "SvgResourceFolderAlias");
            options.setShowPageBorder(false);

            options.setResourceSavingCallback(new ResourceUriPrinter());
        }

        new File(options.getResourcesFolderAlias()).mkdir();

        doc.save(getArtifactsDir() + "SvgResourceFolder.svg", options);
    }

    /// <summary>
    /// Counts and prints URIs of resources contained by as they are converted to .svg
    /// </summary>
    private static class ResourceUriPrinter implements IResourceSavingCallback {
        public void resourceSaving(ResourceSavingArgs args) {
            // If we set a folder alias in the SaveOptions object, it will be printed here
            System.out.println(MessageFormat.format("Resource #{0} \"{1}\"", ++mSavedResourceCount, args.getResourceFileName()));
            System.out.println("\t" + args.getResourceFileUri());
        }

        private int mSavedResourceCount;
    }
    //ExEnd
}
