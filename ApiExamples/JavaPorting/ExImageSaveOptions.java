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
import com.aspose.words.ImageSaveOptions;
import com.aspose.words.SaveFormat;
import com.aspose.words.GraphicsQualityOptions;
import com.aspose.ms.System.Drawing.Drawing2D.SmoothingMode;
import com.aspose.ms.System.Drawing.Text.TextRenderingHint;
import com.aspose.ms.System.Drawing.Drawing2D.CompositingMode;
import com.aspose.ms.System.Drawing.Drawing2D.InterpolationMode;
import com.aspose.words.ImageColorMode;
import com.aspose.words.ImagePixelFormat;
import com.aspose.words.TiffCompression;
import com.aspose.words.ImageBinarizationMethod;



@Test
class ExImageSaveOptions !Test class should be public in Java to run, please fix .Net source!  extends ApiExampleBase
{
    @Test
    public void useGdiEmfRenderer() throws Exception
    {
        //ExStart
        //ExFor:ImageSaveOptions.UseGdiEmfRenderer
        //ExSummary:Shows how to save metafiles directly without using GDI+ to EMF.
        Document doc = new Document(getMyDir() + "SaveOptions.MyriadPro.docx");

        ImageSaveOptions saveOptions = new ImageSaveOptions(SaveFormat.EMF);
        {
            saveOptions.setUseGdiEmfRenderer(false);
        }

        doc.save(getArtifactsDir() + "SaveOptions.UseGdiEmfRenderer.docx", saveOptions);
        //ExEnd
    }

    @Test
    public void saveIntoGif() throws Exception
    {
        //ExStart
        //ExFor:ImageSaveOptions.PageIndex
        //ExSummary:Shows how to save specific document page as image file.
        Document doc = new Document(getMyDir() + "SaveOptions.MyriadPro.docx");

        ImageSaveOptions saveOptions = new ImageSaveOptions(SaveFormat.GIF);
        {
            saveOptions.setPageIndex(0); // Define which page will save
        }

        doc.save(getArtifactsDir() + "SaveOptions.MyraidPro.gif", saveOptions);
        //ExEnd
    }

    @Test
    public void graphicsQuality() throws Exception
    {
        //ExStart
        //ExFor:GraphicsQualityOptions
        //ExFor:GraphicsQualityOptions.CompositingMode
        //ExFor:GraphicsQualityOptions.CompositingQuality
        //ExFor:GraphicsQualityOptions.InterpolationMode
        //ExFor:GraphicsQualityOptions.StringFormat
        //ExFor:GraphicsQualityOptions.SmoothingMode
        //ExFor:GraphicsQualityOptions.TextRenderingHint
        //ExSummary:Shows how to set render quality options when converting documents to image formats. 
        Document doc = new Document(getMyDir() + "SaveOptions.MyriadPro.docx");

        GraphicsQualityOptions qualityOptions = new GraphicsQualityOptions();
        {
            qualityOptions.setSmoothingMode(SmoothingMode.ANTI_ALIAS);
            qualityOptions.setTextRenderingHint(TextRenderingHint.CLEAR_TYPE_GRID_FIT);
            qualityOptions.setCompositingMode(CompositingMode.SOURCE_OVER);
            qualityOptions.setCompositingQuality(CompositingQuality.HighQuality);
            qualityOptions.setInterpolationMode(InterpolationMode.HIGH);
            qualityOptions.setStringFormat(StringFormat.GenericTypographic);
        }

        ImageSaveOptions saveOptions = new ImageSaveOptions(SaveFormat.JPEG);
        saveOptions.setGraphicsQualityOptions(qualityOptions);

        doc.save(getArtifactsDir() + "SaveOptions.GraphicsQuality.jpeg", saveOptions);
        //ExEnd
    }

    @Test (groups = "SkipMono")
    public void converImageColorsToBlackAndWhite() throws Exception
    {
        //ExStart
        //ExFor:ImageSaveOptions.ImageColorMode
        //ExFor:ImageSaveOptions.PixelFormat
        //ExSummary:Show how to convert document images to black and white with 1 bit per pixel
        Document doc = new Document(getMyDir() + "ImageSaveOptions.BlackAndWhite.docx");

        ImageSaveOptions imageSaveOptions = new ImageSaveOptions(SaveFormat.PNG);
        imageSaveOptions.setImageColorMode(ImageColorMode.BLACK_AND_WHITE);
        imageSaveOptions.setPixelFormat(ImagePixelFormat.FORMAT_1_BPP_INDEXED);
        
        doc.save(getArtifactsDir() + "ImageSaveOptions.BlackAndWhite.png", imageSaveOptions);
        //ExEnd
    }

    @Test
    public void thresholdForFloydSteinbergDithering() throws Exception
    {
        //ExStart
        //ExFor:ImageSaveOptions.ThresholdForFloydSteinbergDithering
        //ExSummary: Shows how to control the threshold for TIFF binarization in the Floyd-Steinberg method
        Document doc = new Document (getMyDir() + "ImagesSaveOptions.ThresholdForFloydSteinbergDithering.docx");

        ImageSaveOptions options = new ImageSaveOptions(SaveFormat.TIFF);
        {
            options.setTiffCompression(TiffCompression.CCITT_3);
            options.setImageColorMode(ImageColorMode.GRAYSCALE);
            options.setTiffBinarizationMethod(ImageBinarizationMethod.FLOYD_STEINBERG_DITHERING);
            options.setThresholdForFloydSteinbergDithering((byte) 254); // The default value of this property is 128. The higher value, the darker image.
        }

        doc.save(getArtifactsDir() + "ImagesSaveOptions.ThresholdForFloydSteinbergDithering.tiff", options);
        //ExEnd
    }
}
