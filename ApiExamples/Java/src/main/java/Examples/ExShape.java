package Examples;

//////////////////////////////////////////////////////////////////////////
// Copyright (c) 2001-2019 Aspose Pty Ltd. All Rights Reserved.
//
// This file is part of Aspose.Words. The source code in this file
// is only intended as a supplement to the documentation, and is provided
// "as is", without warranty of any kind, either expressed or implied.
//////////////////////////////////////////////////////////////////////////

import com.aspose.words.*;
import com.aspose.words.Shape;
import com.aspose.words.Stroke;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.UUID;

/**
 * Examples using shapes in documents.
 */
public class ExShape extends ApiExampleBase {
    @Test
    public void insertShape() throws Exception {
        //ExStart
        //ExFor:ShapeBase.AlternativeText
        //ExFor:ShapeBase.Name
        //ExFor:ShapeBase.Font
        //ExFor:ShapeBase.CanHaveImage
        //ExFor:ShapeBase.ParentParagraph
        //ExFor:ShapeBase.Rotation
        //ExSummary:Shows how to insert shapes.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Insert a cube and set its name
        Shape shape = builder.insertShape(ShapeType.CUBE, 150.0, 150.0);
        shape.setName("MyCube");

        // We can also set the alt text like this
        // This text will be found in Format AutoShape > Alt Text
        shape.setAlternativeText("Alt text for MyCube.");

        // Insert a text box
        shape = builder.insertShape(ShapeType.TEXT_BOX, 300.0, 50.0);
        shape.getFont().setName("Times New Roman");

        // Move the builder into the text box and write text
        builder.moveTo(shape.getLastParagraph());
        builder.write("Hello world!");

        // Move the builder out of the text box back into the main document
        builder.moveTo(shape.getParentParagraph());

        // Insert a shape with an image
        shape = builder.insertImage(getImageDir() + "Aspose.Words.gif");
        Assert.assertTrue(shape.canHaveImage());
        Assert.assertTrue(shape.hasImage());

        // Rotate the image
        shape.setRotation(45.0);

        doc.save(getArtifactsDir() + "Shape.InsertShapes.docx");
        //ExEnd
    }

    @Test
    public void shapeCoords() throws Exception {
        //ExStart
        //ExFor:ShapeBase.DistanceBottom
        //ExFor:ShapeBase.DistanceLeft
        //ExFor:ShapeBase.DistanceRight
        //ExFor:ShapeBase.DistanceTop
        //ExSummary:Shows how to set the wrapping distance for text that surrounds a shape.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Insert a rectangle and get the text to wrap tightly around its bounds
        Shape shape = builder.insertShape(ShapeType.RECTANGLE, 150.0, 150.0);
        shape.setWrapType(WrapType.TIGHT);

        // Set the minimum distance between the shape and surrounding text
        shape.setDistanceTop(40.0);
        shape.setDistanceBottom(40.0);
        shape.setDistanceLeft(40.0);
        shape.setDistanceRight(40.0);

        // Move the shape closer to the centre of the page
        shape.setLeft(100.0);
        shape.setTop(100.0);

        // Rotate the shape
        shape.setRotation(60.0);

        // Add text that the shape will push out of the way
        for (int i = 0; i < 500; i++) {
            builder.write("text ");
        }

        doc.save(getArtifactsDir() + "Shape.ShapeCoords.docx");
        //ExEnd
    }

    @Test
    public void insertGroupShape() throws Exception {
        //ExStart
        //ExFor:ShapeBase.AnchorLocked
        //ExFor:ShapeBase.IsTopLevel
        //ExFor:ShapeBase.CoordOrigin
        //ExFor:ShapeBase.CoordSize
        //ExFor:ShapeBase.LocalToParent(PointF)
        //ExSummary:Shows how to create and work with a group of shapes.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Every GroupShape is top level
        GroupShape group = new GroupShape(doc);
        Assert.assertTrue(group.isGroup());
        Assert.assertTrue(group.isTopLevel());

        // And it is a floating shape too, so we can set its coordinates independently of the text
        Assert.assertEquals(group.getWrapType(), WrapType.NONE);

        // Make it a floating shape
        group.setWrapType(WrapType.NONE);

        // Top level shapes can have this property changed
        group.setAnchorLocked(true);

        // Set the XY coordinates of the shape group and the size of its containing block, as it appears on the page
        group.setBounds(new Rectangle2D.Float(100f, 50f, 200f, 100f));

        // Set the scale of the inner coordinates of the shape group
        // These values mean that the bottom right corner of the 200x100 outer block we set before
        // will be at x = 2000 and y = 1000, or 2000 units from the left and 1000 units from the top
        group.setCoordSize(new Dimension(2000, 1000));

        // The coordinate origin of a shape group is x = 0, y = 0 by default, which is the top left corner
        // If we insert a child shape and set its distance from the left to 2000 and the distance from the top to 1000,
        // its origin will be at the bottom right corner of the shape group
        // We can offset the coordinate origin by setting the CoordOrigin attribute
        // In this instance, we move the origin to the centre of the shape group
        group.setCoordOrigin(new Point(-1000, -500));

        // Populate the shape group with child shapes
        // First, insert a rectangle
        Shape subShape = new Shape(doc, ShapeType.RECTANGLE);
        subShape.setWidth(500.0);
        subShape.setHeight(700.0);

        // Place its top left corner at the parent group's coordinate origin, which is currently at its centre
        subShape.setLeft(0.0);
        subShape.setTop(0.0);

        // Add the rectangle to the group
        group.appendChild(subShape);

        // Insert a triangle
        subShape = new Shape(doc, ShapeType.TRIANGLE);
        subShape.setWidth(400.0);
        subShape.setHeight(400.0);

        // Place its origin at the bottom right corner of the group
        subShape.setLeft(1000.0);
        subShape.setTop(500.0);

        // The offset between this child shape and parent group can be seen here
        Assert.assertEquals(subShape.localToParent(new Point2D.Float(0f, 0f)), new Point2D.Float(1000f, 500f));

        // Add the triangle to the group
        group.appendChild(subShape);

        // Child shapes of a group shape are not top level
        Assert.assertFalse(subShape.isTopLevel());

        // Finally, insert the group into the document and save
        builder.insertNode(group);
        doc.save(getArtifactsDir() + "Shape.InsertGroupShape.docx");
        //ExEnd
    }

    @Test
    public void deleteAllShapes() throws Exception {
        Document doc = new Document(getMyDir() + "Shape.DeleteAllShapes.doc");

        //ExStart
        //ExFor:Shape
        //ExSummary:Shows how to delete all shapes from a document.
        // Here we get all shapes from the document node, but you can do this for any smaller
        // node too, for example delete shapes from a single section or a paragraph.
        NodeCollection shapes = doc.getChildNodes(NodeType.SHAPE, true);
        shapes.clear();

        // There could also be group shapes, they have different node type, remove them all too.
        NodeCollection groupShapes = doc.getChildNodes(NodeType.GROUP_SHAPE, true);
        groupShapes.clear();
        //ExEnd

        Assert.assertEquals(doc.getChildNodes(NodeType.SHAPE, true).getCount(), 0);
        Assert.assertEquals(doc.getChildNodes(NodeType.GROUP_SHAPE, true).getCount(), 0);
        doc.save(getArtifactsDir() + "Shape.DeleteAllShapes.doc");
    }

    @Test
    public void checkShapeInline() throws Exception {
        //ExStart
        //ExFor:ShapeBase.IsInline
        //ExSummary:Shows how to test if a shape in the document is inline or floating.
        Document doc = new Document(getMyDir() + "Shape.DeleteAllShapes.doc");

        for (Shape shape : (Iterable<Shape>) doc.getChildNodes(NodeType.SHAPE, true)) {
            if (shape.isInline()) System.out.println("Shape is inline.");
            else System.out.println("Shape is floating.");
        }
        //ExEnd

        // Verify that the first shape in the document is not inline.
        Assert.assertFalse(((Shape) doc.getChild(NodeType.SHAPE, 0, true)).isInline());
    }

    @Test
    public void lineFlipOrientation() throws Exception {
        //ExStart
        //ExFor:ShapeBase.Bounds
        //ExFor:ShapeBase.BoundsInPoints
        //ExFor:ShapeBase.FlipOrientation
        //ExFor:FlipOrientation
        //ExSummary:Shows how to create line shapes and set specific location and size.
        Document doc = new Document();

        // The lines will cross the whole page.
        float pageWidth = (float) doc.getFirstSection().getPageSetup().getPageWidth();
        float pageHeight = (float) doc.getFirstSection().getPageSetup().getPageHeight();

        // This line goes from top left to bottom right by default.
        Shape lineA = new Shape(doc, ShapeType.LINE);
        lineA.setBounds(new Rectangle2D.Float(0, 0, pageWidth, pageHeight));
        lineA.setRelativeHorizontalPosition(RelativeHorizontalPosition.PAGE);
        lineA.setRelativeVerticalPosition(RelativeVerticalPosition.PAGE);
        doc.getFirstSection().getBody().getFirstParagraph().appendChild(lineA);

        Assert.assertEquals(lineA.getBoundsInPoints(), new Rectangle2D.Float(0f, 0f, pageWidth, pageHeight));

        // This line goes from bottom left to top right because we flipped it.
        Shape lineB = new Shape(doc, ShapeType.LINE);
        lineB.setBounds(new Rectangle2D.Float(0, 0, pageWidth, pageHeight));
        lineB.setFlipOrientation(FlipOrientation.HORIZONTAL);
        lineB.setRelativeHorizontalPosition(RelativeHorizontalPosition.PAGE);
        lineB.setRelativeVerticalPosition(RelativeVerticalPosition.PAGE);

        Assert.assertEquals(new Rectangle2D.Float(0f, 0f, pageWidth, pageHeight), lineB.getBoundsInPoints());

        // Add lines to the document.
        doc.getFirstSection().getBody().getFirstParagraph().appendChild(lineB);
        doc.getFirstSection().getBody().getFirstParagraph().appendChild(lineA);

        doc.save(getArtifactsDir() + "Shape.LineFlipOrientation.doc");
        //ExEnd
    }

    @Test
    public void fill() throws Exception {
        //ExStart
        //ExFor:Shape.Fill
        //ExFor:Shape.FillColor
        //ExFor:Fill
        //ExFor:Fill.Opacity
        //ExSummary:Demonstrates how to create shapes with fill.
        DocumentBuilder builder = new DocumentBuilder();

        builder.writeln();
        builder.writeln();
        builder.writeln();
        builder.write("Some text under the shape.");

        // Create a red balloon, semitransparent.
        // The shape is floating and its coordinates are (0,0) by default, relative to the current paragraph.
        Shape shape = new Shape(builder.getDocument(), ShapeType.BALLOON);
        shape.setFillColor(Color.RED);
        shape.getFill().setOpacity(0.3);
        shape.setWidth(100);
        shape.setHeight(100);
        shape.setTop(-100);
        builder.insertNode(shape);

        builder.getDocument().save(getArtifactsDir() + "Shape.Fill.doc");
        //ExEnd
    }

    @Test
    public void getShapeAltTextTitle() throws Exception {
        //ExStart
        //ExFor:ShapeBase.Title
        //ExSummary:Shows how to get or set title of shape object.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Create test shape.
        Shape shape = new Shape(doc, ShapeType.CUBE);
        shape.setWidth(431.5);
        shape.setHeight(346.35);
        shape.setTitle("Alt Text Title");

        builder.insertNode(shape);

        ByteArrayOutputStream dstStream = new ByteArrayOutputStream();
        doc.save(dstStream, SaveFormat.DOCX);

        shape = (Shape) doc.getChild(NodeType.SHAPE, 0, true);
        System.out.println("Shape text: " + shape.getTitle());
        //ExEnd

        Assert.assertEquals(shape.getTitle(), "Alt Text Title");
    }

    @Test
    public void replaceTextboxesWithImages() throws Exception {
        //ExStart
        //ExFor:WrapSide
        //ExFor:ShapeBase.WrapSide
        //ExFor:NodeCollection
        //ExFor:CompositeNode.InsertAfter(Node, Node)
        //ExFor:NodeCollection.ToArray
        //ExSummary:Shows how to replace all textboxes with images.
        Document doc = new Document(getMyDir() + "Shape.ReplaceTextboxesWithImages.doc");

        // This gets a live collection of all shape nodes in the document.
        NodeCollection shapeCollection = doc.getChildNodes(NodeType.SHAPE, true);

        // Since we will be adding/removing nodes, it is better to copy all collection
        // into a fixed size array, otherwise iterator will be invalidated.
        Node[] shapes = shapeCollection.toArray();

        for (Node node : shapes) {
            Shape shape = (Shape) node;
            // Filter out all shapes that we don't need.
            if (shape.getShapeType() == ShapeType.TEXT_BOX) {
                // Create a new shape that will replace the existing shape.
                Shape image = new Shape(doc, ShapeType.IMAGE);

                // Load the image into the new shape.
                image.getImageData().setImage(getImageDir() + "Hammer.wmf");

                // Make new shape's position to match the old shape.
                image.setLeft(shape.getLeft());
                image.setTop(shape.getTop());
                image.setWidth(shape.getWidth());
                image.setHeight(shape.getHeight());
                image.setRelativeHorizontalPosition(shape.getRelativeHorizontalPosition());
                image.setRelativeVerticalPosition(shape.getRelativeVerticalPosition());
                image.setHorizontalAlignment(shape.getHorizontalAlignment());
                image.setVerticalAlignment(shape.getVerticalAlignment());
                image.setWrapType(shape.getWrapType());
                image.setWrapSide(shape.getWrapSide());

                // Insert new shape after the old shape and remove the old shape.
                shape.getParentNode().insertAfter(image, shape);
                shape.remove();
            }
        }

        doc.save(getArtifactsDir() + "Shape.ReplaceTextboxesWithImages.doc");
        //ExEnd
    }

    @Test
    public void createTextBox() throws Exception {
        //ExStart
        //ExFor:Shape.#ctor(DocumentBase, ShapeType)
        //ExFor:ShapeBase.ZOrder
        //ExFor:Story.FirstParagraph
        //ExFor:Shape.FirstParagraph
        //ExFor:ShapeBase.WrapType
        //ExSummary:Creates a textbox with some text and different formatting options in a new document.
        // Create a blank document.
        Document doc = new Document();

        // Create a new shape of type TextBox
        Shape textBox = new Shape(doc, ShapeType.TEXT_BOX);

        // Set some settings of the textbox itself.
        // Set the wrap of the textbox to inline
        textBox.setWrapType(WrapType.NONE);
        // Set the horizontal and vertical alignment of the text inside the shape.
        textBox.setHorizontalAlignment(HorizontalAlignment.CENTER);
        textBox.setVerticalAlignment(VerticalAlignment.TOP);

        // Set the textbox height and width.
        textBox.setHeight(50);
        textBox.setWidth(200);

        // Set the textbox in front of other shapes with a lower ZOrder
        textBox.setZOrder(2);

        // Let's create a new paragraph for the textbox manually and align it in the center. Make sure we add the new nodes to the textbox as well.
        textBox.appendChild(new Paragraph(doc));
        Paragraph para = textBox.getFirstParagraph();
        para.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);

        // Add some text to the paragraph.
        Run run = new Run(doc);
        run.setText("Content in textbox");
        para.appendChild(run);

        // Append the textbox to the first paragraph in the body.
        doc.getFirstSection().getBody().getFirstParagraph().appendChild(textBox);

        // Save the output
        doc.save(getArtifactsDir() + "Shape.CreateTextBox.doc");
        //ExEnd
    }

    @Test
    public void getActiveXControlProperties() throws Exception {
        //ExStart
        //ExFor:OleControl
        //ExFor:Ole.OleControl.IsForms2OleControl
        //ExFor:Ole.OleControl.Name
        //ExFor:OleFormat.OleControl
        //ExFor:Forms2OleControl
        //ExFor:Forms2OleControl.Caption
        //ExFor:Forms2OleControl.Value
        //ExFor:Forms2OleControl.Enabled
        //ExFor:Forms2OleControl.Type
        //ExFor:Forms2OleControl.ChildNodes
        //ExSummary: Shows how to get ActiveX control and properties from the document.
        Document doc = new Document(getMyDir() + "Shape.ActiveXObject.docx");

        // Get ActiveX control from the document
        Shape shape = (Shape) doc.getChild(NodeType.SHAPE, 0, true);
        OleControl oleControl = shape.getOleFormat().getOleControl();

        Assert.assertEquals(oleControl.getName(), null);

        // Get ActiveX control properties
        if (oleControl.isForms2OleControl()) {
            Forms2OleControl checkBox = (Forms2OleControl) oleControl;
            Assert.assertEquals(checkBox.getCaption(), "Первый");
            Assert.assertEquals(checkBox.getValue(), "0");
            Assert.assertEquals(checkBox.getEnabled(), true);
            Assert.assertEquals(checkBox.getType(), Forms2OleControlType.CHECK_BOX);
            Assert.assertEquals(checkBox.getChildNodes(), null);
        }
        //ExEnd
    }

    @Test
    public void oleControl() throws Exception {
        //ExStart
        //ExFor:OleFormat
        //ExFor:OleFormat.AutoUpdate
        //ExFor:OleFormat.IsLocked
        //ExFor:OleFormat.ProgId
        //ExFor:OleFormat.Save(Stream)
        //ExFor:OleFormat.Save(String)
        //ExFor:OleFormat.SuggestedExtension
        //ExSummary:Shows how to extract embedded OLE objects into files.
        Document doc = new Document(getMyDir() + "Shape.Ole.Spreadsheet.docm");

        // The first shape will contain an OLE object
        Shape shape = (Shape) doc.getChild(NodeType.SHAPE, 0, true);

        // This object is a Microsoft Excel spreadsheet
        OleFormat oleFormat = shape.getOleFormat();
        Assert.assertEquals(oleFormat.getProgId(), "Excel.Sheet.12");

        // Our object is neither auto updating nor locked from updates
        Assert.assertFalse(oleFormat.getAutoUpdate());
        Assert.assertEquals(oleFormat.isLocked(), false);

        // If we want to extract the OLE object by saving it into our local file system, this property can tell us the relevant file extension
        Assert.assertEquals(oleFormat.getSuggestedExtension(), ".xlsx");

        // We can save it via a stream
        OutputStream stream = new FileOutputStream(getArtifactsDir() + "OLE spreadsheet extracted via stream" + oleFormat.getSuggestedExtension());
        oleFormat.save(stream);

        // We can also save it directly to a file
        oleFormat.save(getArtifactsDir() + "OLE spreadsheet saved directly" + oleFormat.getSuggestedExtension());
        //ExEnd
    }

    @Test
    public void oleLinked() throws Exception {
        //ExStart
        //ExFor:OleFormat.IconCaption
        //ExFor:OleFormat.GetOleEntry(String)
        //ExFor:OleFormat.IsLink
        //ExFor:OleFormat.OleIcon
        //ExFor:OleFormat.SourceFullName
        //ExFor:OleFormat.SourceItem
        //ExSummary:Shows how to insert linked and unlinked OLE objects.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Embed a Microsoft Visio drawing as an OLE object into the document
        builder.insertOleObject(getImageDir() + "visio2010.vsd", "Package", false, false, null);

        // Insert a link to the file in the local file system and display it as an icon
        builder.insertOleObject(getImageDir() + "visio2010.vsd", "Package", true, true, null);

        // Both the OLE objects are stored within shapes
        NodeCollection shapes = doc.getChildNodes(NodeType.SHAPE, true);
        Assert.assertEquals(shapes.getCount(), 2);

        // If the shape is an OLE object, it will have a valid OleFormat property
        // We can use it check if it is linked or displayed as an icon, among other things
        Shape firstShape = (Shape) shapes.get(0);
        OleFormat oleFormat = firstShape.getOleFormat();
        Assert.assertEquals(oleFormat.isLink(), false);
        Assert.assertEquals(oleFormat.getOleIcon(), false);

        Shape secondShape = (Shape) shapes.get(1);
        oleFormat = secondShape.getOleFormat();
        Assert.assertEquals(oleFormat.isLink(), true);
        Assert.assertEquals(oleFormat.getOleIcon(), true);

        // Get the name or the source file and verify that the whole file is linked
        Assert.assertTrue(oleFormat.getSourceFullName().endsWith("Images" + File.separatorChar + "visio2010.vsd"));
        Assert.assertEquals(oleFormat.getSourceItem(), "");
        Assert.assertEquals(oleFormat.getIconCaption(), "Packager");

        doc.save(getArtifactsDir() + "Shape.OleLinks.docx");

        // We can get a stream with the OLE data entry, if the object has this
        byte[] oleEntryBytes = oleFormat.getOleEntry("\u0001CompObj");
        Assert.assertEquals(oleEntryBytes.length, 76);
        //ExEnd
    }

    @Test
    public void oleControlCollection() throws Exception {
        //ExStart
        //ExFor:OleFormat.Clsid
        //ExFor:Ole.Forms2OleControlCollection
        //ExFor:Ole.Forms2OleControlCollection.Count
        //ExFor:Ole.Forms2OleControlCollection.Item(Int32)
        //ExFor:Ole.NamespaceDoc
        //ExSummary:Shows how to access an OLE control embedded in a document and its child controls.
        // Open a document that contains a Microsoft Forms OLE control with child controls
        Document doc = new Document(getMyDir() + "Shape.Ole.ControlCollection.docm");

        // Get the shape that contains the control
        Shape shape = (Shape) doc.getChild(NodeType.SHAPE, 0, true);

        Assert.assertEquals(shape.getOleFormat().getClsid().toString(), "6e182020-f460-11ce-9bcd-00aa00608e01");

        Forms2OleControl oleControl = (Forms2OleControl) shape.getOleFormat().getOleControl();

        // Some controls contain child controls
        Forms2OleControlCollection oleControlCollection = oleControl.getChildNodes();

        // In this case, the child controls are 3 option buttons
        Assert.assertEquals(oleControlCollection.getCount(), 3);

        Assert.assertEquals(oleControlCollection.get(0).getCaption(), "C#");
        Assert.assertEquals(oleControlCollection.get(0).getValue(), "1");

        Assert.assertEquals(oleControlCollection.get(1).getCaption(), "Visual Basic");
        Assert.assertEquals(oleControlCollection.get(1).getValue(), "0");

        Assert.assertEquals(oleControlCollection.get(2).getCaption(), "Delphi");
        Assert.assertEquals(oleControlCollection.get(2).getValue(), "0");
        //ExEnd
    }

    @Test
    public void suggestedFileName() throws Exception {
        //ExStart
        //ExFor:OleFormat.SuggestedFileName
        //ExSummary:Shows how to get suggested file name from the object.
        Document doc = new Document(getMyDir() + "Shape.SuggestedFileName.rtf");

        // Gets the file name suggested for the current embedded object if you want to save it into a file
        Shape oleShape = (Shape) doc.getFirstSection().getBody().getChild(NodeType.SHAPE, 0, true);
        String suggestedFileName = oleShape.getOleFormat().getSuggestedFileName();
        //ExEnd

        Assert.assertEquals(suggestedFileName, "CSV.csv");
    }

    @Test
    public void objectDidNotHaveSuggestedFileName() throws Exception {
        Document doc = new Document(getMyDir() + "Shape.ActiveXObject.docx");

        Shape shape = (Shape) doc.getChild(NodeType.SHAPE, 0, true);
        Assert.assertEquals(shape.getOleFormat().getSuggestedFileName(), "");
    }

    @Test
    public void getOpaqueBoundsInPixels() throws Exception {
        Document doc = new Document(getMyDir() + "Shape.TextBox.doc");

        Shape shape = (Shape) doc.getChild(NodeType.SHAPE, 0, true);

        ImageSaveOptions imageOptions = new ImageSaveOptions(SaveFormat.JPEG);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ShapeRenderer renderer = shape.getShapeRenderer();
        renderer.save(stream, imageOptions);

        shape.remove();

        // Check that the opaque bounds and bounds have default values
        Assert.assertEquals(renderer.getOpaqueBoundsInPixels(imageOptions.getScale(), imageOptions.getVerticalResolution()).getWidth(), 250.0);
        Assert.assertEquals(renderer.getOpaqueBoundsInPixels(imageOptions.getScale(), imageOptions.getHorizontalResolution()).getHeight(), 52.0);

        Assert.assertEquals(renderer.getBoundsInPixels(imageOptions.getScale(), imageOptions.getVerticalResolution()).getWidth(), 250.0);
        Assert.assertEquals(renderer.getBoundsInPixels(imageOptions.getScale(), imageOptions.getHorizontalResolution()).getHeight(), 52.0);

        Assert.assertEquals(renderer.getOpaqueBoundsInPixels(imageOptions.getScale(), imageOptions.getHorizontalResolution()).getWidth(), 250.0);
        Assert.assertEquals(renderer.getOpaqueBoundsInPixels(imageOptions.getScale(), imageOptions.getHorizontalResolution()).getHeight(), 52.0);

        Assert.assertEquals(renderer.getBoundsInPixels(imageOptions.getScale(), imageOptions.getVerticalResolution()).getWidth(), 250.0);
        Assert.assertEquals(renderer.getBoundsInPixels(imageOptions.getScale(), imageOptions.getVerticalResolution()).getHeight(), 52.0);

        Assert.assertEquals((float) renderer.getOpaqueBoundsInPoints().getWidth(), (float) 187.85);
        Assert.assertEquals((float) renderer.getOpaqueBoundsInPoints().getHeight(), (float) 39.25);
    }

    @Test
    public void resolutionDefaultValues() {
        ImageSaveOptions imageOptions = new ImageSaveOptions(SaveFormat.JPEG);

        Assert.assertEquals(imageOptions.getHorizontalResolution(), (float) 96.0);
        Assert.assertEquals(imageOptions.getVerticalResolution(), (float) 96.0);
    }

    //For assert result of the test you need to open "Shape.OfficeMath.svg" and check that OfficeMath node is there
    @Test
    public void saveShapeObjectAsImage() throws Exception {
        //ExStart
        //ExFor:OfficeMath.GetMathRenderer
        //ExFor:NodeRendererBase.Save(String, ImageSaveOptions)
        //ExSummary:Shows how to convert specific object into image
        Document doc = new Document(getMyDir() + "Shape.OfficeMath.docx");

        //Get OfficeMath node from the document and render this as image (you can also do the same with the Shape node)
        OfficeMath math = (OfficeMath) doc.getChild(NodeType.OFFICE_MATH, 0, true);
        math.getMathRenderer().save(getArtifactsDir() + "Shape.OfficeMath.svg", new ImageSaveOptions(SaveFormat.SVG));
        //ExEnd
    }

    @Test
    public void officeMathDisplayException() throws Exception {
        Document doc = new Document(getMyDir() + "Shape.OfficeMath.docx");

        OfficeMath officeMath = (OfficeMath) doc.getChild(NodeType.OFFICE_MATH, 0, true);
        officeMath.setDisplayType(OfficeMathDisplayType.DISPLAY);
        try {
            officeMath.setJustification(OfficeMathJustification.INLINE);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void officeMathDefaultValue() throws Exception {
        Document doc = new Document(getMyDir() + "Shape.OfficeMath.docx");

        OfficeMath officeMath = (OfficeMath) doc.getChild(NodeType.OFFICE_MATH, 0, true);

        Assert.assertEquals(officeMath.getDisplayType(), OfficeMathDisplayType.DISPLAY);
        Assert.assertEquals(officeMath.getJustification(), OfficeMathJustification.CENTER);
    }

    @Test
    public void officeMathDisplayGold() throws Exception {
        //ExStart
        //ExFor:OfficeMath
        //ExFor:OfficeMath.DisplayType
        //ExFor:OfficeMath.EquationXmlEncoding
        //ExFor:OfficeMath.Justification
        //ExFor:OfficeMath.NodeType
        //ExFor:OfficeMath.ParentParagraph
        //ExFor:OfficeMathDisplayType
        //ExFor:OfficeMathJustification
        //ExSummary:Shows how to set office math display formatting.
        Document doc = new Document(getMyDir() + "Shape.OfficeMath.docx");

        OfficeMath officeMath = (OfficeMath) doc.getChild(NodeType.OFFICE_MATH, 0, true);

        // OfficeMath nodes that are children of other OfficeMath nodes are always inline
        // The node we are working with is a base node, so its location and display type can be changed
        Assert.assertEquals(officeMath.getMathObjectType(), MathObjectType.O_MATH_PARA);
        Assert.assertEquals(officeMath.getNodeType(), NodeType.OFFICE_MATH);
        Assert.assertEquals(officeMath.getParentParagraph(), officeMath.getParentNode());

        // Used by OOXML and WML formats
        Assert.assertNull(officeMath.getEquationXmlEncoding());

        // We can change the location and display type of the OfficeMath node
        officeMath.setDisplayType(OfficeMathDisplayType.DISPLAY);
        officeMath.setJustification(OfficeMathJustification.LEFT);

        doc.save(getArtifactsDir() + "Shape.OfficeMath.docx");
        //ExEnd
        Assert.assertTrue(DocumentHelper.compareDocs(getArtifactsDir() + "Shape.OfficeMath.docx", getGoldsDir() + "Shape.OfficeMath Gold.docx"));
    }

    @Test
    public void cannotBeSetDisplayWithInlineJustification() throws Exception {
        Document doc = new Document(getMyDir() + "Shape.OfficeMath.docx");

        OfficeMath officeMath = (OfficeMath) doc.getChild(NodeType.OFFICE_MATH, 0, true);
        officeMath.setDisplayType(OfficeMathDisplayType.DISPLAY);
        try {
            officeMath.setJustification(OfficeMathJustification.INLINE);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void cannotBeSetInlineDisplayWithJustification() throws Exception {
        Document doc = new Document(getMyDir() + "Shape.OfficeMath.docx");

        OfficeMath officeMath = (OfficeMath) doc.getChild(NodeType.OFFICE_MATH, 0, true);
        officeMath.setDisplayType(OfficeMathDisplayType.INLINE);

        try {
            officeMath.setJustification(OfficeMathJustification.CENTER);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void officeMathDisplayNestedObjects() throws Exception {
        Document doc = new Document(getMyDir() + "Shape.NestedOfficeMath.docx");

        OfficeMath officeMath = (OfficeMath) doc.getChild(NodeType.OFFICE_MATH, 0, true);

        //Always inline
        Assert.assertEquals(officeMath.getDisplayType(), OfficeMathDisplayType.INLINE);
        Assert.assertEquals(officeMath.getJustification(), OfficeMathJustification.INLINE);
    }

    @Test(dataProvider = "workWithMathObjectTypeDataProvider")
    public void workWithMathObjectType(final int index, final int objectType) throws Exception {
        Document doc = new Document(getMyDir() + "Shape.OfficeMath.docx");

        OfficeMath officeMath = (OfficeMath) doc.getChild(NodeType.OFFICE_MATH, index, true);
        Assert.assertEquals(officeMath.getMathObjectType(), objectType);
    }

    //JAVA-added data provider for test method
    @DataProvider(name = "workWithMathObjectTypeDataProvider")
    public static Object[][] workWithMathObjectTypeDataProvider() {
        return new Object[][]
                {
                        {0, MathObjectType.O_MATH_PARA},
                        {1, MathObjectType.O_MATH},
                        {2, MathObjectType.SUPERCRIPT},
                        {3, MathObjectType.ARGUMENT},
                        {4, MathObjectType.SUPERSCRIPT_PART}
                };
    }

    @Test(dataProvider = "aspectRatioLockedDataProvider")
    public void aspectRatioLocked(final boolean isLocked) throws Exception {
        //ExStart
        //ExFor:ShapeBase.AspectRatioLocked
        //ExSummary:Shows how to set "AspectRatioLocked" for the shape object
        Document doc = new Document(getMyDir() + "Shape.ActiveXObject.docx");

        // Get shape object from the document and set AspectRatioLocked(it is possible to get/set AspectRatioLocked for child shapes (mimic MS Word behavior), 
        // but AspectRatioLocked has effect only for top level shapes!)
        Shape shape = (Shape) doc.getChild(NodeType.SHAPE, 0, true);
        shape.setAspectRatioLocked(isLocked);
        //ExEnd

        ByteArrayOutputStream dstStream = new ByteArrayOutputStream();
        doc.save(dstStream, SaveFormat.DOCX);

        shape = (Shape) doc.getChild(NodeType.SHAPE, 0, true);
        Assert.assertEquals(shape.getAspectRatioLocked(), isLocked);
    }

    //JAVA-added data provider for test method
    @DataProvider(name = "aspectRatioLockedDataProvider")
    public static Object[][] aspectRatioLockedDataProvider() {
        return new Object[][]
                {
                        {true},
                        {false}
                };
    }

    @Test
    public void aspectRatioLockedDefaultValue() throws Exception {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // The best place for the watermark image is in the header or footer so it is shown on every page.
        builder.moveToHeaderFooter(HeaderFooterType.HEADER_PRIMARY);

        // Insert a floating picture.
        Shape shape = builder.insertImage(getImageDir() + "Watermark.png");
        shape.setWrapType(WrapType.NONE);
        shape.setBehindText(true);

        shape.setRelativeHorizontalPosition(RelativeHorizontalPosition.PAGE);
        shape.setRelativeVerticalPosition(RelativeVerticalPosition.PAGE);

        // Calculate image left and top position so it appears in the centre of the page.
        shape.setLeft((builder.getPageSetup().getPageWidth() - shape.getWidth()) / 2.0);
        shape.setTop((builder.getPageSetup().getPageHeight() - shape.getHeight()) / 2.0);

        ByteArrayOutputStream dstStream = new ByteArrayOutputStream();
        doc.save(dstStream, SaveFormat.DOCX);

        shape = (Shape) doc.getChild(NodeType.SHAPE, 0, true);
        Assert.assertEquals(shape.getAspectRatioLocked(), true);
    }

    @Test
    public void markupLunguageByDefault() throws Exception {
        //ExStart
        //ExFor:ShapeBase.MarkupLanguage
        //ExFor:ShapeBase.SizeInPoints
        //ExSummary:Shows how get markup language for shape object in document
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape image = builder.insertImage(getImageDir() + "dotnet-logo.png");

        // Loop through all single shapes inside document.
        for (Shape shape : (Iterable<Shape>) doc.getChildNodes(NodeType.SHAPE, true)) {
            Assert.assertEquals(shape.getMarkupLanguage(), ShapeMarkupLanguage.DML);

            System.out.println("Shape: " + shape.getMarkupLanguage());
            System.out.println("ShapeSize: " + shape.getSizeInPoints());
        }
        //ExEnd
    }

    @Test(dataProvider = "markupLunguageForDifferentMsWordVersionsDataProvider")
    public void markupLunguageForDifferentMsWordVersions(final int msWordVersion, final byte shapeMarkupLanguage) throws Exception {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        doc.getCompatibilityOptions().optimizeFor(msWordVersion);

        builder.insertImage(getImageDir() + "dotnet-logo.png");

        // Loop through all single shapes inside document.
        for (Shape shape : (Iterable<Shape>) doc.getChildNodes(NodeType.SHAPE, true)) {
            Assert.assertEquals(shape.getMarkupLanguage(), shapeMarkupLanguage);
        }
    }

    //JAVA-added data provider for test method
    @DataProvider(name = "markupLunguageForDifferentMsWordVersionsDataProvider")
    public static Object[][] markupLunguageForDifferentMsWordVersionsDataProvider() {
        return new Object[][]
                {
                        {MsWordVersion.WORD_2000, ShapeMarkupLanguage.VML},
                        {MsWordVersion.WORD_2002, ShapeMarkupLanguage.VML},
                        {MsWordVersion.WORD_2003, ShapeMarkupLanguage.VML},
                        {MsWordVersion.WORD_2007, ShapeMarkupLanguage.VML},
                        {MsWordVersion.WORD_2010, ShapeMarkupLanguage.DML},
                        {MsWordVersion.WORD_2013, ShapeMarkupLanguage.DML},
                        {MsWordVersion.WORD_2016, ShapeMarkupLanguage.DML}
                };
    }

    @Test
    public void changeStrokeProperties() throws Exception {
        //ExStart
        //ExFor:Stroke
        //ExFor:Stroke.On
        //ExFor:Stroke.Weight
        //ExFor:Stroke.JoinStyle
        //ExFor:Stroke.LineStyle
        //ExFor:ShapeLineStyle
        //ExSummary:Shows how change stroke properties
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Create a new shape of type Rectangle
        Shape rectangle = new Shape(doc, ShapeType.RECTANGLE);

        //Change stroke properties
        Stroke stroke = rectangle.getStroke();
        stroke.setOn(true);
        stroke.setWeight(5.0);
        stroke.setColor(Color.RED);
        stroke.setDashStyle(DashStyle.SHORT_DASH_DOT_DOT);
        stroke.setJoinStyle(JoinStyle.MITER);
        stroke.setEndCap(EndCap.SQUARE);
        stroke.setLineStyle(ShapeLineStyle.TRIPLE);

        //Insert shape object
        builder.insertNode(rectangle);
        //ExEnd

        ByteArrayOutputStream dstStream = new ByteArrayOutputStream();
        doc.save(dstStream, SaveFormat.DOCX);

        rectangle = (Shape) doc.getChild(NodeType.SHAPE, 0, true);

        Stroke strokeAfter = rectangle.getStroke();

        Assert.assertEquals(strokeAfter.getOn(), true);
        Assert.assertEquals(strokeAfter.getWeight(), 5.0);
        Assert.assertEquals(strokeAfter.getColor().getRGB(), Color.RED.getRGB());
        Assert.assertEquals(strokeAfter.getDashStyle(), DashStyle.SHORT_DASH_DOT_DOT);
        Assert.assertEquals(strokeAfter.getJoinStyle(), JoinStyle.MITER);
        Assert.assertEquals(strokeAfter.getEndCap(), EndCap.SQUARE);
        Assert.assertEquals(strokeAfter.getLineStyle(), ShapeLineStyle.TRIPLE);
    }

    @Test(description = "WORDSNET-16067")
    public void insertOleObjectAsHtmlFile() throws Exception {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.insertOleObject("http://www.aspose.com", "htmlfile", true, false, null);

        doc.save(getArtifactsDir() + "Document.InsertedOleObject.docx");
    }

    @Test(description = "WORDSNET-16085")
    public void insertOlePackage() throws Exception {
        //ExStart
        //ExFor:OlePackage
        //ExFor:OleFormat.OlePackage
        //ExFor:OlePackage.FileName
        //ExFor:OlePackage.DisplayName
        //ExSummary:Shows how insert ole object as ole package and set it file name and display name.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        byte[] zipFileBytes = Files.readAllBytes(Paths.get(getDatabaseDir() + "cat001.zip"));

        InputStream stream = new ByteArrayInputStream(zipFileBytes);
        try {
            Shape shape = builder.insertOleObject(stream, "Package", true, null);

            OlePackage setOlePackage = shape.getOleFormat().getOlePackage();
            setOlePackage.setFileName("Cat FileName.zip");
            setOlePackage.setDisplayName("Cat DisplayName.zip");

            doc.save(getArtifactsDir() + "Shape.InsertOlePackage.docx");
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
        //ExEnd

        doc = new Document(getArtifactsDir() + "Shape.InsertOlePackage.docx");

        Shape getShape = (Shape) doc.getChild(NodeType.SHAPE, 0, true);
        OlePackage getOlePackage = getShape.getOleFormat().getOlePackage();

        Assert.assertEquals(getOlePackage.getFileName(), "Cat FileName.zip");
        Assert.assertEquals(getOlePackage.getDisplayName(), "Cat DisplayName.zip");
    }

    @Test
    public void getAccessToOlePackage() throws Exception {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape oleObject = builder.insertOleObject(getMyDir() + "Document.Spreadsheet.xlsx", false, false, null);
        Shape oleObjectAsOlePackage = builder.insertOleObject(getMyDir() + "Document.Spreadsheet.xlsx", "Excel.Sheet", false, false, null);

        Assert.assertEquals(oleObject.getOleFormat().getOlePackage(), null);
        Assert.assertEquals(oleObjectAsOlePackage.getOleFormat().getOlePackage().getClass(), OlePackage.class);
    }

    @Test
    public void replaceRelativeSizeToAbsolute() throws Exception {
        Document doc = new Document(getMyDir() + "Shape.ShapeSize.docx");

        Shape shape = (Shape) doc.getChild(NodeType.SHAPE, 0, true);

        // Change shape size and rotation
        shape.setHeight(300.0);
        shape.setWidth(500.0);
        shape.setRotation(30.0);

        doc.save(getArtifactsDir() + "Shape.Resize.docx");
    }

    @Test
    public void displayTheShapeIntoATableCell() throws Exception {
        //ExStart
        //ExFor:ShapeBase.IsLayoutInCell
        //ExFor:MsWordVersion
        //ExSummary:Shows how to display the shape, inside a table or outside of it.
        Document doc = new Document(getMyDir() + "Shape.LayoutInCell.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        NodeCollection runs = doc.getChildNodes(NodeType.RUN, true);
        int num = 1;

        for (Run run : (Iterable<Run>) runs) {
            Shape watermark = new Shape(doc, ShapeType.TEXT_PLAIN_TEXT);
            watermark.setRelativeHorizontalPosition(RelativeHorizontalPosition.PAGE);
            watermark.setRelativeVerticalPosition(RelativeVerticalPosition.PAGE);
            watermark.isLayoutInCell(true); // False - display the shape outside of table cell, True - display the shape outside of table cell

            watermark.setWidth(30.0);
            watermark.setHeight(30.0);
            watermark.setHorizontalAlignment(HorizontalAlignment.CENTER);
            watermark.setVerticalAlignment(VerticalAlignment.CENTER);

            watermark.setRotation(-40);
            watermark.getFill().setColor(new Color(220, 220, 220));
            watermark.setStrokeColor(new Color(220, 220, 220));

            watermark.getTextPath().setText(MessageFormat.format("{0}", num));
            watermark.getTextPath().setFontFamily("Arial");

            watermark.setName(MessageFormat.format("WaterMark_{0}", UUID.randomUUID()));
            // Property will take effect only if the WrapType property is set to something other than WrapType.Inline
            watermark.setWrapType(WrapType.NONE);
            watermark.setBehindText(true);

            builder.moveTo(run);
            builder.insertNode(watermark);

            num = num + 1;
        }

        // Behaviour of MS Word on working with shapes in table cells is changed in the last versions.
        // Adding the following line is needed to make the shape displayed in center of a page.
        doc.getCompatibilityOptions().optimizeFor(MsWordVersion.WORD_2010);

        doc.save(getArtifactsDir() + "Shape.LayoutInCell.docx");
        //ExEnd
    }

    @Test
    public void shapeInsertion() throws Exception {
        //ExStart
        //ExFor:DocumentBuilder.InsertShape(ShapeType, RelativeHorizontalPosition, double, RelativeVerticalPosition, double, double, double, WrapType)
        //ExFor:DocumentBuilder.InsertShape(ShapeType, double, double)
        //ExSummary:Shows how to insert DML shapes into the document using a document builder.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // There are two ways of shape insertion
        // These methods allow inserting DML shape into the document model
        // Document must be saved in the format, which supports DML shapes, otherwise, such nodes will be converted
        // to VML shape, while document saving

        // 1. Free-floating shape insertion
        Shape freeFloatingShape = builder.insertShape(ShapeType.TEXT_BOX, RelativeHorizontalPosition.PAGE, 100.0, RelativeVerticalPosition.PAGE, 100.0, 50.0, 50.0, WrapType.NONE);
        freeFloatingShape.setRotation(30.0);
        // 2. Inline shape insertion
        Shape inlineShape = builder.insertShape(ShapeType.TEXT_BOX, 50.0, 50.0);
        inlineShape.setRotation(30.0);

        // If you need to create "NonPrimitive" shapes, like SingleCornerSnipped, TopCornersSnipped, DiagonalCornersSnipped,
        // TopCornersOneRoundedOneSnipped, SingleCornerRounded, TopCornersRounded, DiagonalCornersRounded
        // please save the document with "Strict" or "Transitional" compliance which allows saving shape as DML
        OoxmlSaveOptions saveOptions = new OoxmlSaveOptions(SaveFormat.DOCX);
        saveOptions.setCompliance(OoxmlCompliance.ISO_29500_2008_TRANSITIONAL);

        doc.save(getArtifactsDir() + "RotatedShape.docx", saveOptions);
        //ExEnd
    }

    //ExStart
    //ExFor:Shape.Accept(DocumentVisitor)
    //ExFor:Shape.Chart
    //ExFor:Shape.Clone(Boolean, INodeCloningListener)
    //ExFor:Shape.ExtrusionEnabled
    //ExFor:Shape.Filled
    //ExFor:Shape.HasChart
    //ExFor:Shape.OleFormat
    //ExFor:Shape.ShadowEnabled
    //ExFor:Shape.StoryType
    //ExFor:Shape.StrokeColor
    //ExFor:Shape.Stroked
    //ExFor:Shape.StrokeWeight
    //ExSummary:Shows how to iterate over all the shapes in a document.
    @Test //ExSkip
    public void visitShapes() throws Exception {
        // Open a document that contains shapes
        Document doc = new Document(getMyDir() + "Shape.Revisions.docx");

        // Create a ShapeVisitor and get the document to accept it
        ShapeVisitor shapeVisitor = new ShapeVisitor();
        doc.accept(shapeVisitor);

        // Print all the information that the visitor has collected
        System.out.println(shapeVisitor.getText());
    }

    /// <summary>
    /// DocumentVisitor implementation that collects information about visited shapes into a StringBuilder, to be printed to the console
    /// </summary>
    private static class ShapeVisitor extends DocumentVisitor {
        public ShapeVisitor() {
            mShapesVisited = 0;
            mTextIndentLevel = 0;
            mStringBuilder = new StringBuilder();
        }

        /// <summary>
        /// Appends a line to the StringBuilder with one prepended tab character for each indent level
        /// </summary>
        private void appendLine(String text) {
            for (int i = 0; i < mTextIndentLevel; i++) {
                mStringBuilder.append('\t');
            }

            mStringBuilder.append(text + "\n");
        }

        /// <summary>
        /// Return all the text that the StringBuilder has accumulated
        /// </summary>
        public String getText() {
            return MessageFormat.format("Shapes visited: {0}\n{1}", mShapesVisited, mStringBuilder);
        }

        /// <summary>
        /// Called when the start of a Shape node is visited
        /// </summary>
        public int visitShapeStart(Shape shape) {
            appendLine(MessageFormat.format("Shape found: {0}", shape.getShapeType()));

            mTextIndentLevel++;

            if (shape.hasChart())
                appendLine(MessageFormat.format("Has chart: {0}", shape.getChart().getTitle().getText()));

            appendLine(MessageFormat.format("Extrusion enabled: {0}", shape.getExtrusionEnabled()));
            appendLine(MessageFormat.format("Shadow enabled: {0}", shape.getShadowEnabled()));
            appendLine(MessageFormat.format("StoryType: {0}", shape.getStoryType()));

            if (shape.getStroked()) {
                Assert.assertEquals(shape.getStrokeColor(), shape.getStroke().getColor());
                appendLine(MessageFormat.format("Stroke colors: {0}, {1}", shape.getStroke().getColor(), shape.getStroke().getColor2()));
                appendLine(MessageFormat.format("Stroke weight: {0}", shape.getStrokeWeight()));
            }

            if (shape.getFilled())
                appendLine(MessageFormat.format("Filled: {0}", shape.getFillColor()));

            if (shape.getOleFormat() != null)
                appendLine(MessageFormat.format("Ole found of type: {0}", shape.getOleFormat().getProgId()));

            if (shape.getSignatureLine() != null)
                appendLine(MessageFormat.format("Found signature line for: {0}, {1}", shape.getSignatureLine().getSigner(), shape.getSignatureLine().getSignerTitle()));

            return VisitorAction.CONTINUE;
        }

        /// <summary>
        /// Called when the end of a Shape node is visited
        /// </summary>
        public int visitShapeEnd(Shape shape) {
            mTextIndentLevel--;
            mShapesVisited++;
            appendLine(MessageFormat.format("End of {0}", shape.getShapeType()));

            return VisitorAction.CONTINUE;
        }

        /// <summary>
        /// Called when the start of a GroupShape node is visited
        /// </summary>
        public int visitGroupShapeStart(GroupShape groupShape) {
            appendLine(MessageFormat.format("Shape group found: {0}", groupShape.getShapeType()));
            mTextIndentLevel++;

            return VisitorAction.CONTINUE;
        }

        /// <summary>
        /// Called when the end of a GroupShape node is visited
        /// </summary>
        public int visitGroupShapeEnd(GroupShape groupShape) {
            mTextIndentLevel--;
            appendLine(MessageFormat.format("End of {0}", groupShape.getShapeType()));

            return VisitorAction.CONTINUE;
        }

        private int mShapesVisited;
        private int mTextIndentLevel;
        private StringBuilder mStringBuilder;
    }
    //ExEnd

    @Test
    public void signatureLine() throws Exception {
        //ExStart
        //ExFor:Shape.SignatureLine
        //ExFor:ShapeBase.IsSignatureLine
        //ExFor:SignatureLine
        //ExFor:SignatureLine.AllowComments
        //ExFor:SignatureLine.DefaultInstructions
        //ExFor:SignatureLine.Email
        //ExFor:SignatureLine.Instructions
        //ExFor:SignatureLine.IsSigned
        //ExFor:SignatureLine.IsValid
        //ExFor:SignatureLine.ShowDate
        //ExFor:SignatureLine.Signer
        //ExFor:SignatureLine.SignerTitle
        //ExSummary:Shows how to create a line for a signature and insert it into a document.
        // Create a blank document and its DocumentBuilder
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // The SignatureLineOptions will contain all the data that the signature line will display
        SignatureLineOptions options = new SignatureLineOptions();
        {
            options.setAllowComments(true);
            options.setDefaultInstructions(true);
            options.setEmail("john.doe@management.com");
            options.setInstructions("Please sign here");
            options.setShowDate(true);
            options.setSigner("John Doe");
            options.setSignerTitle("Senior Manager");
        }

        // Insert the signature line, applying our SignatureLineOptions
        // We can control where the signature line will appear on the page using a combination of left/top indents and margin-relative positions
        // Since we're placing the signature line at the bottom right of the page, we will need to use negative indents to move it into view
        Shape shape = builder.insertSignatureLine(options, RelativeHorizontalPosition.RIGHT_MARGIN, -170.0, RelativeVerticalPosition.BOTTOM_MARGIN, -60.0, WrapType.NONE);
        Assert.assertTrue(shape.isSignatureLine());

        // The SignatureLine object is a member of the shape that contains it
        SignatureLine signatureLine = shape.getSignatureLine();

        Assert.assertEquals(signatureLine.getEmail(), "john.doe@management.com");
        Assert.assertEquals(signatureLine.getSigner(), "John Doe");
        Assert.assertEquals(signatureLine.getSignerTitle(), "Senior Manager");
        Assert.assertEquals(signatureLine.getInstructions(), "Please sign here");
        Assert.assertTrue(signatureLine.getShowDate());

        Assert.assertTrue(signatureLine.getAllowComments());
        Assert.assertTrue(signatureLine.getDefaultInstructions());

        // We will be prompted to sign it when we open the document
        Assert.assertFalse(signatureLine.isSigned());

        // The object may be valid, but the signature itself isn't until it is signed
        Assert.assertFalse(signatureLine.isValid());

        doc.save(getArtifactsDir() + "Drawing.SignatureLine.docx");
        //ExEnd
    }

    @Test
    public void textBox() throws Exception {
        //ExStart
        //ExFor:Shape.TextBox
        //ExFor:Shape.LastParagraph
        //ExFor:TextBox
        //ExFor:TextBox.FitShapeToText
        //ExFor:TextBox.InternalMarginBottom
        //ExFor:TextBox.InternalMarginLeft
        //ExFor:TextBox.InternalMarginRight
        //ExFor:TextBox.InternalMarginTop
        //ExFor:TextBox.LayoutFlow
        //ExFor:TextBox.TextBoxWrapMode
        //ExFor:TextBoxWrapMode
        //ExSummary:Shows how to insert text boxes and arrange their text.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Insert a shape that contains a TextBox
        Shape textBoxShape = builder.insertShape(ShapeType.TEXT_BOX, 150.0, 100.0);
        TextBox textBox = textBoxShape.getTextBox();

        // Move the document builder to inside the TextBox and write text
        builder.moveTo(textBoxShape.getLastParagraph());
        builder.write("Vertical text");

        // Text is displayed vertically, written top to bottom
        textBox.setLayoutFlow(LayoutFlow.TOP_TO_BOTTOM_IDEOGRAPHIC);

        // Move the builder out of the shape and back into the main document body
        builder.moveTo(textBoxShape.getParentParagraph());

        // Insert another TextBox
        textBoxShape = builder.insertShape(ShapeType.TEXT_BOX, 150.0, 100.0);
        textBox = textBoxShape.getTextBox();

        // Apply these values to both these members to get the parent shape to defy the dimensions we set to fit tightly around the TextBox's text
        textBox.setFitShapeToText(true);
        textBox.setTextBoxWrapMode(TextBoxWrapMode.NONE);

        builder.moveTo(textBoxShape.getLastParagraph());
        builder.write("Text fit tightly inside textbox");

        builder.moveTo(textBoxShape.getParentParagraph());

        textBoxShape = builder.insertShape(ShapeType.TEXT_BOX, 100.0, 100.0);
        textBox = textBoxShape.getTextBox();

        // Set margins for the textbox
        textBox.setInternalMarginTop(15.0);
        textBox.setInternalMarginBottom(15.0);
        textBox.setInternalMarginLeft(15.0);
        textBox.setInternalMarginRight(15.0);

        builder.moveTo(textBoxShape.getLastParagraph());
        builder.write("Text placed according to textbox margins");

        doc.save(getArtifactsDir() + "Drawing.TextBox.docx");
        //ExEnd
    }

    @Test
    public void createNewTextBoxAndChangeTextAnchor() throws Exception {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Set compatibility options to correctly using of VerticalAnchor property
        doc.getCompatibilityOptions().optimizeFor(MsWordVersion.WORD_2016);

        Shape textBoxShape = builder.insertShape(ShapeType.TEXT_BOX, 100.0, 100.0);
        // Not all formats are compatible with this one
        // For most of incompatible formats AW generated a warnings on save, so use doc.WarningCallback to check it.
        textBoxShape.getTextBox().setVerticalAnchor(TextBoxAnchor.BOTTOM);

        builder.moveTo(textBoxShape.getLastParagraph());
        builder.write("Text placed bottom");

        doc.save(getArtifactsDir() + "Shape.CreateNewTextBoxAndChangeAnchor.docx");
    }

    @Test
    public void getTextBoxAndChangeTextAnchor() throws Exception {
        //ExStart
        //ExFor:TextBoxAnchor
        //ExFor:TextBox.VerticalAnchor
        //ExSummary:Shows how to change text position inside textbox shape.
        Document doc = new Document(getMyDir() + "Shape.GetTextBoxAndChangeAnchor.docx");
        NodeCollection shapes = doc.getChildNodes(NodeType.SHAPE, true);

        Shape textbox = (Shape) shapes.get(0);
        textbox.getTextBox().setVerticalAnchor(TextBoxAnchor.BOTTOM);

        doc.save(getArtifactsDir() + "Shape.GetTextBoxAndChangeAnchor.docx");
        //ExEnd
    }

    //ExStart
    //ExFor:Shape.TextPath
    //ExFor:ShapeBase.IsWordArt
    //ExFor:TextPath
    //ExFor:TextPath.Bold
    //ExFor:TextPath.FitPath
    //ExFor:TextPath.FitShape
    //ExFor:TextPath.FontFamily
    //ExFor:TextPath.Italic
    //ExFor:TextPath.Kerning
    //ExFor:TextPath.On
    //ExFor:TextPath.ReverseRows
    //ExFor:TextPath.RotateLetters
    //ExFor:TextPath.SameLetterHeights
    //ExFor:TextPath.Shadow
    //ExFor:TextPath.SmallCaps
    //ExFor:TextPath.Spacing
    //ExFor:TextPath.StrikeThrough
    //ExFor:TextPath.Text
    //ExFor:TextPath.TextPathAlignment
    //ExFor:TextPath.Trim
    //ExFor:TextPath.Underline
    //ExFor:TextPath.XScale
    //ExFor:TextPathAlignment
    //ExSummary:Shows how to work with WordArt.
    @Test //ExSkip
    public void insertTextPaths() throws Exception {
        Document doc = new Document();

        // Insert a WordArt object and capture the shape that contains it in a variable
        Shape shape = appendWordArt(doc, "Bold & Italic", "Arial", 240.0, 24.0, Color.WHITE, Color.BLACK, ShapeType.TEXT_PLAIN_TEXT);

        // View and verify various text formatting settings
        shape.getTextPath().setBold(true);
        shape.getTextPath().setItalic(true);

        Assert.assertFalse(shape.getTextPath().getUnderline());
        Assert.assertFalse(shape.getTextPath().getShadow());
        Assert.assertFalse(shape.getTextPath().getStrikeThrough());
        Assert.assertFalse(shape.getTextPath().getReverseRows());
        Assert.assertFalse(shape.getTextPath().getXScale());
        Assert.assertFalse(shape.getTextPath().getTrim());
        Assert.assertFalse(shape.getTextPath().getSmallCaps());

        Assert.assertEquals(shape.getTextPath().getSize(), 36.0);
        Assert.assertEquals(shape.getTextPath().getText(), "Bold & Italic");
        Assert.assertEquals(shape.getShapeType(), ShapeType.TEXT_PLAIN_TEXT);

        // Toggle whether or not to display text
        shape = appendWordArt(doc, "On set to true", "Calibri", 150.0, 24.0, Color.YELLOW, Color.RED, ShapeType.TEXT_PLAIN_TEXT);
        shape.getTextPath().setOn(true);

        shape = appendWordArt(doc, "On set to false", "Calibri", 150.0, 24.0, Color.YELLOW, Color.RED, ShapeType.TEXT_PLAIN_TEXT);
        shape.getTextPath().setOn(false);

        // Apply kerning
        shape = appendWordArt(doc, "Kerning: VAV", "Times New Roman", 90.0, 24.0, Color.ORANGE, Color.RED, ShapeType.TEXT_PLAIN_TEXT);
        shape.getTextPath().setKerning(true);

        shape = appendWordArt(doc, "No kerning: VAV", "Times New Roman", 100.0, 24.0, Color.ORANGE, Color.RED, ShapeType.TEXT_PLAIN_TEXT);
        shape.getTextPath().setKerning(false);

        // Apply custom spacing, on a scale from 0.0 (none) to 1.0 (default)
        shape = appendWordArt(doc, "Spacing set to 0.1", "Calibri", 120.0, 24.0, Color.BLUE, Color.BLUE, ShapeType.TEXT_CASCADE_DOWN);
        shape.getTextPath().setSpacing(0.1);

        // Rotate letters 90 degrees to the left, text is still laid out horizontally
        shape = appendWordArt(doc, "RotateLetters", "Calibri", 200.0, 36.0, Color.YELLOW, Color.GREEN, ShapeType.TEXT_WAVE);
        shape.getTextPath().setRotateLetters(true);

        // Set the x-height to equal the cap height
        shape = appendWordArt(doc, "Same character height for lower and UPPER case", "Calibri", 300.0, 24.0, Color.BLUE, Color.BLUE, ShapeType.TEXT_SLANT_UP);
        shape.getTextPath().setSameLetterHeights(true);

        // By default, the size of the text will scale to always fit the size of the containing shape, overriding the text size setting
        shape = appendWordArt(doc, "FitShape on", "Calibri", 160.0, 24.0, Color.BLUE, Color.BLUE, ShapeType.TEXT_PLAIN_TEXT);
        Assert.assertTrue(shape.getTextPath().getFitShape());
        shape.getTextPath().setSize(24.0);

        // If we set FitShape to false, the size of the text will defy the shape bounds and always keep the size value we set below
        // We can also set TextPathAlignment to align the text
        shape = appendWordArt(doc, "FitShape off", "Calibri", 160.0, 24.0, Color.BLUE, Color.BLUE, ShapeType.TEXT_PLAIN_TEXT);
        shape.getTextPath().setFitShape(false);
        shape.getTextPath().setSize(24.0);
        shape.getTextPath().setTextPathAlignment(TextPathAlignment.RIGHT);

        doc.save(getArtifactsDir() + "Drawing.TextPath.docx");
    }

    /// <summary>
    /// Insert a new paragraph with a WordArt shape inside it
    /// </summary>
    private Shape appendWordArt(Document doc, String text, String textFontFamily, double shapeWidth, double shapeHeight, Color wordArtFill, Color line, int wordArtShapeType) throws Exception {
        // Insert a new paragraph
        Paragraph para = (Paragraph) doc.getFirstSection().getBody().appendChild(new Paragraph(doc));

        // Create an inline Shape, which will serve as a container for our WordArt, and append it to the paragraph
        // The shape can only be a valid WordArt shape if the ShapeType assigned here is a WordArt-designated ShapeType
        // These types will have "WordArt object" in the description and their enumerator names will start with "Text..."
        Shape shape = new Shape(doc, wordArtShapeType);
        shape.setWrapType(WrapType.INLINE);
        para.appendChild(shape);

        // Set the shape's width and height
        shape.setWidth(shapeWidth);
        shape.setHeight(shapeHeight);

        // These color settings will apply to the letters of the displayed WordArt text
        shape.setFillColor(wordArtFill);
        shape.setStrokeColor(line);

        // The WordArt object is accessed here, and we will set the text and font like this
        shape.getTextPath().setText(text);
        shape.getTextPath().setFontFamily(textFontFamily);

        return shape;
    }
    //ExEnd

    @Test
    public void shapeRevision() throws Exception {
        //ExStart
        //ExFor:ShapeBase.IsDeleteRevision
        //ExFor:ShapeBase.IsInsertRevision
        //ExSummary:Shows how to work with revision shapes.
        // Open a blank document
        Document doc = new Document();

        // Insert an inline shape without tracking revisions
        Assert.assertFalse(doc.getTrackRevisions());
        Shape shape = new Shape(doc, ShapeType.CUBE);
        shape.setWrapType(WrapType.INLINE);
        shape.setWidth(100.0);
        shape.setHeight(100.0);
        doc.getFirstSection().getBody().getFirstParagraph().appendChild(shape);

        // Start tracking revisions and then insert another shape
        doc.startTrackRevisions("John Doe");

        shape = new Shape(doc, ShapeType.SUN);
        shape.setWrapType(WrapType.INLINE);
        shape.setWidth(100.0);
        shape.setHeight(100.0);
        doc.getFirstSection().getBody().getFirstParagraph().appendChild(shape);

        // Get the document's shape collection which includes just the two shapes we added
        NodeCollection shapes = doc.getChildNodes(NodeType.SHAPE, true);
        Assert.assertEquals(shapes.getCount(), 2);

        // Remove the first shape
        Shape firstShape = (Shape) shapes.get(0);
        firstShape.remove();

        // Because we removed that shape while changes were being tracked, the shape counts as a delete revision
        Assert.assertEquals(firstShape.getShapeType(), ShapeType.CUBE);
        Assert.assertTrue(firstShape.isDeleteRevision());

        Shape secondShape = (Shape) shapes.get(1);

        // And we inserted another shape while tracking changes, so that shape will count as an insert revision
        Assert.assertEquals(secondShape.getShapeType(), ShapeType.SUN);
        Assert.assertTrue(secondShape.isInsertRevision());
        //ExEnd
    }

    @Test
    public void moveRevisions() throws Exception {
        //ExStart
        //ExFor:ShapeBase.IsMoveFromRevision
        //ExFor:ShapeBase.IsMoveToRevision
        //ExSummary:Shows how to identify move revision shapes.
        // Open a document that contains a move revision
        // A move revision is when we, while changes are tracked, cut(not copy)-and-paste or highlight and drag text from one place to another
        // If inline shapes are caught up in the text movement, they will count as move revisions as well
        // Moving a floating shape will not count as a move revision
        Document doc = new Document(getMyDir() + "Shape.Revisions.docx");

        // The document has one shape that was moved, but shape move revisions will have two instances of that shape
        // One will be the shape at its arrival destination and the other will be the shape at its original location
        NodeCollection shapes = doc.getChildNodes(NodeType.SHAPE, true);
        Assert.assertEquals(shapes.getCount(), 2);

        Shape firstShape = (Shape) shapes.get(0);

        // This is the move to revision, also the shape at its arrival destination
        Assert.assertFalse(firstShape.isMoveFromRevision());
        Assert.assertTrue(firstShape.isMoveToRevision());

        Shape secondShape = (Shape) shapes.get(1);

        // This is the move from revision, which is the shape at its original location
        Assert.assertTrue(secondShape.isMoveFromRevision());
        Assert.assertFalse(secondShape.isMoveToRevision());
        //ExEnd
    }

    @Test
    public void adjustWithEffects() throws Exception {
        //ExStart
        //ExFor:ShapeBase.AdjustWithEffects(RectangleF)
        //ExFor:ShapeBase.BoundsWithEffects
        //ExSummary:Shows how to check how a shape's bounds are affected by shape effects.
        // Open a document that contains two shapes and get its shape collection
        Document doc = new Document(getMyDir() + "Shape.AdjustWithEffects.docx");
        NodeCollection shapes = doc.getChildNodes(NodeType.SHAPE, true);
        Assert.assertEquals(shapes.getCount(), 2);

        Shape firstShape = (Shape) shapes.get(0);
        Shape secondShape = (Shape) shapes.get(1);

        // The two shapes are identical in terms of dimensions and shape type
        Assert.assertEquals(secondShape.getWidth(), firstShape.getWidth());
        Assert.assertEquals(secondShape.getHeight(), firstShape.getHeight());
        Assert.assertEquals(secondShape.getShapeType(), firstShape.getShapeType());

        // However, the first shape has no effects, while the second one has a shadow and thick outline
        Assert.assertEquals(firstShape.getStrokeWeight(), 0.0);
        Assert.assertEquals(secondShape.getStrokeWeight(), 20.0);
        Assert.assertFalse(firstShape.getShadowEnabled());
        Assert.assertTrue(secondShape.getShadowEnabled());

        // These effects make the size of the second shape's silhouette bigger than that of the first
        // Even though the size of the rectangle that shows up when we click on these shapes in Microsoft Word is the same,
        // the practical outer bounds of the second shape are affected by the shadow and outline and are bigger
        // We can use the AdjustWithEffects method to see exactly how much bigger they are

        // Create a RectangleF object, which represents a rectangle, which we could potentially use as the coordinates and bounds for a shape
        Rectangle2D.Float rectangleF = new Rectangle2D.Float(200f, 200f, 1000f, 1000f);

        // Run this method to get the size of the rectangle adjusted for all of our shape's effects
        Rectangle2D.Float rectangleFOut = firstShape.adjustWithEffects(rectangleF);

        // Since the shape has no border-changing effects, its boundary dimensions are unaffected
        Assert.assertEquals(rectangleFOut.getX(), 200.0);
        Assert.assertEquals(rectangleFOut.getY(), 200.0);
        Assert.assertEquals(rectangleFOut.getWidth(), 1000.0);
        Assert.assertEquals(rectangleFOut.getHeight(), 1000.0);

        // The final extent of the first shape, in points
        Assert.assertEquals(firstShape.getBoundsWithEffects().getX(), -0.0);
        Assert.assertEquals(firstShape.getBoundsWithEffects().getY(), -0.0);
        Assert.assertEquals(firstShape.getBoundsWithEffects().getWidth(), 147.0);
        Assert.assertEquals(firstShape.getBoundsWithEffects().getHeight(), 147.0);

        // Do the same with the second shape
        rectangleF = new Rectangle2D.Float(200f, 200f, 1000f, 1000f);
        rectangleFOut = secondShape.adjustWithEffects(rectangleF);

        // The shape's x/y coordinates (top left corner location) have been pushed back by the thick outline
        Assert.assertEquals(rectangleFOut.getX(), 171.5);
        Assert.assertEquals(rectangleFOut.getY(), 167.0);

        // The width and height were also affected by the outline and shadow
        Assert.assertEquals(rectangleFOut.getWidth(), 1045.0);
        Assert.assertEquals(rectangleFOut.getHeight(), 1132.0);

        // These values are also affected by effects
        Assert.assertEquals(secondShape.getBoundsWithEffects().getX(), -28.5);
        Assert.assertEquals(secondShape.getBoundsWithEffects().getY(), -33.0);
        Assert.assertEquals(secondShape.getBoundsWithEffects().getWidth(), 192.0);
        Assert.assertEquals(secondShape.getBoundsWithEffects().getHeight(), 279.0);
        //ExEnd
    }

    @Test
    public void renderAllShapes() throws Exception {
        //ExStart
        //ExFor:ShapeBase.GetShapeRenderer
        //ExFor:NodeRendererBase.Save(Stream, ImageSaveOptions)
        //ExSummary:Shows how to export shapes to files in the local file system using a shape renderer.
        // Open a document that contains shapes and get its shape collection
        Document doc = new Document(getMyDir() + "Shape.VarietyOfShapes.docx");
        NodeCollection shapes = doc.getChildNodes(NodeType.SHAPE, true);
        Assert.assertEquals(7, shapes.getCount());

        // There are 7 shapes in the document, with one group shape with 2 child shapes
        // The child shapes will be rendered but their parent group shape will be skipped, so we will see 6 output files
        for (Shape shape : (Iterable<Shape>) shapes) {
            ShapeRenderer renderer = shape.getShapeRenderer();
            ImageSaveOptions options = new ImageSaveOptions(SaveFormat.PNG);
            renderer.save(getArtifactsDir() + MessageFormat.format("Shape.ShapeRenderer {0}.png", shape.getName()), options);
        }
        //ExEnd
    }

    @Test
    public void officeMathRenderer() throws Exception {
        //ExStart
        //ExFor:NodeRendererBase
        //ExFor:NodeRendererBase.BoundsInPoints
        //ExFor:NodeRendererBase.GetBoundsInPixels(Single, Single)
        //ExFor:NodeRendererBase.GetBoundsInPixels(Single, Single, Single)
        //ExFor:NodeRendererBase.GetOpaqueBoundsInPixels(Single, Single)
        //ExFor:NodeRendererBase.GetOpaqueBoundsInPixels(Single, Single, Single)
        //ExFor:NodeRendererBase.GetSizeInPixels(Single, Single)
        //ExFor:NodeRendererBase.GetSizeInPixels(Single, Single, Single)
        //ExFor:NodeRendererBase.OpaqueBoundsInPoints
        //ExFor:NodeRendererBase.SizeInPoints
        //ExFor:OfficeMathRenderer
        //ExFor:OfficeMathRenderer.#ctor(Math.OfficeMath)
        //ExSummary:Shows how to measure and scale shapes.
        // Open a document that contains an OfficeMath object
        Document doc = new Document(getMyDir() + "Shape.OfficeMath.docx");

        // Create a renderer for the OfficeMath object
        OfficeMath officeMath = (OfficeMath) doc.getChild(NodeType.OFFICE_MATH, 0, true);
        OfficeMathRenderer renderer = new OfficeMathRenderer(officeMath);

        // We can measure the size of the image that the OfficeMath object will create when we render it
        Assert.assertEquals(renderer.getSizeInPoints().getX(), 117.0, 0.1);
        Assert.assertEquals(renderer.getSizeInPoints().getY(), 12.9, 0.1);

        Assert.assertEquals(renderer.getBoundsInPoints().getWidth(), 117.0, 0.1);
        Assert.assertEquals(renderer.getBoundsInPoints().getHeight(), 12.9, 0.1);

        // Shapes with transparent parts may return different values here
        Assert.assertEquals(renderer.getOpaqueBoundsInPoints().getWidth(), 117.0, 0.1);
        Assert.assertEquals(renderer.getOpaqueBoundsInPoints().getHeight(), 14.7, 0.1);

        // Get the shape size in pixels, with linear scaling to a specific DPI
        Rectangle bounds = renderer.getBoundsInPixels(1.0f, 96.0f);
        Assert.assertEquals(bounds.getWidth(), 156.0);
        Assert.assertEquals(bounds.getHeight(), 18.0);

        // Get the shape size in pixels, but with a different DPI for the horizontal and vertical dimensions
        bounds = renderer.getBoundsInPixels(1.0f, 96.0f, 150.0f);
        Assert.assertEquals(bounds.getWidth(), 156.0);
        Assert.assertEquals(bounds.getHeight(), 27.0);

        // The opaque bounds may vary here also
        bounds = renderer.getOpaqueBoundsInPixels(1.0f, 96.0f);
        Assert.assertEquals(bounds.getWidth(), 156.0);
        Assert.assertEquals(bounds.getHeight(), 20.0);

        bounds = renderer.getOpaqueBoundsInPixels(1.0f, 96.0f, 150.0f);
        Assert.assertEquals(bounds.getWidth(), 156.0);
        Assert.assertEquals(bounds.getHeight(), 31.0);
        //ExEnd
    }

    //ExStart
    //ExFor:NodeRendererBase.RenderToScale(Graphics, Single, Single, Single)
    //ExFor:NodeRendererBase.RenderToSize(Graphics, Single, Single, Single, Single)
    //ExFor:ShapeRenderer
    //ExFor:ShapeRenderer.#ctor(ShapeBase)
    //ExSummary:Shows how to render a shape with a Graphics object.
    public static class JFrameGraphics extends JPanel {
        public void paint(Graphics graphics) {
            try {
                // Open a document and get its first shape, which is a chart
                Document doc = new Document(getMyDir() + "Shape.VarietyOfShapes.docx");

                Shape shape = (Shape) doc.getChild(NodeType.SHAPE, 1, true);

                // Create a ShapeRenderer instance and a Graphics object
                // The ShapeRenderer will render the shape that is passed during construction over the Graphics object
                // Whatever is rendered on this Graphics object will be displayed on the screen inside this form
                ShapeRenderer renderer = new ShapeRenderer(shape);

                // Call this method on the renderer to render the chart in the passed Graphics object,
                // on a specified x/y coordinate and scale
                renderer.renderToScale((Graphics2D) graphics, 0f, 0f, 1.5f);

                // Get another shape from the document, and render it to a specific size instead of a linear scale
                GroupShape groupShape = (GroupShape) doc.getChild(NodeType.GROUP_SHAPE, 0, true);
                renderer = new ShapeRenderer(groupShape);
                renderer.renderToSize((Graphics2D) graphics, 500f, 400f, 100f, 200f);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static void main(String[] args) {
            // Create windows form for present shapes
            JFrame frame = new JFrame("Aspose example");
            frame.getContentPane().add(new JFrameGraphics());
            frame.setSize(1000, 800);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
        }
        //ExEnd
    }
}
