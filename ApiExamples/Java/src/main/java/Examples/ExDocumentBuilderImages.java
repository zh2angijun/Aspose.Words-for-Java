package Examples;

//////////////////////////////////////////////////////////////////////////
// Copyright (c) 2001-2019 Aspose Pty Ltd. All Rights Reserved.
//
// This file is part of Aspose.Words. The source code in this file
// is only intended as a supplement to the documentation, and is provided
// "as is", without warranty of any kind, either expressed or implied.
//////////////////////////////////////////////////////////////////////////

import com.aspose.words.*;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Test
public class ExDocumentBuilderImages extends ApiExampleBase {
    @Test
    public void insertImageFromStream() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.InsertImage(Stream)
        //ExSummary:Shows different solutions of how to import an image into a document from a stream.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        InputStream stream = new FileInputStream(getImageDir() + "Aspose.Words.gif");
        try {
            builder.writeln("Inserted image from stream: ");
            builder.insertImage(stream);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }

        doc.save(getArtifactsDir() + "InsertImageFromStream.docx");
        //ExEnd
    }

    @Test
    public void insertImageFromStreamWithACustomSize() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.InsertImage(Stream, Double, Double)
        //ExSummary:Shows different solutions of how to import an image with a custom size into a document from a stream.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        InputStream stream = new FileInputStream(getImageDir() + "Aspose.Words.gif");
        try {
            builder.writeln("\nInserted image from stream with a custom size: ");
            builder.insertImage(stream, ConvertUtil.pixelToPoint(250.0), ConvertUtil.pixelToPoint(144.0));
        } finally {
            if (stream != null) {
                stream.close();
            }
        }

        doc.save(getArtifactsDir() + "InsertImageFromStreamWithACustomSize.docx");
        //ExEnd
    }

    @Test
    public void insertImageFromStreamUsingRelativePositions() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.InsertImage(Stream, RelativeHorizontalPosition, Double, RelativeVerticalPosition, Double, Double, Double, WrapType)
        //ExSummary:Shows different solutions of how to import an image using relative positions into a document from a stream.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        InputStream stream = new FileInputStream(getImageDir() + "Aspose.Words.gif");
        try {
            builder.writeln("\nInserted image from stream using relative positions: ");
            builder.insertImage(stream, RelativeHorizontalPosition.MARGIN, 100.0, RelativeVerticalPosition.MARGIN,
                    100.0, 200.0, 100.0, WrapType.SQUARE);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }

        doc.save(getArtifactsDir() + "InsertImageFromStreamUsingRelativePositions.docx");
        //ExEnd
    }

    @Test
    public void insertImageFromString() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.InsertImage(String)
        //ExFor:DocumentBuilder.InsertImage(String, Double, Double)
        //ExFor:DocumentBuilder.InsertImage(String, RelativeHorizontalPosition, Double, RelativeVerticalPosition, Double, Double, Double, WrapType)
        //ExSummary:Shows different solutions of how to import an image into a document from a string.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.writeln("\nInserted image from string: ");
        builder.insertImage(getImageDir() + "Aspose.Words.gif");

        builder.writeln("\nInserted image from string with a custom size: ");
        builder.insertImage(getImageDir() + "Aspose.Words.gif", ConvertUtil.pixelToPoint(250.0),
                ConvertUtil.pixelToPoint(144.0));

        builder.writeln("\nInserted image from string using relative positions: ");
        builder.insertImage(getImageDir() + "Aspose.Words.gif", RelativeHorizontalPosition.MARGIN, 100.0,
                RelativeVerticalPosition.MARGIN, 100.0, 200.0, 100.0, WrapType.SQUARE);

        doc.save(getArtifactsDir() + "InsertImageFromString.docx");
        //ExEnd
    }

    @Test
    public void insertImageFromImageClass() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.InsertImage(Image)
        //ExFor:DocumentBuilder.InsertImage(Image, Double, Double)
        //ExFor:DocumentBuilder.InsertImage(Image, RelativeHorizontalPosition, Double, RelativeVerticalPosition, Double, Double, Double, WrapType)
        //ExSummary:Shows different solutions of how to import an image into a document from Image class.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        BufferedImage image = ImageIO.read(new File(getImageDir() + "Aspose.Words.gif"));

        builder.writeln("\nInserted image from Image class: ");
        builder.insertImage(image);

        builder.writeln("\nInserted image from Image class with a custom size: ");
        builder.insertImage(image, ConvertUtil.pixelToPoint(250.0), ConvertUtil.pixelToPoint(144.0));

        builder.writeln("\nInserted image from Image class using relative positions: ");
        builder.insertImage(image, RelativeHorizontalPosition.MARGIN, 100.0, RelativeVerticalPosition.MARGIN,
                100.0, 200.0, 100.0, WrapType.SQUARE);

        doc.save(getArtifactsDir() + "InsertImageFromImageClass.docx");
        //ExEnd
    }

    @Test
    public void insertImageFromByteArray() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.InsertImage(Byte[])
        //ExFor:DocumentBuilder.InsertImage(Byte[], Double, Double)
        //ExFor:DocumentBuilder.InsertImage(Byte[], RelativeHorizontalPosition, Double, RelativeVerticalPosition, Double, Double, Double, WrapType)
        //ExSummary:Shows different solutions of how to import an image into a document from a byte array.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        byte[] imageByteArray = DocumentHelper.getBytesFromStream(new FileInputStream(getImageDir() + "Aspose.Words.gif"));

        builder.writeln("\nInserted image from byte array: ");
        builder.insertImage(imageByteArray);

        builder.writeln("\nInserted image from byte array with a custom size: ");
        builder.insertImage(imageByteArray, ConvertUtil.pixelToPoint(250.0), ConvertUtil.pixelToPoint(144.0));

        builder.writeln("\nInserted image from byte array using relative positions: ");
        builder.insertImage(imageByteArray, RelativeHorizontalPosition.MARGIN, 100.0, RelativeVerticalPosition.MARGIN,
                100.0, 200.0, 100.0, WrapType.SQUARE);

        doc.save(getArtifactsDir() + "InsertImageFromByteArray.docx");
        //ExEnd
    }
}

