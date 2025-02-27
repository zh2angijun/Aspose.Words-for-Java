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

import java.awt.*;
import java.io.ByteArrayOutputStream;

public class ExSection extends ApiExampleBase {
    @Test
    public void protect() throws Exception {
        //ExStart
        //ExFor:Document.Protect(ProtectionType)
        //ExFor:ProtectionType
        //ExFor:Section.ProtectedForForms
        //ExSummary:Protects a section so only editing in form fields is possible.
        // Create a blank document
        Document doc = new Document();

        // Insert two sections with some text
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.writeln("Section 1. Unprotected.");
        builder.insertBreak(BreakType.SECTION_BREAK_CONTINUOUS);
        builder.writeln("Section 2. Protected.");

        // Section protection only works when document protection is turned and only editing in form fields is allowed.
        doc.protect(ProtectionType.ALLOW_ONLY_FORM_FIELDS);

        // By default, all sections are protected, but we can selectively turn protection off.
        doc.getSections().get(0).setProtectedForForms(false);

        builder.getDocument().save(getArtifactsDir() + "Section.Protect.doc");
        //ExEnd
    }

    @Test
    public void addRemove() throws Exception {
        //ExStart
        //ExFor:Document.Sections
        //ExFor:Section.Clone
        //ExFor:SectionCollection
        //ExFor:NodeCollection.RemoveAt(Int32)
        //ExSummary:Shows how to add/remove sections in a document.
        // Open the document.
        Document doc = new Document(getMyDir() + "Section.AddRemove.doc");

        // This shows what is in the document originally. The document has two sections.
        System.out.println(doc.getText());

        // Delete the first section from the document
        doc.getSections().removeAt(0);

        // Duplicate the last section and append the copy to the end of the document.
        int lastSectionIdx = doc.getSections().getCount() - 1;
        Section newSection = doc.getSections().get(lastSectionIdx).deepClone();
        doc.getSections().add(newSection);

        // Check what the document contains after we changed it.
        System.out.println(doc.getText());
        //ExEnd

        Assert.assertEquals(doc.getText(), "Hello2\fHello2\f");
    }

    @Test
    public void createFromScratch() throws Exception {
        //ExStart
        //ExFor:Node.GetText
        //ExFor:CompositeNode.RemoveAllChildren
        //ExFor:CompositeNode.AppendChild
        //ExFor:Section
        //ExFor:Section.#ctor
        //ExFor:Section.PageSetup
        //ExFor:PageSetup.SectionStart
        //ExFor:PageSetup.PaperSize
        //ExFor:SectionStart
        //ExFor:PaperSize
        //ExFor:Body
        //ExFor:Body.#ctor
        //ExFor:Paragraph
        //ExFor:Paragraph.#ctor
        //ExFor:Paragraph.ParagraphFormat
        //ExFor:ParagraphFormat
        //ExFor:ParagraphFormat.StyleName
        //ExFor:ParagraphFormat.Alignment
        //ExFor:ParagraphAlignment
        //ExFor:Run
        //ExFor:Run.#ctor(DocumentBase)
        //ExFor:Run.Text
        //ExFor:Inline.Font
        //ExSummary:Creates a simple document from scratch using the Aspose.Words object model.

        // Create an "empty" document. Note that like in Microsoft Word,
        // the empty document has one section, body and one paragraph in it.
        Document doc = new Document();

        // This truly makes the document empty. No sections (not possible in Microsoft Word).
        doc.removeAllChildren();

        // Create a new section node.
        // Note that the section has not yet been added to the document,
        // but we have to specify the parent document.
        Section section = new Section(doc);

        // Append the section to the document.
        doc.appendChild(section);

        // Lets set some properties for the section.
        section.getPageSetup().setSectionStart(SectionStart.NEW_PAGE);
        section.getPageSetup().setPaperSize(PaperSize.LETTER);

        // The section that we created is empty, lets populate it. The section needs at least the Body node.
        Body body = new Body(doc);
        section.appendChild(body);

        // The body needs to have at least one paragraph.
        // Note that the paragraph has not yet been added to the document,
        // but we have to specify the parent document.
        // The parent document is needed so the paragraph can correctly work
        // with styles and other document-wide information.
        Paragraph para = new Paragraph(doc);
        body.appendChild(para);

        // We can set some formatting for the paragraph
        para.getParagraphFormat().setStyleName("Heading 1");
        para.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);

        // So far we have one empty paragraph in the document.
        // The document is valid and can be saved, but lets add some text before saving.
        // Create a new run of text and add it to our paragraph.
        Run run = new Run(doc);
        run.setText("Hello World!");
        run.getFont().setColor(Color.RED);
        para.appendChild(run);

        // As a matter of interest, you can retrieve text of the whole document and
        // see that \x000c is automatically appended. \x000c is the end of section character.
        System.out.println(doc.getText());

        // Save the document.
        doc.save(getArtifactsDir() + "Section.CreateFromScratch.doc");
        //ExEnd

        Assert.assertEquals(doc.getText(), "Hello World!\f");
    }

    @Test
    public void ensureSectionMinimum() throws Exception {
        //ExStart
        //ExFor:Section.EnsureMinimum
        //ExSummary:Ensures that a section is valid.
        // Create a blank document
        Document doc = new Document();
        Section section = doc.getFirstSection();

        // Makes sure that the section contains a body with at least one paragraph.
        section.ensureMinimum();
        //ExEnd
    }

    @Test
    public void bodyEnsureMinimum() throws Exception {
        //ExStart
        //ExFor:Section.Body
        //ExFor:Body.EnsureMinimum
        //ExSummary:Clears main text from all sections from the document leaving the sections themselves.

        // Open a document.
        Document doc = new Document(getMyDir() + "Section.BodyEnsureMinimum.doc");

        // This shows what is in the document originally. The document has two sections.
        System.out.println(doc.getText());

        // Loop through all sections in the document.
        for (Section section : doc.getSections()) {
            // Each section has a Body node that contains main story (main text) of the section.
            Body body = section.getBody();

            // This clears all nodes from the body.
            body.removeAllChildren();

            // Technically speaking, for the main story of a section to be valid, it needs to have
            // at least one empty paragraph. That's what the EnsureMinimum method does.
            body.ensureMinimum();
        }

        // Check how the content of the document looks now.
        System.out.println(doc.getText());
        //ExEnd

        Assert.assertEquals(doc.getText(), "\f\f");
    }

    @Test
    public void bodyNodeType() throws Exception {
        //ExStart
        //ExFor:Body.NodeType
        //ExFor:HeaderFooter.NodeType
        //ExFor:Document.FirstSection
        //ExSummary:Shows how you can enumerate through children of a composite node and detect types of the children nodes.
        // Open a document.
        Document doc = new Document(getMyDir() + "Section.BodyNodeType.doc");

        // Get the first section in the document.
        Section section = doc.getFirstSection();

        // A Section is a composite node and therefore can contain child nodes.
        // Section can contain only Body and HeaderFooter nodes.
        for (Node node : section) {
            // Every node has the NodeType property.
            switch (node.getNodeType()) {
                case NodeType.BODY:
                    // If the node type is Body, we can cast the node to the Body class.
                    Body body = (Body) node;

                    // Write the content of the main story of the section to the console.
                    System.out.println("*** Body ***");
                    System.out.println(body.getText());
                    break;

                case NodeType.HEADER_FOOTER:
                    // If the node type is HeaderFooter, we can cast the node to the HeaderFooter class.
                    HeaderFooter headerFooter = (HeaderFooter) node;

                    // Write the content of the header footer to the console.
                    System.out.println("*** HeaderFooter ***");
                    System.out.println(headerFooter.getHeaderFooterType());
                    System.out.println(headerFooter.getText());
                    break;

                default:
                    // Other types of nodes never occur inside a Section node.
                    throw new Exception("Unexpected node type in a section.");
            }
        }
        //ExEnd
    }

    @Test
    public void sectionsAccessByIndex() throws Exception {
        //ExStart
        //ExFor:SectionCollection.Item(Int32)
        //ExId:SectionsAccessByIndex
        //ExSummary:Shows how to access a section at the specified index.
        Document doc = new Document(getMyDir() + "Document.doc");
        Section section = doc.getSections().get(0);
        //ExEnd
    }

    @Test
    public void sectionsAddSection() throws Exception {
        //ExStart
        //ExFor:NodeCollection.Add
        //ExId:SectionsAddSection
        //ExSummary:Shows how to add a section to the end of the document.
        Document doc = new Document(getMyDir() + "Document.doc");
        Section sectionToAdd = new Section(doc);
        doc.getSections().add(sectionToAdd);
        //ExEnd
    }

    @Test
    public void sectionsDeleteSection() throws Exception {
        //ExStart
        //ExId:SectionsDeleteSection
        //ExSummary:Shows how to remove a section at the specified index.
        Document doc = new Document(getMyDir() + "Document.doc");
        doc.getSections().removeAt(0);
        //ExEnd
    }

    @Test
    public void sectionsDeleteAllSections() throws Exception {
        //ExStart
        //ExFor:NodeCollection.Clear
        //ExId:SectionsDeleteAllSections
        //ExSummary:Shows how to remove all sections from a document.
        Document doc = new Document(getMyDir() + "Document.doc");
        doc.getSections().clear();
        //ExEnd
    }

    @Test
    public void sectionsAppendSectionContent() throws Exception {
        //ExStart
        //ExFor:Section.AppendContent
        //ExFor:Section.PrependContent
        //ExId:SectionsAppendSectionContent
        //ExSummary:Shows how to append content of an existing section. The number of sections in the document remains the same.
        Document doc = new Document(getMyDir() + "Section.AppendContent.doc");

        // This is the section that we will append and prepend to.
        Section section = doc.getSections().get(2);

        // This copies content of the 1st section and inserts it at the beginning of the specified section.
        Section sectionToPrepend = doc.getSections().get(0);
        section.prependContent(sectionToPrepend);

        // This copies content of the 2nd section and inserts it at the end of the specified section.
        Section sectionToAppend = doc.getSections().get(1);
        section.appendContent(sectionToAppend);
        //ExEnd
    }

    @Test
    public void sectionsDeleteSectionContent() throws Exception {
        //ExStart
        //ExFor:Section.ClearContent
        //ExId:SectionsDeleteSectionContent
        //ExSummary:Shows how to delete main content of a section.
        Document doc = new Document(getMyDir() + "Document.doc");
        Section section = doc.getSections().get(0);
        section.clearContent();
        //ExEnd
    }

    @Test
    public void sectionsDeleteHeaderFooter() throws Exception {
        //ExStart
        //ExFor:Section.ClearHeadersFooters
        //ExId:SectionsDeleteHeaderFooter
        //ExSummary:Clears content of all headers and footers in a section.
        Document doc = new Document(getMyDir() + "Document.doc");
        Section section = doc.getSections().get(0);
        section.clearHeadersFooters();
        //ExEnd
    }

    @Test
    public void sectionDeleteHeaderFooterShapes() throws Exception {
        //ExStart
        //ExFor:Section.DeleteHeaderFooterShapes
        //ExSummary:Removes all images and shapes from all headers footers in a section.
        Document doc = new Document(getMyDir() + "Document.doc");
        Section section = doc.getSections().get(0);
        section.deleteHeaderFooterShapes();
        //ExEnd
    }

    @Test
    public void sectionsCloneSection() throws Exception {
        //ExStart
        //ExId:SectionsCloneSection
        //ExSummary:Shows how to create a duplicate of a particular section.
        Document doc = new Document(getMyDir() + "Document.doc");
        Section cloneSection = doc.getSections().get(0).deepClone();
        //ExEnd
    }

    @Test
    public void sectionsImportSection() throws Exception {
        //ExStart
        //ExId:SectionsImportSection
        //ExSummary:Shows how to copy sections between documents.
        Document srcDoc = new Document(getMyDir() + "Document.doc");
        Document dstDoc = new Document();

        Section sourceSection = srcDoc.getSections().get(0);
        Section newSection = (Section) dstDoc.importNode(sourceSection, true);
        dstDoc.getSections().add(newSection);
        //ExEnd
    }

    @Test
    public void migrateFrom2XImportSection() throws Exception {
        Document srcDoc = new Document();
        Document dstDoc = new Document();

        //ExStart
        //ExId:MigrateFrom2XImportSection
        //ExSummary:This fragment shows how to insert a section from another document in Aspose.Words 3.0 or higher.
        Section sourceSection = srcDoc.getSections().get(0);
        Section newSection = (Section) dstDoc.importNode(sourceSection, true);
        dstDoc.getSections().add(newSection);
        //ExEnd
    }

    @Test
    public void modifyPageSetupInAllSections() throws Exception {
        //ExStart
        //ExId:ModifyPageSetupInAllSections
        //ExSummary:Shows how to set paper size for the whole document.
        Document doc = new Document(getMyDir() + "Section.ModifyPageSetupInAllSections.doc");

        // It is important to understand that a document can contain many sections and each
        // section has its own page setup. In this case we want to modify them all.
        for (Node node : doc) {
            Section section = (Section) node;
            section.getPageSetup().setPaperSize(PaperSize.LETTER);
        }

        doc.save(getArtifactsDir() + "Section.ModifyPageSetupInAllSections.doc");
        //ExEnd
    }

    @Test
    public void cultureInfoPageSetupDefaults() throws Exception {
        CurrentThreadSettings.setLocale("en-us");

        Document docEn = new Document();

        //Assert that page defaults comply current culture info
        Section sectionEn = docEn.getSections().get(0);
        Assert.assertEquals(sectionEn.getPageSetup().getLeftMargin(), 72.0); // 2.54 cm
        Assert.assertEquals(sectionEn.getPageSetup().getRightMargin(), 72.0); // 2.54 cm
        Assert.assertEquals(sectionEn.getPageSetup().getTopMargin(), 72.0); // 2.54 cm
        Assert.assertEquals(sectionEn.getPageSetup().getBottomMargin(), 72.0); // 2.54 cm
        Assert.assertEquals(sectionEn.getPageSetup().getHeaderDistance(), 36.0); // 1.27 cm
        Assert.assertEquals(sectionEn.getPageSetup().getFooterDistance(), 36.0); // 1.27 cm
        Assert.assertEquals(sectionEn.getPageSetup().getTextColumns().getSpacing(), 36.0); // 1.27 cm

        //Change culture and assert that the page defaults are changed
        CurrentThreadSettings.setLocale("de-de");

        Document docDe = new Document();

        Section sectionDe = docDe.getSections().get(0);
        Assert.assertEquals(sectionDe.getPageSetup().getLeftMargin(), 70.85); // 2.5 cm         
        Assert.assertEquals(sectionDe.getPageSetup().getRightMargin(), 70.85); // 2.5 cm
        Assert.assertEquals(sectionDe.getPageSetup().getTopMargin(), 70.85); // 2.5 cm
        Assert.assertEquals(sectionDe.getPageSetup().getBottomMargin(), 56.7); // 2 cm
        Assert.assertEquals(sectionDe.getPageSetup().getHeaderDistance(), 35.4); // 1.25 cm
        Assert.assertEquals(sectionDe.getPageSetup().getFooterDistance(), 35.4); // 1.25 cm
        Assert.assertEquals(sectionDe.getPageSetup().getTextColumns().getSpacing(), 35.4); // 1.25 cm

        //Change page defaults
        sectionDe.getPageSetup().setLeftMargin(90.0); // 3.17 cm
        sectionDe.getPageSetup().setRightMargin(90.0); // 3.17 cm
        sectionDe.getPageSetup().setTopMargin(72.0); // 2.54 cm
        sectionDe.getPageSetup().setBottomMargin(72.0); // 2.54 cm
        sectionDe.getPageSetup().setHeaderDistance(35.4); // 1.25 cm
        sectionDe.getPageSetup().setFooterDistance(35.4); // 1.25 cm
        sectionDe.getPageSetup().getTextColumns().setSpacing(35.4); // 1.25 cm

        ByteArrayOutputStream dstStream = new ByteArrayOutputStream();
        docDe.save(dstStream, SaveFormat.DOCX);

        Section sectionDeAfter = docDe.getSections().get(0);
        Assert.assertEquals(sectionDeAfter.getPageSetup().getLeftMargin(), 90.0); // 3.17 cm         
        Assert.assertEquals(sectionDeAfter.getPageSetup().getRightMargin(), 90.0); // 3.17 cm
        Assert.assertEquals(sectionDeAfter.getPageSetup().getTopMargin(), 72.0); // 2.54 cm
        Assert.assertEquals(sectionDeAfter.getPageSetup().getBottomMargin(), 72.0); // 2.54 cm
        Assert.assertEquals(sectionDeAfter.getPageSetup().getHeaderDistance(), 35.4); // 1.25 cm
        Assert.assertEquals(sectionDeAfter.getPageSetup().getFooterDistance(), 35.4); // 1.25 cm
        Assert.assertEquals(sectionDeAfter.getPageSetup().getTextColumns().getSpacing(), 35.4); // 1.25 cm
    }
}

