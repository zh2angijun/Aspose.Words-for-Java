// Copyright (c) 2001-2019 Aspose Pty Ltd. All Rights Reserved.
//
// This file is part of Aspose.Words. The source code in this file
// is only intended as a supplement to the documentation, and is provided
// "as is", without warranty of any kind, either expressed or implied.
//////////////////////////////////////////////////////////////////////////

package ApiExamples;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.words.Node;
import com.aspose.words.Document;
import com.aspose.words.NodeType;
import com.aspose.words.CompositeNode;
import com.aspose.words.NodeImporter;
import com.aspose.words.ImportFormatMode;
import com.aspose.words.Section;
import com.aspose.words.Paragraph;
import com.aspose.words.Bookmark;
import com.aspose.words.IFieldMergingCallback;
import com.aspose.words.FieldMergingArgs;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.ImageFieldMergingArgs;
import com.aspose.words.FindReplaceOptions;
import com.aspose.words.FindReplaceDirection;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.words.IReplacingCallback;
import com.aspose.words.ReplaceAction;
import com.aspose.words.ReplacingArgs;


@Test
public class ExInsertDocument extends ApiExampleBase
{
    //ExStart
    //ExFor:Paragraph.IsEndOfSection
    //ExFor:NodeImporter
    //ExFor:NodeImporter.#ctor(DocumentBase, DocumentBase, ImportFormatMode)
    //ExFor:NodeImporter.ImportNode(Node, Boolean)
    //ExId:InsertDocumentMain
    //ExSummary:This is a method that inserts contents of one document at a specified location in another document.
    /// <summary>
    /// Inserts content of the external document after the specified node.
    /// Section breaks and section formatting of the inserted document are ignored.
    /// </summary>
    /// <param name="insertAfterNode">Node in the destination document after which the content 
    /// should be inserted. This node should be a block level node (paragraph or table).</param>
    /// <param name="srcDoc">The document to insert.</param>
    static void insertDocument(Node insertAfterNode, Document srcDoc)
    {
        // Make sure that the node is either a paragraph or table.
        if ((!((insertAfterNode.getNodeType()) == (NodeType.PARAGRAPH))) &
            (!((insertAfterNode.getNodeType()) == (NodeType.TABLE))))
            throw new IllegalArgumentException("The destination node should be either a paragraph or table.");

        // We will be inserting into the parent of the destination paragraph.
        CompositeNode dstStory = insertAfterNode.getParentNode();

        // This object will be translating styles and lists during the import.
        NodeImporter importer =
            new NodeImporter(srcDoc, insertAfterNode.getDocument(), ImportFormatMode.KEEP_SOURCE_FORMATTING);

        // Loop through all sections in the source document.
        for (Section srcSection : srcDoc.getSections().<Section>OfType() !!Autoporter error: Undefined expression type )
        {
            // Loop through all block level nodes (paragraphs and tables) in the body of the section.
            for (Node srcNode : (Iterable<Node>) srcSection.getBody())
            {
                // Let's skip the node if it is a last empty paragraph in a section.
                if (((srcNode.getNodeType()) == (NodeType.PARAGRAPH)))
                {
                    Paragraph para = (Paragraph) srcNode;
                    if (para.isEndOfSection() && !para.hasChildNodes())
                        continue;
                }

                // This creates a clone of the node, suitable for insertion into the destination document.
                Node newNode = importer.importNode(srcNode, true);

                // Insert new node after the reference node.
                dstStory.insertAfter(newNode, insertAfterNode);
                insertAfterNode = newNode;
            }
        }
    }
    //ExEnd

    @Test
    public void insertDocumentAtBookmark() throws Exception
    {
        //ExStart
        //ExId:InsertDocumentAtBookmark
        //ExSummary:Invokes the InsertDocument method shown above to insert a document at a bookmark.
        Document mainDoc = new Document(getMyDir() + "InsertDocument1.doc");
        Document subDoc = new Document(getMyDir() + "InsertDocument2.doc");

        Bookmark bookmark = mainDoc.getRange().getBookmarks().get("insertionPlace");
        insertDocument(bookmark.getBookmarkStart().getParentNode(), subDoc);

        mainDoc.save(getArtifactsDir() + "InsertDocumentAtBookmark.doc");
        //ExEnd
    }

    @Test
    public void insertDocumentAtMailMerge() throws Exception
    {
        //ExStart
        //ExFor:CompositeNode.HasChildNodes
        //ExId:InsertDocumentAtMailMerge
        //ExSummary:Demonstrates how to use the InsertDocument method to insert a document into a merge field during mail merge.
        // Open the main document.
        Document mainDoc = new Document(getMyDir() + "InsertDocument1.doc");

        // Add a handler to MergeField event
        mainDoc.getMailMerge().setFieldMergingCallback(new InsertDocumentAtMailMergeHandler());

        // The main document has a merge field in it called "Document_1".
        // The corresponding data for this field contains fully qualified path to the document
        // that should be inserted to this field.
        mainDoc.getMailMerge().execute(new String[] { "Document_1" }, new Object[] { getMyDir() + "InsertDocument2.doc" });

        mainDoc.save(getArtifactsDir() + "InsertDocumentAtMailMerge.doc");
    }

    private static class InsertDocumentAtMailMergeHandler implements IFieldMergingCallback
    {
        /// <summary>
        /// This handler makes special processing for the "Document_1" field.
        /// The field value contains the path to load the document. 
        /// We load the document and insert it into the current merge field.
        /// </summary>
        public void /*IFieldMergingCallback.*/fieldMerging(FieldMergingArgs args) throws Exception
        {
            if ("Document_1".equals(args.getDocumentFieldName()))
            {
                // Use document builder to navigate to the merge field with the specified name.
                DocumentBuilder builder = new DocumentBuilder(args.getDocument());
                builder.moveToMergeField(args.getDocumentFieldName());

                // The name of the document to load and insert is stored in the field value.
                Document subDoc = new Document((String) args.getFieldValue());

                // Insert the document.
                insertDocument(builder.getCurrentParagraph(), subDoc);

                // The paragraph that contained the merge field might be empty now and you probably want to delete it.
                if (!builder.getCurrentParagraph().hasChildNodes())
                    builder.getCurrentParagraph().remove();

                // Indicate to the mail merge engine that we have inserted what we wanted.
                args.setText(null);
            }
        }

        public void /*IFieldMergingCallback.*/imageFieldMerging(ImageFieldMergingArgs args)
        {
            // Do nothing.
        }
    }
    //ExEnd

    @Test
    public void insertDocumentAtReplace() throws Exception
    {
        //ExStart
        //ExFor:Range.Replace(Regex, String, FindReplaceOptions)
        //ExFor:IReplacingCallback
        //ExFor:ReplaceAction
        //ExFor:IReplacingCallback.Replacing
        //ExFor:ReplacingArgs
        //ExFor:ReplacingArgs.MatchNode
        //ExFor:FindReplaceDirection
        //ExId:InsertDocumentAtReplace
        //ExSummary:Shows how to insert content of one document into another during a customized find and replace operation.
        Document mainDoc = new Document(getMyDir() + "InsertDocument1.doc");

        FindReplaceOptions options = new FindReplaceOptions();
        options.setDirection(FindReplaceDirection.BACKWARD);
        options.setReplacingCallback(new InsertDocumentAtReplaceHandler());

        mainDoc.getRange().replaceInternal(new Regex("\\[MY_DOCUMENT\\]"), "", options);
        mainDoc.save(getArtifactsDir() + "InsertDocumentAtReplace.doc");
    }

    private static class InsertDocumentAtReplaceHandler implements IReplacingCallback
    {
        public /*ReplaceAction*/int /*IReplacingCallback.*/replacing(ReplacingArgs args) throws Exception
        {
            Document subDoc = new Document(getMyDir() + "InsertDocument2.doc");

            // Insert a document after the paragraph, containing the match text.
            Paragraph para = (Paragraph) args.getMatchNode().getParentNode();
            insertDocument(para, subDoc);

            // Remove the paragraph with the match text.
            para.remove();

            return ReplaceAction.SKIP;
        }
    }

    //ExEnd
}
