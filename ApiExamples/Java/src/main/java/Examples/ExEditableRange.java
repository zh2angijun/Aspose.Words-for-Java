package Examples;

//////////////////////////////////////////////////////////////////////////
// Copyright (c) 2001-2019 Aspose Pty Ltd. All Rights Reserved.
//
// This file is part of Aspose.Words. The source code in this file
// is only intended as a supplement to the documentation, and is provided
// "as is", without warranty of any kind, either expressed or implied.
//////////////////////////////////////////////////////////////////////////

import com.aspose.words.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;

public class ExEditableRange extends ApiExampleBase {
    @Test
    public void removesEditableRange() throws Exception {
        //ExStart
        //ExFor:EditableRange.Remove
        //ExSummary:Shows how to remove an editable range from a document.
        Document doc = new Document(getMyDir() + "Document.doc");
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Create an EditableRange so we can remove it. Does not have to be well-formed.
        EditableRangeStart edRange1Start = builder.startEditableRange();
        EditableRange editableRange1 = edRange1Start.getEditableRange();
        builder.writeln("Paragraph inside editable range");
        EditableRangeEnd edRange1End = builder.endEditableRange();

        // Remove the range that was just made.
        editableRange1.remove();
        //ExEnd
    }

    //ExStart
    //ExFor:DocumentBuilder.StartEditableRange
    //ExFor:DocumentBuilder.EndEditableRange
    //ExFor:DocumentBuilder.EndEditableRange(EditableRangeStart)
    //ExFor:EditableRange
    //ExFor:EditableRange.EditableRangeEnd
    //ExFor:EditableRange.EditableRangeStart
    //ExFor:EditableRange.Id
    //ExFor:EditableRange.SingleUser
    //ExFor:EditableRangeEnd
    //ExFor:EditableRangeEnd.Accept(DocumentVisitor)
    //ExFor:EditableRangeEnd.EditableRangeStart
    //ExFor:EditableRangeEnd.Id
    //ExFor:EditableRangeEnd.NodeType
    //ExFor:EditableRangeStart
    //ExFor:EditableRangeStart.Accept(DocumentVisitor)
    //ExFor:EditableRangeStart.EditableRange
    //ExFor:EditableRangeStart.Id
    //ExFor:EditableRangeStart.NodeType
    //ExSummary:Shows how to start and end an editable range.
    @Test //ExSkip
    public void createEditableRanges() throws Exception {
        Document doc = new Document(getMyDir() + "Document.doc");
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Start an editable range
        EditableRangeStart edRange1Start = builder.startEditableRange();

        // An EditableRange object is created for the EditableRangeStart that we just made
        EditableRange editableRange1 = edRange1Start.getEditableRange();

        // Put something inside the editable range
        builder.writeln("Paragraph inside first editable range");

        // An editable range is well-formed if it has a start and an end
        // Multiple editable ranges can be nested and overlapping
        EditableRangeEnd edRange1End = builder.endEditableRange();

        // Explicitly state which EditableRangeStart a new EditableRangeEnd should be paired with
        EditableRangeStart edRange2Start = builder.startEditableRange();
        builder.writeln("Paragraph inside second editable range");
        EditableRange editableRange2 = edRange2Start.getEditableRange();
        EditableRangeEnd edRange2End = builder.endEditableRange(edRange2Start);

        // Editable range starts and ends have their own respective node types
        Assert.assertEquals(edRange1Start.getNodeType(), NodeType.EDITABLE_RANGE_START);
        Assert.assertEquals(edRange1End.getNodeType(), NodeType.EDITABLE_RANGE_END);

        // Editable range IDs are unique and set automatically
        Assert.assertEquals(editableRange1.getId(), 0);
        Assert.assertEquals(editableRange2.getId(), 1);

        // Editable range starts and ends always belong to a range
        Assert.assertEquals(editableRange1.getEditableRangeStart(), edRange1Start);
        Assert.assertEquals(editableRange1.getEditableRangeEnd(), edRange1End);

        // They also inherit the ID of the entire editable range that they belong to
        Assert.assertEquals(editableRange1.getId(), edRange1Start.getId());
        Assert.assertEquals(editableRange1.getId(), edRange1End.getId());
        Assert.assertEquals(editableRange2.getId(), edRange2Start.getEditableRange().getId());
        Assert.assertEquals(editableRange2.getId(), edRange2End.getEditableRangeStart().getEditableRange().getId());

        // If the editable range was found in a document, it will probably have something in the single user property
        // But if we make one programmatically, the property is null by default
        Assert.assertEquals(editableRange1.getSingleUser(), "");

        // We have to set it ourselves if we want the ranges to belong to somebody
        editableRange1.setSingleUser("john.doe@myoffice.com");
        editableRange2.setSingleUser("jane.doe@myoffice.com");

        // Initialize a custom visitor for editable ranges that will print their contents
        EditableRangeInfoPrinter editableRangeReader = new EditableRangeInfoPrinter();

        // Both the start and end of an editable range can accept visitors, but not the editable range itself
        edRange1Start.accept(editableRangeReader);
        edRange2End.accept(editableRangeReader);

        // Or, if we want to go over all the editable ranges in a document, we can get the document to accept the visitor
        editableRangeReader.reset();
        doc.accept(editableRangeReader);

        System.out.println(editableRangeReader.toText());
    }

    /// <summary>
    /// Visitor implementation that prints attributes and contents of ranges.
    /// </summary>
    public static class EditableRangeInfoPrinter extends DocumentVisitor {
        public EditableRangeInfoPrinter() {
            mBuilder = new StringBuilder();
        }

        public String toText() {
            return mBuilder.toString();
        }

        public void reset() {
            mBuilder = new StringBuilder();
            mInsideEditableRange = false;
        }

        /// <summary>
        /// Called when an EditableRangeStart node is encountered in the document.
        /// </summary>
        public int visitEditableRangeStart(final EditableRangeStart editableRangeStart) {
            mBuilder.append(" -- Editable range found! -- " + "\r\n");
            mBuilder.append("\tID: " + editableRangeStart.getId() + "\r\n");
            mBuilder.append("\tUser: " + editableRangeStart.getEditableRange().getSingleUser() + "\r\n");
            mBuilder.append("\tContents: " + "\r\n");

            mInsideEditableRange = true;

            // Let the visitor continue visiting other nodes
            return VisitorAction.CONTINUE;
        }

        /// <summary>
        /// Called when an EditableRangeEnd node is encountered in the document.
        /// </summary>
        public int visitEditableRangeEnd(final EditableRangeEnd editableRangeEnd) {
            mBuilder.append(" -- End of editable range -- " + "\r\n");

            mInsideEditableRange = false;

            // Let the visitor continue visiting other nodes
            return VisitorAction.CONTINUE;
        }

        /// <summary>
        /// Called when a Run node is encountered in the document. Only runs within editable ranges have their contents recorded.
        /// </summary>
        public int visitRun(final Run run) {
            if (mInsideEditableRange) {
                mBuilder.append("\t\"" + run.getText() + "\"" + "\r\n");
            }

            // Let the visitor continue visiting other nodes
            return VisitorAction.CONTINUE;
        }

        private boolean mInsideEditableRange;
        private StringBuilder mBuilder;
    }
    //ExEnd

    @Test
    public void incorrectStructureException() throws Exception {
        Document doc = new Document();

        DocumentBuilder builder = new DocumentBuilder(doc);

        //Is not valid structure for the current document
        try {
            builder.endEditableRange();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalStateException);
        }

        builder.startEditableRange();
    }

    @Test
    public void incorrectStructureDoNotAdded() throws Exception {
        Document doc = DocumentHelper.createDocumentFillWithDummyText();
        DocumentBuilder builder = new DocumentBuilder(doc);

        //ExStart
        //ExFor:EditableRange.EditorGroup
        //ExFor:EditorType
        //ExSummary:Shows how to add editing group for editable ranges
        EditableRangeStart startRange1 = builder.startEditableRange();

        builder.writeln("EditableRange_1_1");
        builder.writeln("EditableRange_1_2");

        // Sets the editor for editable range region
        startRange1.getEditableRange().setEditorGroup(EditorType.EVERYONE);
        //ExEnd

        ByteArrayOutputStream dstStream = new ByteArrayOutputStream();
        doc.save(dstStream, SaveFormat.DOCX);

        // Assert that it's not valid structure and editable ranges aren't added to the current document
        NodeCollection startNodes = doc.getChildNodes(NodeType.EDITABLE_RANGE_START, true);
        Assert.assertEquals(startNodes.getCount(), 0);

        NodeCollection endNodes = doc.getChildNodes(NodeType.EDITABLE_RANGE_END, true);
        Assert.assertEquals(endNodes.getCount(), 0);
    }
}
