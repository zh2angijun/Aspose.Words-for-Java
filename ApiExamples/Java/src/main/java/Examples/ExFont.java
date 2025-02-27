package Examples;

//////////////////////////////////////////////////////////////////////////
// Copyright (c) 2001-2019 Aspose Pty Ltd. All Rights Reserved.
//
// This file is part of Aspose.Words. The source code in this file
// is only intended as a supplement to the documentation, and is provided
// "as is", without warranty of any kind, either expressed or implied.
//////////////////////////////////////////////////////////////////////////


import com.aspose.words.*;
import com.aspose.words.Font;
import com.aspose.words.Shape;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

public class ExFont extends ApiExampleBase {
    @Test
    public void createFormattedRun() throws Exception {
        //ExStart
        //ExFor:Document.#ctor
        //ExFor:Font
        //ExFor:Font.Name
        //ExFor:Font.Size
        //ExFor:Font.HighlightColor
        //ExFor:Run
        //ExFor:Run.#ctor(DocumentBase,String)
        //ExFor:Story.FirstParagraph
        //ExSummary:Shows how to add a formatted run of text to a document using the object model.
        // Create an empty document. It contains one empty paragraph.
        Document doc = new Document();

        // Create a new run of text.
        Run run = new Run(doc, "Hello");

        // Specify character formatting for the run of text.
        Font f = run.getFont();
        f.setName("Courier New");
        f.setSize(36);
        f.setHighlightColor(Color.YELLOW);

        // Append the run of text to the end of the first paragraph
        // in the body of the first section of the document.
        doc.getFirstSection().getBody().getFirstParagraph().appendChild(run);
        //ExEnd
    }

    @Test
    public void caps() throws Exception {
        //ExStart
        //ExFor:Font.AllCaps
        //ExFor:Font.SmallCaps
        //ExSummary:Shows how to use all capitals and small capitals character formatting properties.
        // Create an empty document. It contains one empty paragraph.
        Document doc = new Document();

        // Get the paragraph from the document, we will be adding runs of text to it.
        Paragraph para = (Paragraph) doc.getChild(NodeType.PARAGRAPH, 0, true);

        Run run = new Run(doc, "All capitals");
        run.getFont().setAllCaps(true);
        para.appendChild(run);

        run = new Run(doc, "SMALL CAPITALS");
        run.getFont().setSmallCaps(true);
        para.appendChild(run);
        //ExEnd
    }

    @Test
    public void getDocumentFonts() throws Exception {
        //ExStart
        //ExFor:FontInfoCollection
        //ExFor:DocumentBase.FontInfos
        //ExFor:FontInfo
        //ExFor:FontInfo.Name
        //ExFor:FontInfo.IsTrueType
        //ExSummary:Shows how to gather the details of what fonts are present in a document.
        Document doc = new Document(getMyDir() + "Document.doc");

        FontInfoCollection fonts = doc.getFontInfos();
        int fontIndex = 1;

        // The fonts info extracted from this document does not necessarily mean that the fonts themselves are
        // used in the document. If a font is present but not used then most likely they were referenced at some time
        // and then removed from the Document.
        for (FontInfo info : fonts) {
            // Print out some important details about the font.
            System.out.println(MessageFormat.format("Font #{0}", fontIndex));
            System.out.println(MessageFormat.format("Name: {0}", info.getName()));
            System.out.println(MessageFormat.format("IsTrueType: {0}", info.isTrueType()));
            fontIndex++;
        }
        //ExEnd
    }

    @Test(description = "WORDSNET-16234")
    public void defaultValuesEmbeddedFontsParameters() throws Exception {
        Document doc = new Document();

        Assert.assertFalse(doc.getFontInfos().getEmbedTrueTypeFonts());
        Assert.assertFalse(doc.getFontInfos().getEmbedSystemFonts());
        Assert.assertFalse(doc.getFontInfos().getSaveSubsetFonts());
    }


    @Test
    public void fontInfoCollection() throws Exception {
        //ExStart
        //ExFor:FontInfoCollection
        //ExFor:DocumentBase.FontInfos
        //ExFor:FontInfoCollection.EmbedTrueTypeFonts
        //ExFor:FontInfoCollection.EmbedSystemFonts
        //ExFor:FontInfoCollection.SaveSubsetFonts
        //ExSummary:Shows how to save a document with embedded TrueType fonts
        Document doc = new Document(getMyDir() + "Document.docx");

        FontInfoCollection fontInfos = doc.getFontInfos();
        fontInfos.setEmbedTrueTypeFonts(true);
        fontInfos.setEmbedSystemFonts(false);
        fontInfos.setSaveSubsetFonts(false);

        doc.save(getMyDir() + "/Artifacts/Document.docx");
        //ExEnd
    }

    @Test(dataProvider = "workWithEmbeddedFontsDataProvider")
    public void workWithEmbeddedFonts(final boolean embedTrueTypeFonts, final boolean embedSystemFonts, final boolean saveSubsetFonts) throws Exception {
        Document doc = new Document(getMyDir() + "Document.doc");

        FontInfoCollection fontInfos = doc.getFontInfos();
        fontInfos.setEmbedTrueTypeFonts(embedTrueTypeFonts);
        fontInfos.setEmbedSystemFonts(embedSystemFonts);
        fontInfos.setSaveSubsetFonts(saveSubsetFonts);

        doc.save(getMyDir() + "/Artifacts/Document.docx");
    }

    //JAVA-added data provider for test method
    @DataProvider(name = "workWithEmbeddedFontsDataProvider")
    public static Object[][] workWithEmbeddedFontsDataProvider() {
        return new Object[][]
                {
                        {true, false, false},
                        {true, true, false},
                        {true, true, true},
                        {true, false, true},
                        {false, false, false},
                };
    }

    @Test
    public void strikeThrough() throws Exception {
        //ExStart
        //ExFor:Font.StrikeThrough
        //ExFor:Font.DoubleStrikeThrough
        //ExSummary:Shows how to use strike-through character formatting properties.
        // Create an empty document. It contains one empty paragraph.
        Document doc = new Document();

        // Get the paragraph from the document, we will be adding runs of text to it.
        Paragraph para = (Paragraph) doc.getChild(NodeType.PARAGRAPH, 0, true);

        Run run = new Run(doc, "Double strike through text");
        run.getFont().setDoubleStrikeThrough(true);
        para.appendChild(run);

        run = new Run(doc, "Single strike through text");
        run.getFont().setStrikeThrough(true);
        para.appendChild(run);
        //ExEnd
    }

    @Test
    public void positionSubscript() throws Exception {
        //ExStart
        //ExFor:Font.Position
        //ExFor:Font.Subscript
        //ExFor:Font.Superscript
        //ExSummary:Shows how to use subscript, superscript, complex script, text effects, and baseline text position properties.
        // Create an empty document. It contains one empty paragraph.
        Document doc = new Document();

        // Get the paragraph from the document, we will be adding runs of text to it.
        Paragraph para = (Paragraph) doc.getChild(NodeType.PARAGRAPH, 0, true);

        // Add a run of text that is raised 5 points above the baseline.
        Run run = new Run(doc, "Raised text");
        run.getFont().setPosition(5);
        para.appendChild(run);

        // Add a run of normal text.
        run = new Run(doc, "Normal text");
        para.appendChild(run);

        // Add a run of text that appears as subscript.
        run = new Run(doc, "Subscript");
        run.getFont().setSubscript(true);
        para.appendChild(run);

        // Add a run of text that appears as superscript.
        run = new Run(doc, "Superscript");
        run.getFont().setSuperscript(true);
        para.appendChild(run);
        //ExEnd
    }

    @Test
    public void scalingSpacing() throws Exception {
        //ExStart
        //ExFor:Font.Scaling
        //ExFor:Font.Spacing
        //ExSummary:Shows how to use character scaling and spacing properties.
        // Create an empty document. It contains one empty paragraph.
        Document doc = new Document();

        // Get the paragraph from the document, we will be adding runs of text to it.
        Paragraph para = (Paragraph) doc.getChild(NodeType.PARAGRAPH, 0, true);

        // Add a run of text with characters 150% width of normal characters.
        Run run = new Run(doc, "Wide characters");
        run.getFont().setScaling(150);
        para.appendChild(run);

        // Add a run of text with extra 1pt space between characters.
        run = new Run(doc, "Expanded by 1pt");
        run.getFont().setSpacing(1);
        para.appendChild(run);

        // Add a run of text with space between characters reduced by 1pt.
        run = new Run(doc, "Condensed by 1pt");
        run.getFont().setSpacing(-1);
        para.appendChild(run);
        //ExEnd
    }

    @Test
    public void embossItalic() throws Exception {
        Document doc = new Document();
        //ExStart
        //ExFor:Font.Emboss
        //ExFor:Font.Italic
        //ExSummary:Shows how to create a run of formatted text.
        Run run = new Run(doc, "Hello");
        run.getFont().setEmboss(true);
        run.getFont().setItalic(true);
        //ExEnd
    }

    @Test
    public void engrave() throws Exception {
        Document doc = new Document();
        //ExStart
        //ExFor:Font.Engrave
        //ExSummary:Shows how to create a run of text formatted as engraved.
        Run run = new Run(doc, "Hello");
        run.getFont().setEngrave(true);
        //ExEnd
    }

    @Test
    public void shadow() throws Exception {
        Document doc = new Document();
        //ExStart
        //ExFor:Font.Shadow
        //ExSummary:Shows how to create a run of text formatted with a shadow.
        Run run = new Run(doc, "Hello");
        run.getFont().setShadow(true);
        //ExEnd
    }

    @Test
    public void outline() throws Exception {
        Document doc = new Document();
        //ExStart
        //ExFor:Font.Outline
        //ExSummary:Shows how to create a run of text formatted as outline.
        Run run = new Run(doc, "Hello");
        run.getFont().setOutline(true);
        //ExEnd
    }

    @Test
    public void hidden() throws Exception {
        Document doc = new Document();
        //ExStart
        //ExFor:Font.Hidden
        //ExSummary:Shows how to create a hidden run of text.
        Run run = new Run(doc, "Hello");
        run.getFont().setHidden(true);
        //ExEnd
    }

    @Test
    public void kerning() throws Exception {
        Document doc = new Document();
        //ExStart
        //ExFor:Font.Kerning
        //ExSummary:Shows how to specify the font size at which kerning starts.
        Run run = new Run(doc, "Hello");
        run.getFont().setKerning(24);
        //ExEnd
    }

    @Test
    public void noProofing() throws Exception {
        Document doc = new Document();
        //ExStart
        //ExFor:Font.NoProofing
        //ExSummary:Shows how to specify that the run of text is not to be spell checked by Microsoft Word.
        Run run = new Run(doc, "Hello");
        run.getFont().setNoProofing(true);
        //ExEnd
    }

    @Test
    public void localeId() throws Exception {
        Document doc = new Document();

        //ExStart
        //ExFor:Font.LocaleId
        //ExSummary:Shows how to specify the language of a text run so Microsoft Word can use a proper spell checker.
        //Create a run of text that contains Russian text.
        Run run = new Run(doc, "Привет");

        //Specify the locale so Microsoft Word recognizes this text as Russian.
        //For the list of locale identifiers see https://docs.microsoft.com/en-us/deployoffice/office2016/language-identifiers-and-optionstate-id-values-in-office-2016
        run.getFont().setLocaleId(1049);
        //ExEnd
    }

    @Test
    public void underlines() throws Exception {
        Document doc = new Document();
        //ExStart
        //ExFor:Font.Underline
        //ExFor:Font.UnderlineColor
        //ExSummary:Shows how use the underline character formatting properties.
        Run run = new Run(doc, "Hello");
        run.getFont().setUnderline(Underline.DOTTED);
        run.getFont().setUnderlineColor(Color.RED);
        //ExEnd
    }

    @Test
    public void complexScript() throws Exception {
        Document doc = new Document();
        //ExStart
        //ExFor:Font.ComplexScript
        //ExSummary:Shows how to make a run that's always treated as complex script.
        Run run = new Run(doc, "Complex script");
        run.getFont().setComplexScript(true);
        //ExEnd
    }

    @Test
    public void sparklingText() throws Exception {
        Document doc = new Document();
        //ExStart
        //ExFor:Font.TextEffect
        //ExSummary:Shows how to apply a visual effect to a run.
        Run run = new Run(doc, "Text with effect");
        run.getFont().setTextEffect(TextEffect.SPARKLE_TEXT);
        //ExEnd
    }

    @Test
    public void shading() throws Exception {
        //ExStart
        //ExFor:Font.Shading
        //ExSummary:Shows how to apply shading for a run of text.
        DocumentBuilder builder = new DocumentBuilder();

        Shading shd = builder.getFont().getShading();
        shd.setTexture(TextureIndex.TEXTURE_DIAGONAL_CROSS);
        shd.setBackgroundPatternColor(Color.BLUE);
        shd.setForegroundPatternColor(new Color(138, 43, 226)); // Violet-blue

        builder.getFont().setColor(Color.WHITE);

        builder.writeln("White text on a blue background with texture.");
        //ExEnd
    }

    @Test
    public void bidi() throws Exception {
        //ExStart
        //ExFor:Font.Bidi
        //ExFor:Font.NameBi
        //ExFor:Font.SizeBi
        //ExFor:Font.ItalicBi
        //ExFor:Font.BoldBi
        //ExFor:Font.LocaleIdBi
        //ExSummary:Shows how to insert and format right-to-left text.
        DocumentBuilder builder = new DocumentBuilder();

        // Signal to Microsoft Word that this run of text contains right-to-left text.
        builder.getFont().setBidi(true);

        // Specify the font and font size to be used for the right-to-left text.
        builder.getFont().setNameBi("Andalus");
        builder.getFont().setSizeBi(48);

        // Specify that the right-to-left text in this run is bold and italic.
        builder.getFont().setItalicBi(true);
        builder.getFont().setBoldBi(true);

        // Specify the locale so Microsoft Word recognizes this text as Arabic - Saudi Arabia.
        // For the list of locale identifiers see https://docs.microsoft.com/en-us/deployoffice/office2016/language-identifiers-and-optionstate-id-values-in-office-2016
        builder.getFont().setLocaleIdBi(1025);

        // Insert some Arabic text.
        builder.writeln("مرحبًا");

        builder.getDocument().save(getArtifactsDir() + "Font.Bidi.doc");
        //ExEnd
    }

    @Test
    public void farEast() throws Exception {
        //ExStart
        //ExFor:Font.NameFarEast
        //ExFor:Font.LocaleIdFarEast
        //ExSummary:Shows how to insert and format text in Chinese or any other Far East language.
        DocumentBuilder builder = new DocumentBuilder();

        builder.getFont().setSize(48);

        // Specify the font name. Make sure it the font has the glyphs that you want to display.
        builder.getFont().setNameFarEast("SimSun");

        // Specify the locale so Microsoft Word recognizes this text as Chinese.
        // For the list of locale identifiers see https://docs.microsoft.com/en-us/deployoffice/office2016/language-identifiers-and-optionstate-id-values-in-office-2016
        builder.getFont().setLocaleIdFarEast(2052);

        // Insert some Chinese text.
        builder.writeln("你好世界");

        builder.getDocument().save(getArtifactsDir() + "Font.FarEast.doc");
        //ExEnd
    }

    @Test
    public void names() throws Exception {
        //ExStart
        //ExFor:Font.NameAscii
        //ExFor:Font.NameOther
        //ExSummary:A pretty unusual example of how Microsoft Word can combine two different fonts in one run.
        DocumentBuilder builder = new DocumentBuilder();

        // This tells Microsoft Word to use Arial for characters 0..127 and
        // Times New Roman for characters 128..255.
        // Looks like a pretty strange case to me, but it is possible.
        builder.getFont().setNameAscii("Arial");
        builder.getFont().setNameOther("Times New Roman");

        builder.writeln("Hello, Привет");

        builder.getDocument().save(getArtifactsDir() + "Font.Names.doc");
        //ExEnd
    }

    @Test
    public void changeStyleIdentifier() throws Exception {
        //ExStart
        //ExFor:Font.StyleIdentifier
        //ExFor:StyleIdentifier
        //ExSummary:Shows how to use style identifier to find text formatted with a specific character style and apply different character style.
        Document doc = new Document(getMyDir() + "Font.StyleIdentifier.doc");

        // Select all run nodes in the document.
        NodeCollection runs = doc.getChildNodes(NodeType.RUN, true);

        // Loop through every run node.
        for (Run run : (Iterable<Run>) runs) {
            // If the character style of the run is what we want, do what we need. Change the style in this case.
            // Note that using StyleIdentifier we can identify a built-in style regardless
            // of the language of Microsoft Word used to create the document.
            if (run.getFont().getStyleIdentifier() == StyleIdentifier.EMPHASIS) {
                run.getFont().setStyleIdentifier(StyleIdentifier.STRONG);
            }
        }

        doc.save(getArtifactsDir() + "Font.StyleIdentifier.doc");
        //ExEnd
    }

    @Test
    public void changeStyleName() throws Exception {
        //ExStart
        //ExFor:Font.StyleName
        //ExSummary:Shows how to use style name to find text formatted with a specific character style and apply different character style.
        Document doc = new Document(getMyDir() + "Font.StyleName.doc");

        // Select all run nodes in the document.
        NodeCollection runs = doc.getChildNodes(NodeType.RUN, true);

        // Loop through every run node.
        for (Run run : (Iterable<Run>) runs) {
            // If the character style of the run is what we want, do what we need. Change the style in this case.
            // Note that names of built in styles could be different in documents
            // created by Microsoft Word versions for different languages.
            if (run.getFont().getStyleName().equals("Emphasis")) {
                run.getFont().setStyleName("Strong");
            }
        }

        doc.save(getArtifactsDir() + "Font.StyleName.doc");
        //ExEnd
    }

    @Test
    public void style() throws Exception {
        //ExStart
        //ExFor:Font.Style
        //ExFor:Style.BuiltIn
        //ExSummary:Applies double underline to all runs in a document that are formatted with custom character styles.
        Document doc = new Document(getMyDir() + "Font.Style.doc");

        // Select all run nodes in the document.
        NodeCollection runs = doc.getChildNodes(NodeType.RUN, true);

        // Loop through every run node.
        for (Run run : (Iterable<Run>) runs) {
            Style charStyle = run.getFont().getStyle();
            // If the style of the run is not a built-in character style, apply double underline.
            if (!charStyle.getBuiltIn()) {
                run.getFont().setUnderline(Underline.DOUBLE);
            }
        }

        doc.save(getArtifactsDir() + "Font.Style.doc");
        //ExEnd
    }

    @Test
    public void getAllFonts() throws Exception {
        //ExStart
        //ExFor:Run
        //ExSummary:Gets all fonts used in a document.
        Document doc = new Document(getMyDir() + "Font.Names.doc");

        // Select all runs in the document.
        NodeCollection runs = doc.getChildNodes(NodeType.RUN, true);

        // Use a hashtable so we will keep only unique font names.
        HashMap fontNames = new HashMap();

        for (Run run : (Iterable<Run>) runs) {
            // This adds an entry into the hashmap.
            // The key is the font name. The value is null, we don't need the value.
            fontNames.put(run.getFont().getName(), null);
        }

        // There are two fonts used in this document.
        System.out.println("Font Count: " + fontNames.size());
        //ExEnd

        // Verify the font count is correct.
        Assert.assertEquals(fontNames.size(), 2);

    }

    @Test
    public void recieveFontSubstitutionNotification() throws Exception {
        // Store the font sources currently used so we can restore them later. 
        FontSourceBase[] origFontSources = FontSettings.getDefaultInstance().getFontsSources();

        //ExStart
        //ExFor:IWarningCallback
        //ExFor:DocumentBase.WarningCallback
        //ExFor:Fonts.FontSettings.DefaultInstance
        //ExId:FontSubstitutionNotification
        //ExSummary:Demonstrates how to receive notifications of font substitutions by using IWarningCallback.
        // Load the document to render.
        Document doc = new Document(getMyDir() + "Document.doc");

        // Create a new class implementing IWarningCallback and assign it to the PdfSaveOptions class.
        HandleDocumentWarnings callback = new HandleDocumentWarnings();
        doc.setWarningCallback(callback);

        // We can choose the default font to use in the case of any missing fonts.
        FontSettings.getDefaultInstance().getSubstitutionSettings().getDefaultFontSubstitution().setDefaultFontName("Arial");

        // For testing we will set Aspose.Words to look for fonts only in a folder which doesn't exist. Since Aspose.Words won't
        // find any fonts in the specified directory, then during rendering the fonts in the document will be substituted with the default 
        // font specified under FontSettings.DefaultFontName. We can pick up on this substitution using our callback.
        FontSettings.getDefaultInstance().setFontsFolder("", false);

        // Pass the save options along with the save path to the save method.
        doc.save(getArtifactsDir() + "Rendering.MissingFontNotification.pdf");
        //ExEnd

        Assert.assertTrue(callback.mFontWarnings.getCount() > 0);
        Assert.assertTrue(callback.mFontWarnings.get(0).getWarningType() == WarningType.FONT_SUBSTITUTION);
        Assert.assertTrue(callback.mFontWarnings.get(0).getDescription().equals("Font 'Times New Roman' has not been found. Using 'Fanwood' font instead. Reason: first available font."));

        // Restore default fonts. 
        FontSettings.getDefaultInstance().setFontsSources(origFontSources);
    }

    @Test
    public void getAvailableFonts() {
        //ExStart
        //ExFor:Fonts.PhysicalFontInfo
        //ExFor:FontSourceBase.GetAvailableFonts
        //ExFor:PhysicalFontInfo.FontFamilyName
        //ExFor:PhysicalFontInfo.FullFontName
        //ExFor:PhysicalFontInfo.Version
        //ExFor:PhysicalFontInfo.FilePath
        //ExSummary:Shows how to get available fonts and information about them.
        // Add a new folder source which will instruct Aspose.Words to search the following folder for fonts.
        FontSourceBase[] folderFontSource = {new FolderFontSource(getMyDir() + "MyFonts\\", true)};

        for (PhysicalFontInfo fontInfo : folderFontSource[0].getAvailableFonts()) {
            System.out.println(MessageFormat.format("FontFamilyName : {0}", fontInfo.getFontFamilyName()));
            System.out.println(MessageFormat.format("FullFontName  : {0}", fontInfo.getFullFontName()));
            System.out.println(MessageFormat.format("Version  : {0}", fontInfo.getVersion()));
            System.out.println(MessageFormat.format("FilePath : {0}\n", fontInfo.getFilePath()));
        }
        //ExEnd
    }

    //ExStart
    //ExFor:IWarningCallback
    //ExFor:IWarningCallback.Warning(WarningInfo)
    //ExFor:WarningInfo
    //ExFor:WarningInfo.Description
    //ExFor:WarningInfo.WarningType
    //ExFor:WarningInfoCollection
    //ExFor:WarningInfoCollection.Warning(WarningInfo)
    //ExFor:WarningType
    //ExFor:DocumentBase.WarningCallback
    //ExId:FontSubstitutionWarningCallback
    //ExSummary:Shows how to implement the IWarningCallback to be notified of any font substitution during document save.
    public static class HandleDocumentWarnings implements IWarningCallback {
        /**
         * Our callback only needs to implement the "Warning" method. This method is called whenever there is a
         * potential issue during document processing. The callback can be set to listen for warnings generated during document
         * load and/or document save.
         */
        public void warning(final WarningInfo info) {
            // We are only interested in fonts being substituted.
            if (info.getWarningType() == WarningType.FONT_SUBSTITUTION) {
                System.out.println("Font substitution: " + info.getDescription());
                mFontWarnings.warning(info); //ExSkip
            }
        }

        public WarningInfoCollection mFontWarnings = new WarningInfoCollection(); //ExSkip
    }
    //ExEnd

    @Test
    public void enableFontSubstitutionTrue() throws Exception {
        //ExStart
        //ExFor:Fonts.FontInfoSubstitutionRule
        //ExFor:Fonts.FontSubstitutionSettings.FontInfoSubstitution
        //ExSummary:Shows how to set the property for finding the closest match font among the available font sources instead missing font.
        Document doc = new Document(getMyDir() + "Font.EnableFontSubstitution.docx");

        // Create a new class implementing IWarningCallback and assign it to the PdfSaveOptions class.
        HandleDocumentWarnings callback = new HandleDocumentWarnings();
        doc.setWarningCallback(callback);

        FontSettings fontSettings = new FontSettings();
        fontSettings.getSubstitutionSettings().getDefaultFontSubstitution().setDefaultFontName("Arial");
        fontSettings.getSubstitutionSettings().getFontInfoSubstitution().setEnabled(true);
        //ExEnd

        doc.setFontSettings(fontSettings);
        doc.save(getArtifactsDir() + "Font.EnableFontSubstitution.pdf");

        Pattern pattern = Pattern.compile("Font \'28 Days Later\' has not been found. Using (.*) font instead. Reason: font info substitution.");

        for (WarningInfo fontWarning : callback.mFontWarnings) {
            Matcher match = pattern.matcher(fontWarning.getDescription());
            if (!match.matches()) {
                Assert.fail();
                break;
            }
        }
    }

    @Test
    public void enableFontSubstitutionFalse() throws Exception {
        Document doc = new Document(getMyDir() + "Font.EnableFontSubstitution.docx");

        // Create a new class implementing IWarningCallback and assign it to the PdfSaveOptions class.
        HandleDocumentWarnings callback = new HandleDocumentWarnings();
        doc.setWarningCallback(callback);

        FontSettings fontSettings = new FontSettings();
        fontSettings.getSubstitutionSettings().getDefaultFontSubstitution().setDefaultFontName("Arial");
        fontSettings.getSubstitutionSettings().getFontInfoSubstitution().setEnabled(false);

        doc.setFontSettings(fontSettings);
        doc.save(getArtifactsDir() + "Font.EnableFontSubstitution.pdf");

        Pattern pattern = Pattern.compile("Font '28 Days Later' has not been found. Using (.*) font instead. Reason: default font substitution.");

        for (WarningInfo fontWarning : callback.mFontWarnings) {
            Matcher match = pattern.matcher(fontWarning.getDescription());
            if (!match.matches()) {
                Assert.fail();
                break;
            }
        }
    }

    @Test(groups = "SkipMono")
    public void fontSubstitutionWarnings() throws Exception {
        Document doc = new Document(getMyDir() + "Rendering.doc");

        // Create a new class implementing IWarningCallback and assign it to the PdfSaveOptions class.
        HandleDocumentWarnings callback = new HandleDocumentWarnings();
        doc.setWarningCallback(callback);

        FontSettings fontSettings = new FontSettings();
        fontSettings.getSubstitutionSettings().getDefaultFontSubstitution().setDefaultFontName("Arial");
        fontSettings.getSubstitutionSettings().getTableSubstitution().addSubstitutes("Arial", "Arvo", "Slab");
        fontSettings.setFontsFolder(getMyDir() + "MyFonts\\", false);

        doc.setFontSettings(fontSettings);

        doc.save(getArtifactsDir() + "Rendering.MissingFontNotification.pdf");

        Assert.assertTrue(callback.mFontWarnings.get(0).getDescription().equals("Font 'Arial' has not been found. Using 'Arvo' font instead. Reason: table substitution."));
        Assert.assertTrue(callback.mFontWarnings.get(1).getDescription().equals("Font 'Times New Roman' has not been found. Using 'M+ 2m' font instead. Reason: font info substitution."));
    }

    @Test
    public void fontSubstitutionWarningsClosestMatch() throws Exception {
        Document doc = new Document(getMyDir() + "Font.DisappearingBulletPoints.doc");

        // Create a new class implementing IWarningCallback and assign it to the PdfSaveOptions class.
        HandleDocumentWarnings callback = new HandleDocumentWarnings();
        doc.setWarningCallback(callback);

        doc.save(getArtifactsDir() + "Font.DisappearingBulletPoints.pdf");

        Assert.assertTrue(callback.mFontWarnings.get(0).getDescription().equals("Font 'SymbolPS' has not been found. Using 'Wingdings' font instead. Reason: font info substitution."));
    }

    @Test
    public void setFontAutoColor() throws Exception {
        //ExStart
        //ExFor:Font.AutoColor
        //ExSummary:Shows how calculated color of the text (black or white) to be used for 'auto color'
        Run run = new Run(new Document());

        // Remove direct color, so it can be calculated automatically with Font.AutoColor.
        run.getFont().setColor(new Color(0, 0, 0, 0));

        // When we set black color for background, autocolor for font must be white
        run.getFont().getShading().setBackgroundPatternColor(Color.BLACK);
        Assert.assertEquals(run.getFont().getAutoColor(), Color.WHITE);

        // When we set white color for background, autocolor for font must be black
        run.getFont().getShading().setBackgroundPatternColor(Color.WHITE);
        Assert.assertEquals(run.getFont().getAutoColor(), Color.BLACK);
        //ExEnd
    }

    //ExStart
    //ExFor:Font.Hidden
    //ExFor:Paragraph.Accept
    //ExFor:DocumentVisitor.VisitParagraphStart(Paragraph)
    //ExFor:DocumentVisitor.VisitFormField(FormField)
    //ExFor:DocumentVisitor.VisitTableEnd(Table)
    //ExFor:DocumentVisitor.VisitCellEnd(Cell)
    //ExFor:DocumentVisitor.VisitRowEnd(Row)
    //ExFor:DocumentVisitor.VisitSpecialChar(SpecialChar)
    //ExFor:DocumentVisitor.VisitGroupShapeStart(GroupShape)
    //ExFor:DocumentVisitor.VisitShapeStart(Shape)
    //ExFor:DocumentVisitor.VisitCommentStart(Comment)
    //ExFor:DocumentVisitor.VisitFootnoteStart(Footnote)
    //ExFor:SpecialChar
    //ExFor:Node.Accept
    //ExFor:Paragraph.ParagraphBreakFont
    //ExFor:Table.Accept
    //ExSummary:Implements the Visitor Pattern to remove all content formatted as hidden from the document.
    @Test //ExSkip
    public void removeHiddenContentFromDocument() throws Exception {
        // Open the document we want to remove hidden content from.
        Document doc = new Document(getMyDir() + "Font.Hidden.doc");

        // Create an object that inherits from the DocumentVisitor class.
        RemoveHiddenContentVisitor hiddenContentRemover = new RemoveHiddenContentVisitor();

        // This is the well known Visitor pattern. Get the model to accept a visitor.
        // The model will iterate through itself by calling the corresponding methods
        // on the visitor object (this is called visiting).

        // We can run it over the entire the document like so:
        doc.accept(hiddenContentRemover);

        // Or we can run it on only a specific node.
        Paragraph para = (Paragraph) doc.getChild(NodeType.PARAGRAPH, 4, true);
        para.accept(hiddenContentRemover);

        // Or over a different type of node like below.
        Table table = (Table) doc.getChild(NodeType.TABLE, 0, true);
        table.accept(hiddenContentRemover);

        doc.save(getArtifactsDir() + "Font.Hidden.doc");

        Assert.assertEquals(doc.getChildNodes(NodeType.PARAGRAPH, true).getCount(), 13); //ExSkip
        Assert.assertEquals(doc.getChildNodes(NodeType.TABLE, true).getCount(), 1); //ExSkip
    }

    /**
     * This class when executed will remove all hidden content from the Document. Implemented as a Visitor.
     */
    private class RemoveHiddenContentVisitor extends DocumentVisitor {
        /**
         * Called when a FieldStart node is encountered in the document.
         */
        public int visitFieldStart(final FieldStart fieldStart) throws Exception {
            // If this node is hidden, then remove it.
            if (isHidden(fieldStart)) {
                fieldStart.remove();
            }

            return VisitorAction.CONTINUE;
        }

        /**
         * Called when a FieldEnd node is encountered in the document.
         */
        public int visitFieldEnd(final FieldEnd fieldEnd) throws Exception {
            if (isHidden(fieldEnd)) {
                fieldEnd.remove();
            }

            return VisitorAction.CONTINUE;
        }

        /**
         * Called when a FieldSeparator node is encountered in the document.
         */
        public int visitFieldSeparator(final FieldSeparator fieldSeparator) throws Exception {
            if (isHidden(fieldSeparator)) {
                fieldSeparator.remove();
            }

            return VisitorAction.CONTINUE;
        }

        /**
         * Called when a Run node is encountered in the document.
         */
        public int visitRun(final Run run) throws Exception {
            if (isHidden(run)) {
                run.remove();
            }

            return VisitorAction.CONTINUE;
        }

        /**
         * Called when a Paragraph node is encountered in the document.
         */
        public int visitParagraphStart(final Paragraph paragraph) throws Exception {
            if (isHidden(paragraph)) {
                paragraph.remove();
            }

            return VisitorAction.CONTINUE;
        }

        /**
         * Called when a FormField is encountered in the document.
         */
        public int visitFormField(final FormField field) throws Exception {
            if (isHidden(field)) {
                field.remove();
            }

            return VisitorAction.CONTINUE;
        }

        /**
         * Called when a GroupShape is encountered in the document.
         */
        public int visitGroupShapeStart(final GroupShape groupShape) throws Exception {
            if (isHidden(groupShape)) {
                groupShape.remove();
            }

            return VisitorAction.CONTINUE;
        }

        /**
         * Called when a Shape is encountered in the document.
         */
        public int visitShapeStart(final Shape shape) throws Exception {
            if (isHidden(shape)) {
                shape.remove();
            }

            return VisitorAction.CONTINUE;
        }

        /**
         * Called when a Comment is encountered in the document.
         */
        public int visitCommentStart(final Comment comment) throws Exception {
            if (isHidden(comment)) {
                comment.remove();
            }

            return VisitorAction.CONTINUE;
        }

        /**
         * Called when a Footnote is encountered in the document.
         */
        public int visitFootnoteStart(final Footnote footnote) throws Exception {
            if (isHidden(footnote)) {
                footnote.remove();
            }

            return VisitorAction.CONTINUE;
        }

        /**
         * Called when visiting of a Table node is ended in the document.
         */
        public int visitTableEnd(final Table table) {
            // At the moment there is no way to tell if a particular Table/Row/Cell is hidden.
            // Instead, if the content of a table is hidden, then all inline child nodes of the table should be
            // hidden and thus removed by previous visits as well. This will result in the container being empty
            // so if this is the case we know to remove the table node.
            //
            // Note that a table which is not hidden but simply has no content will not be affected by this algorthim,
            // as technically they are not completely empty (for example a properly formed Cell will have at least
            // an empty paragraph in it)
            if (!table.hasChildNodes()) {
                table.remove();
            }

            return VisitorAction.CONTINUE;
        }

        /**
         * Called when visiting of a Cell node is ended in the document.
         */
        public int visitCellEnd(final Cell cell) {
            if (!cell.hasChildNodes() && cell.getParentNode() != null) {
                cell.remove();
            }

            return VisitorAction.CONTINUE;
        }

        /**
         * Called when visiting of a Row node is ended in the document.
         */
        public int visitRowEnd(final Row row) {
            if (!row.hasChildNodes() && row.getParentNode() != null) {
                row.remove();
            }

            return VisitorAction.CONTINUE;
        }

        /**
         * Called when a SpecialCharacter is encountered in the document.
         */
        public int visitSpecialChar(final SpecialChar character) throws Exception {
            if (isHidden(character)) {
                character.remove();
            }

            return VisitorAction.CONTINUE;
        }

        /**
         * Returns true if the node passed is set as hidden, returns false if it is visible.
         */
        private boolean isHidden(final Node node) {
            if (node instanceof Inline) {
                // If the node is Inline then cast it to retrieve the Font property which contains the hidden property
                Inline currentNode = (Inline) node;
                return currentNode.getFont().getHidden();
            } else if (node.getNodeType() == NodeType.PARAGRAPH) {
                // If the node is a paragraph cast it to retrieve the ParagraphBreakFont which contains the hidden property
                Paragraph para = (Paragraph) node;
                return para.getParagraphBreakFont().getHidden();
            } else if (node instanceof ShapeBase) {
                // Node is a shape or groupshape.
                ShapeBase shape = (ShapeBase) node;
                return shape.getFont().getHidden();
            } else if (node instanceof InlineStory) {
                // Node is a comment or footnote.
                InlineStory inlineStory = (InlineStory) node;
                return inlineStory.getFont().getHidden();
            }

            // A node that is passed to this method which does not contain a hidden property will end up here.
            // By default nodes are not hidden so return false.
            return false;
        }
    }
    //ExEnd

    @Test
    public void blankDocumentFonts() throws Exception {
        //ExStart
        //ExFor:Fonts.FontInfoCollection.Contains(String)
        //ExFor:Fonts.FontInfoCollection.Count
        //ExSummary:Shows info about the fonts that are present in the blank document.
        // Create a new document
        Document doc = new Document();
        // A blank document comes with 3 fonts
        Assert.assertEquals(doc.getFontInfos().getCount(), 3);
        Assert.assertEquals(doc.getFontInfos().contains("Times New Roman"), true);
        Assert.assertEquals(doc.getFontInfos().contains("Symbol"), true);
        Assert.assertEquals(doc.getFontInfos().contains("Arial"), true);
        //ExEnd
    }

    @Test
    public void extractEmbeddedFont() throws Exception {
        //ExStart
        //ExFor:Fonts.EmbeddedFontFormat
        //ExFor:Fonts.EmbeddedFontStyle
        //ExFor:Fonts.FontInfo.GetEmbeddedFont(EmbeddedFontFormat,EmbeddedFontStyle)
        //ExFor:Fonts.FontInfoCollection.Item(Int32)
        //ExFor:Fonts.FontInfoCollection.Item(String)
        //ExSummary:Shows how to extract embedded font from a document.
        Document doc = new Document(getMyDir() + "Font.Embedded.docx");
        // Let's get the font we are interested in
        FontInfo mittelschriftInfo = doc.getFontInfos().get(2);
        // We can now extract this embedded font
        byte[] embeddedFontBytes = mittelschriftInfo.getEmbeddedFont(EmbeddedFontFormat.OPEN_TYPE, EmbeddedFontStyle.REGULAR);
        Assert.assertNotNull(embeddedFontBytes);
        // Then we can save the font to our directory
        Files.write(Paths.get(getArtifactsDir() + "Alte DIN 1451 Mittelschrift.ttf"), embeddedFontBytes,
                new StandardOpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.APPEND});

        // If we want to extract a font from a .doc as opposed to a .docx, we need to make sure to set the appropriate embedded font format
        doc = new Document(getMyDir() + "Font.Embedded.doc");

        Assert.assertNull(doc.getFontInfos().get("Alte DIN 1451 Mittelschrift").getEmbeddedFont(EmbeddedFontFormat.OPEN_TYPE, EmbeddedFontStyle.REGULAR));
        Assert.assertNotNull(doc.getFontInfos().get("Alte DIN 1451 Mittelschrift").getEmbeddedFont(EmbeddedFontFormat.EMBEDDED_OPEN_TYPE, EmbeddedFontStyle.REGULAR));
        //ExEnd
    }

    @Test
    public void getFontInfoFromFile() throws Exception {
        //ExStart
        //ExFor:Fonts.FontFamily
        //ExFor:Fonts.FontPitch
        //ExFor:Fonts.FontInfo.AltName
        //ExFor:Fonts.FontInfo.Charset
        //ExFor:Fonts.FontInfo.Family
        //ExFor:Fonts.FontInfo.Panose
        //ExFor:Fonts.FontInfo.Pitch
        //ExFor:Fonts.FontInfoCollection.GetEnumerator
        //ExSummary:Shows how to get information about each font in a document.
        Document doc = new Document(getMyDir() + "Font.Embedded.docx");

        // We can iterate over all the fonts with an enumerator
        Iterator<FontInfo> fontCollectionEnumerator = doc.getFontInfos().iterator();
        // Print detailed information about each font to the console
        while (fontCollectionEnumerator.hasNext()) {
            FontInfo fontInfo = fontCollectionEnumerator.next();
            if (fontInfo != null) {
                System.out.println("Font name: " + fontInfo.getName());
                System.out.println("Alt name: " + fontInfo.getAltName()); // Alt names are usually blank
                System.out.println("\t- Family: " + fontInfo.getFamily());
                System.out.println("\t- " + (fontInfo.isTrueType() ? "Is TrueType" : "Is not TrueType"));
                System.out.println("\t- Pitch: " + fontInfo.getPitch());
                System.out.println("\t- Charset: " + fontInfo.getCharset());
                System.out.println("\t- Panose:");
                System.out.println("\t\tFamily Kind: " + (fontInfo.getPanose()[0] & 0xFF));
                System.out.println("\t\tSerif Style: " + (fontInfo.getPanose()[1] & 0xFF));
                System.out.println("\t\tWeight: " + (fontInfo.getPanose()[2] & 0xFF));
                System.out.println("\t\tProportion: " + (fontInfo.getPanose()[3] & 0xFF));
                System.out.println("\t\tContrast: " + (fontInfo.getPanose()[4] & 0xFF));
                System.out.println("\t\tStroke Variation: " + (fontInfo.getPanose()[5] & 0xFF));
                System.out.println("\t\tArm Style: " + (fontInfo.getPanose()[6] & 0xFF));
                System.out.println("\t\tLetterform: " + (fontInfo.getPanose()[7] & 0xFF));
                System.out.println("\t\tMidline: " + (fontInfo.getPanose()[8] & 0xFF));
                System.out.println("\t\tX-Height: " + (fontInfo.getPanose()[9] & 0xFF));
            }
        }
        //ExEnd

        Assert.assertEquals(doc.getFontInfos().get("Calibri").getPanose(), new byte[]{2, 15, 5, 2, 2, 2, 4, 3, 2, 4});
        Assert.assertEquals(doc.getFontInfos().get("Times New Roman").getPanose(), new byte[]{2, 2, 6, 3, 5, 4, 5, 2, 3, 4});
    }

    @Test
    public void fontSourceFile() throws Exception {
        //ExStart
        //ExFor:Fonts.FileFontSource
        //ExFor:Fonts.FileFontSource.#ctor(String)
        //ExFor:Fonts.FileFontSource.#ctor(String, Int32)
        //ExFor:Fonts.FileFontSource.FilePath
        //ExFor:Fonts.FileFontSource.Type
        //ExFor:Fonts.FontSourceBase
        //ExFor:Fonts.FontSourceBase.Priority
        //ExFor:Fonts.FontSourceBase.Type
        //ExFor:Fonts.FontSourceType
        //ExSummary:Shows how to create a file font source.
        Document doc = new Document();

        // Create a font settings object for our document
        doc.setFontSettings(new FontSettings());

        // Create a font source from a file in our system
        FileFontSource fileFontSource = new FileFontSource(getMyDir() + "Alte DIN 1451 Mittelschrift.ttf", 0);

        // Import the font source into our document
        doc.getFontSettings().setFontsSources(new FontSourceBase[]{fileFontSource});

        Assert.assertEquals(fileFontSource.getFilePath(), getMyDir() + "Alte DIN 1451 Mittelschrift.ttf");
        Assert.assertEquals(fileFontSource.getType(), FontSourceType.FONT_FILE);
        Assert.assertEquals(fileFontSource.getPriority(), 0);
        //ExEnd
    }

    @Test
    public void fontSourceFolder() throws Exception {
        //ExStart
        //ExFor:Fonts.FolderFontSource
        //ExFor:Fonts.FolderFontSource.#ctor(String, Boolean)
        //ExFor:Fonts.FolderFontSource.#ctor(String, Boolean, Int32)
        //ExFor:Fonts.FolderFontSource.FolderPath
        //ExFor:Fonts.FolderFontSource.ScanSubfolders
        //ExFor:Fonts.FolderFontSource.Type
        //ExSummary:Shows how to create a folder font source.
        Document doc = new Document();

        // Create a font settings object for our document
        doc.setFontSettings(new FontSettings());

        // Create a font source from a folder that contains font files
        FolderFontSource folderFontSource = new FolderFontSource(getMyDir() + "MyFonts", false, 1);

        // Add that source to our document
        doc.getFontSettings().setFontsSources(new FontSourceBase[]{folderFontSource});

        Assert.assertEquals(folderFontSource.getFolderPath(), getMyDir() + "MyFonts");
        Assert.assertEquals(folderFontSource.getScanSubfolders(), false);
        Assert.assertEquals(folderFontSource.getType(), FontSourceType.FONTS_FOLDER);
        Assert.assertEquals(folderFontSource.getPriority(), 1);
        //ExEnd
    }

    @Test
    public void fontSourceMemory() throws Exception {
        //ExStart
        //ExFor:Fonts.MemoryFontSource
        //ExFor:Fonts.MemoryFontSource.#ctor(Byte[])
        //ExFor:Fonts.MemoryFontSource.#ctor(Byte[], Int32)
        //ExFor:Fonts.MemoryFontSource.FontData
        //ExFor:Fonts.MemoryFontSource.Type
        //ExSummary:Shows how to create a memory font source.
        Document doc = new Document();

        // Create a font settings object for our document
        doc.setFontSettings(new FontSettings());

        // Import a font file, putting its contents into a byte array
        byte[] fontBytes = Files.readAllBytes(Paths.get(getMyDir() + "Alte DIN 1451 Mittelschrift.ttf"));

        // Create a memory font source from our array
        MemoryFontSource memoryFontSource = new MemoryFontSource(fontBytes, 0);

        // Add that font source to our document
        doc.getFontSettings().setFontsSources(new FontSourceBase[]{memoryFontSource});

        Assert.assertEquals(memoryFontSource.getFontData().length, 52208);
        Assert.assertEquals(memoryFontSource.getType(), FontSourceType.MEMORY_FONT);
        Assert.assertEquals(memoryFontSource.getPriority(), 0);
        //ExEnd
    }

    @Test
    public void fontSourceSystem() throws Exception {
        //ExStart
        //ExFor:TableSubstitutionRule.AddSubstitutes(String, String[])
        //ExFor:FontSubstitutionRule.Enabled
        //ExFor:TableSubstitutionRule.GetSubstitutes(String)
        //ExFor:Fonts.FontSettings.ResetFontSources
        //ExFor:Fonts.FontSettings.SubstitutionSettings
        //ExFor:Fonts.FontSubstitutionSettings
        //ExFor:Fonts.SystemFontSource
        //ExFor:Fonts.SystemFontSource.#ctor
        //ExFor:Fonts.SystemFontSource.#ctor(Int32)
        //ExFor:Fonts.SystemFontSource.GetSystemFontFolders
        //ExFor:Fonts.SystemFontSource.Type
        //ExSummary:Shows how to access a document's system font source and set font substitutes.
        Document doc = new Document();

        // Create a font settings object for our document
        doc.setFontSettings(new FontSettings());

        // By default we always start with a system font source
        Assert.assertEquals(doc.getFontSettings().getFontsSources().length, 1);

        SystemFontSource systemFontSource = (SystemFontSource) doc.getFontSettings().getFontsSources()[0];
        Assert.assertEquals(systemFontSource.getType(), FontSourceType.SYSTEM_FONTS);
        Assert.assertEquals(systemFontSource.getPriority(), 0);

        if (System.getProperty("os.name").startsWith("Windows")) {
            Assert.assertEquals(SystemFontSource.getSystemFontFolders(), new String[]{"C:\\WINDOWS\\Fonts"});
        }

        for (String systemFontFolder : SystemFontSource.getSystemFontFolders()) {
            System.out.println(systemFontFolder);
        }

        // Set a font that exists in the windows fonts directory as a substitute for one that doesn't
        doc.getFontSettings().getSubstitutionSettings().getFontInfoSubstitution().setEnabled(true);
        doc.getFontSettings().getSubstitutionSettings().getTableSubstitution().addSubstitutes("Kreon-Regular", new String[]{"Calibri"});

        long substituteSize = StreamSupport.stream(doc.getFontSettings().getSubstitutionSettings().getTableSubstitution().getSubstitutes("Kreon-Regular").spliterator(), false).count();
        Assert.assertEquals(substituteSize, 1);
        Assert.assertTrue(doc.getFontSettings().getSubstitutionSettings().getTableSubstitution().getSubstitutes("Kreon-Regular").toString().contains("Calibri"));

        // Alternatively, we could add a folder font source in which the corresponding folder contains the font
        FolderFontSource folderFontSource = new FolderFontSource(getMyDir() + "MyFonts", false);
        doc.getFontSettings().setFontsSources(new FontSourceBase[]{systemFontSource, folderFontSource});
        Assert.assertEquals(doc.getFontSettings().getFontsSources().length, 2);

        // Resetting the font sources still leaves us with the system font source as well as our substitutes
        doc.getFontSettings().resetFontSources();

        Assert.assertEquals(doc.getFontSettings().getFontsSources().length, 1);
        Assert.assertEquals(doc.getFontSettings().getFontsSources()[0].getType(), FontSourceType.SYSTEM_FONTS);

        substituteSize = StreamSupport.stream(doc.getFontSettings().getSubstitutionSettings().getTableSubstitution().getSubstitutes("Kreon-Regular").spliterator(), false).count();
        Assert.assertEquals(substituteSize, 1);
        //ExEnd
    }

    @Test
    public void loadFontFallbackSettingsFromFile() throws Exception {
        //ExStart
        //ExFor:FontFallbackSettings.Load(String)
        //ExFor:FontFallbackSettings.Save(String)
        //ExSummary:Shows how to load and save font fallback settings from file.
        Document doc = new Document(getMyDir() + "Rendering.doc");

        // By default fallback settings are initialized with predefined settings which mimics the Microsoft Word fallback.
        FontSettings fontSettings = new FontSettings();
        fontSettings.getFallbackSettings().load(getMyDir() + "Fallback.xml");

        doc.setFontSettings(fontSettings);
        doc.save(getArtifactsDir() + "LoadFontFallbackSettingsFromFile.pdf");

        // Saves font fallback setting by string
        doc.getFontSettings().getFallbackSettings().save(getArtifactsDir() + "FallbackSettings.xml");
        //ExEnd
    }

    @Test
    public void loadFontFallbackSettingsFromStream() throws Exception {
        //ExStart
        //ExFor:FontFallbackSettings.Load(Stream)
        //ExSummary:Shows how to load and save font fallback settings from stream.
        Document doc = new Document(getMyDir() + "Rendering.doc");

        // By default fallback settings are initialized with predefined settings which mimics the Microsoft Word fallback.
        InputStream fontFallbackStream = new FileInputStream(getMyDir() + "Fallback.xml");
        try {
            FontSettings fontSettings = new FontSettings();
            // Note: Saves font fallback setting by stream is not implemented now
            fontSettings.getFallbackSettings().load(fontFallbackStream);

            doc.setFontSettings(fontSettings);
        } finally {
            if (fontFallbackStream != null) {
                fontFallbackStream.close();
            }
        }

        doc.save(getArtifactsDir() + "LoadFontFallbackSettingsFromStream.pdf");
        //ExEnd
    }

    @Test
    public void loadNotoFontsFallbackSettings() throws Exception {
        //ExStart
        //ExFor:FontFallbackSettings.LoadNotoFallbackSettings
        //ExSummary:Shows how to add predefined font fallback settings for Google Noto fonts.
        FontSettings fontSettings = new FontSettings();
        // These are free fonts licensed under SIL OFL
        // They can be downloaded from https://www.google.com/get/noto/#sans-lgc
        fontSettings.setFontsFolder(getFontsDir() + "Noto", false);
        // Note that only Sans style Noto fonts with regular weight are used in the predefined settings
        // Some of the Noto fonts uses advanced typography features
        // Advanced typography is currently not supported by AW and these fonts may be rendered inaccurately
        fontSettings.getFallbackSettings().loadNotoFallbackSettings();
        fontSettings.getSubstitutionSettings().getFontInfoSubstitution().setEnabled(false);
        fontSettings.getSubstitutionSettings().getDefaultFontSubstitution().setDefaultFontName("Noto Sans");

        Document doc = new Document();
        doc.setFontSettings(fontSettings);
        //ExEnd
    }

    @Test
    public void defaultFontSubstitutionRule() throws Exception {
        //ExStart
        //ExFor:Fonts.DefaultFontSubstitutionRule
        //ExFor:Fonts.DefaultFontSubstitutionRule.DefaultFontName
        //ExFor:Fonts.FontSubstitutionSettings.DefaultFontSubstitution
        //ExSummary:Shows how to set the default font substitution rule.
        // Create a blank document and a new FontSettings property
        Document doc = new Document();
        FontSettings fontSettings = new FontSettings();
        doc.setFontSettings(fontSettings);

        // Get the default substitution rule within FontSettings, which will be enabled by default and will substitute all missing fonts with "Times New Roman"
        DefaultFontSubstitutionRule defaultRule = fontSettings.getSubstitutionSettings().getDefaultFontSubstitution();
        Assert.assertTrue(defaultRule.getEnabled());
        Assert.assertEquals(defaultRule.getDefaultFontName(), "Times New Roman");

        // Set the default font substitute to "Courier New"
        defaultRule.setDefaultFontName("Courier New");

        // Using a document builder, add some text in a font that we don't have to see the substitution take place,
        // and render the result in a PDF
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.getFont().setName("Missing Font");
        builder.writeln("Line written in a missing font, which will be substituted with Courier New.");

        doc.save(getArtifactsDir() + "Font.DefaultFontSubstitutionRule.pdf");
        //ExEnd
    }

    @Test
    public void fontConfigSubstitution() {
        //ExStart
        //ExFor:Fonts.FontConfigSubstitutionRule
        //ExFor:Fonts.FontConfigSubstitutionRule.Enabled
        //ExFor:Fonts.FontConfigSubstitutionRule.IsFontConfigAvailable
        //ExFor:Fonts.FontConfigSubstitutionRule.ResetCache
        //ExFor:Fonts.FontSubstitutionRule
        //ExFor:Fonts.FontSubstitutionRule.Enabled
        //ExFor:Fonts.FontSubstitutionSettings.FontConfigSubstitution
        //ExSummary:Shows OS-dependent font config substitution.
        // Create a new FontSettings object and get its font config substitution rule
        FontSettings fontSettings = new FontSettings();
        FontConfigSubstitutionRule configSubstitution = fontSettings.getSubstitutionSettings().getFontConfigSubstitution();

        // The FontConfigSubstitutionRule object works differently on Windows/non-Windows platforms
        final String OS = System.getProperty("os.name").toLowerCase();

        // On Windows, it is unavailable
        if (OS == "win") {
            Assert.assertFalse(configSubstitution.getEnabled());
            Assert.assertFalse(configSubstitution.isFontConfigAvailable());
        }

        // On Linux/Mac, we will have access and will be able to perform operations
        if (OS == "nix") {
            Assert.assertTrue(configSubstitution.getEnabled());
            Assert.assertTrue(configSubstitution.isFontConfigAvailable());

            configSubstitution.resetCache();
        }
        //ExEnd
    }

    @Test
    public void fallbackSettings() throws Exception {
        //ExStart
        //ExFor:Fonts.FontFallbackSettings.LoadMsOfficeFallbackSettings
        //ExFor:Fonts.FontFallbackSettings.LoadNotoFallbackSettings
        //ExSummary:Shows how to load pre-defined fallback font settings.
        Document doc = new Document();

        // Create a FontSettings object for our document and get its FallbackSettings attribute
        FontSettings fontSettings = new FontSettings();
        doc.setFontSettings(fontSettings);
        FontFallbackSettings fontFallbackSettings = fontSettings.getFallbackSettings();

        // Save the default fallback font scheme in an XML document
        // For example, one of the elements has a value of "0C00-0C7F" for Range and a corresponding "Vani" value for FallbackFonts
        // This means that if the font we are using does not have symbols for the 0x0C00-0x0C7F unicode block,
        // the symbols from the "Vani" font will be used as a substitute
        fontFallbackSettings.save(getArtifactsDir() + "Font.FallbackSettings.Default.xml");

        // There are two pre-defined font fallback schemes we can choose from
        // 1: Use the default Microsoft Office scheme, which is the same one as the default
        fontFallbackSettings.loadMsOfficeFallbackSettings();
        fontFallbackSettings.save(getArtifactsDir() + "Font.FallbackSettings.LoadMsOfficeFallbackSettings.xml");

        // 2: Use the scheme built from Google Noto fonts
        fontFallbackSettings.loadNotoFallbackSettings();
        fontFallbackSettings.save(getArtifactsDir() + "Font.FallbackSettings.LoadNotoFallbackSettings.xml");
        //ExEnd
    }

    @Test
    public void fallbackSettingsCustom() throws Exception {
        //ExStart
        //ExFor:Fonts.FontSettings.FallbackSettings
        //ExFor:Fonts.FontFallbackSettings
        //ExFor:Fonts.FontFallbackSettings.BuildAutomatic
        //ExSummary:Shows how to distribute fallback fonts across unicode character code ranges.
        Document doc = new Document();

        // Create a FontSettings object for our document and get its FallbackSettings attribute
        FontSettings fontSettings = new FontSettings();
        doc.setFontSettings(fontSettings);
        FontFallbackSettings fontFallbackSettings = fontSettings.getFallbackSettings();

        // Set our fonts to be sourced exclusively from the "MyFonts" folder
        FolderFontSource folderFontSource = new FolderFontSource(getMyDir() + "\\MyFonts", false);
        fontSettings.setFontsSources(new FontSourceBase[]{folderFontSource});

        // Calling BuildAutomatic() will generate a fallback scheme that distributes accessible fonts across as many unicode character codes as possible
        // In our case, it only has access to the handful of fonts inside the "MyFonts" folder
        fontFallbackSettings.buildAutomatic();
        fontFallbackSettings.save(getArtifactsDir() + "Font.FontFallbackSettings.BuildAutomatic.xml");

        // We can also load a custom substitution scheme from a file like this
        // This scheme applies the "Arvo" font across the "0000-00ff" unicode blocks, the "Squarish Sans CT" font across "0100-024f",
        // and the "M+ 2m" font in every place that none of the other fonts cover
        fontFallbackSettings.load(getMyDir() + "Font.FallbackSettings.Custom.xml");

        // Create a document builder and set its font to one that doesn't exist in any of our sources
        // In doing that we will rely completely on our font fallback scheme to render text
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.getFont().setName("Missing Font");

        // Type out every unicode character from 0x0021 to 0x052F, with descriptive lines dividing unicode blocks we defined in our custom font fallback scheme
        for (int i = 0x0021; i < 0x0530; i++) {
            switch (i) {
                case 0x0021:
                    builder.writeln("\n\n0x0021 - 0x00FF: \nBasic Latin/Latin-1 Supplement unicode blocks in \"Arvo\" font:");
                    break;
                case 0x0100:
                    builder.writeln("\n\n0x0100 - 0x024F: \nLatin Extended A/B blocks, mostly in \"Squarish Sans CT\" font:");
                    break;
                case 0x0250:
                    builder.writeln("\n\n0x0250 - 0x052F: \nIPA/Greek/Cyrillic blocks in \"M+ 2m\" font:");
                    break;
            }

            builder.write(Character.toString((char) i));
        }

        doc.save(getArtifactsDir() + "Font.FallbackSettings.Custom.pdf");
        //ExEnd
    }

    @Test
    public void tableSubstitutionRule() throws Exception {
        //ExStart
        //ExFor:Fonts.TableSubstitutionRule
        //ExFor:Fonts.TableSubstitutionRule.LoadLinuxSettings
        //ExFor:Fonts.TableSubstitutionRule.LoadWindowsSettings
        //ExFor:Fonts.TableSubstitutionRule.Save(System.String)
        //ExSummary:Shows how to access font substitution tables for Windows and Linux.
        // Create a blank document and a new FontSettings object
        Document doc = new Document();
        FontSettings fontSettings = new FontSettings();
        doc.setFontSettings(fontSettings);

        // Create a new table substitution rule and load the default Windows font substitution table
        TableSubstitutionRule substitutionRule = fontSettings.getSubstitutionSettings().getTableSubstitution();
        substitutionRule.loadWindowsSettings();

        // In Windows, the default substitute for the "Times New Roman CE" font is "Times New Roman"
        Assert.assertEquals(substitutionRule.getSubstitutes("Times New Roman CE"), Arrays.asList(new String[]{"Times New Roman"}));

        // We can save the table for viewing in the form of an XML document
        // Note: Saves table substitution rules by stream is not implemented now
        substitutionRule.save(getArtifactsDir() + "Font.TableSubstitutionRule.Windows.xml");

        // Linux has its own substitution table
        // If "FreeSerif" is unavailable to substitute for "Times New Roman CE", we then look for "Liberation Serif", and so on
        substitutionRule.loadLinuxSettings();
        Assert.assertEquals(substitutionRule.getSubstitutes("Times New Roman CE"), Arrays.asList(new String[]{"FreeSerif", "Liberation Serif", "DejaVu Serif"}));
        //ExEnd
    }

    @Test
    public void tableSubstitutionRuleCustom() throws Exception {
        //ExStart
        //ExFor:Fonts.FontSubstitutionSettings.TableSubstitution
        //ExFor:Fonts.TableSubstitutionRule.AddSubstitutes(System.String,System.String[])
        //ExFor:Fonts.TableSubstitutionRule.GetSubstitutes(System.String)
        //ExFor:Fonts.TableSubstitutionRule.Load(System.IO.Stream)
        //ExFor:Fonts.TableSubstitutionRule.Load(System.String)
        //ExFor:Fonts.TableSubstitutionRule.SetSubstitutes(System.String,System.String[])
        //ExSummary:Shows how to work with custom font substitution tables.
        // Create a blank document and a new FontSettings object
        Document doc = new Document();
        FontSettings fontSettings = new FontSettings();
        doc.setFontSettings(fontSettings);

        // Create a new table substitution rule and load the default Windows font substitution table
        TableSubstitutionRule tableSubstitutionRule = fontSettings.getSubstitutionSettings().getTableSubstitution();

        // If we select fonts exclusively from our own folder, we will need a custom substitution table
        FolderFontSource folderFontSource = new FolderFontSource(getMyDir() + "\\MyFonts", false);
        fontSettings.setFontsSources(new FontSourceBase[]{folderFontSource});

        // There are two ways of loading a substitution table from a file in the local file system
        // 1: Loading from a stream
        InputStream fileStream = new FileInputStream(getMyDir() + "Font.TableSubstitutionRule.Custom.xml");
        try {
            tableSubstitutionRule.load(fileStream);
        } finally {
            if (fileStream != null) fileStream.close();
        }

        // 2: Load directly from file
        tableSubstitutionRule.load(getMyDir() + "Font.TableSubstitutionRule.Custom.xml");

        // Since we no longer have access to "Arial", our font table will first try substitute it with "Nonexistent Font", which we don't have,
        // and then with "Kreon", found in the "MyFonts" folder
        Assert.assertEquals(tableSubstitutionRule.getSubstitutes("Arial"), Arrays.asList(new String[]{"Missing Font", "Kreon"}));

        // If we find this substitution table lacking, we can also expand it programmatically
        // In this case, we add an entry that substitutes "Times New Roman" with "Arvo"
        Assert.assertNull(tableSubstitutionRule.getSubstitutes("Times New Roman"));
        tableSubstitutionRule.addSubstitutes("Times New Roman", "Arvo");
        Assert.assertEquals(tableSubstitutionRule.getSubstitutes("Times New Roman"), Arrays.asList(new String[]{"Arvo"}));

        // We can add a secondary fallback substitute for an existing font entry with AddSubstitutes()
        // In case "Arvo" is unavailable, our table will look for "M+ 2m"
        tableSubstitutionRule.addSubstitutes("Times New Roman", "M+ 2m");
        Assert.assertEquals(tableSubstitutionRule.getSubstitutes("Times New Roman"), Arrays.asList(new String[]{"Arvo", "M+ 2m"}));

        // SetSubstitutes() can set a new list of substitute fonts for a font
        tableSubstitutionRule.setSubstitutes("Times New Roman", new String[]{"Squarish Sans CT", "M+ 2m"});
        Assert.assertEquals(tableSubstitutionRule.getSubstitutes("Times New Roman"), Arrays.asList(new String[]{"Squarish Sans CT", "M+ 2m"}));

        // TO demonstrate substitution, write text in fonts we have no access to and render the result in a PDF
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.getFont().setName("Arial");
        builder.writeln("Text written in Arial, to be substituted by Kreon.");

        builder.getFont().setName("Times New Roman");
        builder.writeln("Text written in Times New Roman, to be substituted by Squarish Sans CT.");

        doc.save(getArtifactsDir() + "Font.TableSubstitutionRule.Custom.pdf");
        //ExEnd
    }

    @Test
    public void resolveFontsBeforeLoadingDocument() throws Exception {
        //ExStart
        //ExFor:LoadOptions.FontSettings
        //ExSummary:Shows how to resolve fonts before loading HTML and SVG documents.
        FontSettings fontSettings = new FontSettings();
        TableSubstitutionRule substitutionRule = fontSettings.getSubstitutionSettings().getTableSubstitution();
        // If "HaettenSchweiler" is not installed on the local machine,
        // it is still considered available, because it is substituted with "Comic Sans MS"
        substitutionRule.addSubstitutes("HaettenSchweiler", new String[]{"Comic Sans MS"});

        LoadOptions loadOptions = new LoadOptions();
        loadOptions.setFontSettings(fontSettings);
        // The same for SVG document
        Document doc = new Document(getMyDir() + "Document.LoadFormat.html", loadOptions);
        //ExEnd
    }

    @Test
    public void getFontLeading() throws Exception {
        //ExStart
        //ExFor:Font.LineSpacing
        //ExSummary:Shows how to get line spacing of current font (in points)
        DocumentBuilder builder = new DocumentBuilder(new Document());
        builder.getFont().setName("Calibri");
        builder.writeln("qText");

        // Obtain line spacing.
        Font font = builder.getDocument().getFirstSection().getBody().getFirstParagraph().getRuns().get(0).getFont();
        System.out.println(MessageFormat.format("lineSpacing = {0}", font.getLineSpacing()));
        //ExEnd
    }

    @Test
    public void hasDmlEffect() throws Exception {
        //ExStart
        //ExFor:Font.HasDmlEffect(TextDmlEffect)
        //ExSummary:Shows how to checks if particular Dml text effect is applied.
        Document doc = new Document(getMyDir() + "Font.HasDmlEffect.docx");

        RunCollection runs = doc.getFirstSection().getBody().getFirstParagraph().getRuns();

        Assert.assertTrue(runs.get(0).getFont().hasDmlEffect(TextDmlEffect.SHADOW));
        Assert.assertTrue(runs.get(1).getFont().hasDmlEffect(TextDmlEffect.SHADOW));
        Assert.assertTrue(runs.get(2).getFont().hasDmlEffect(TextDmlEffect.REFLECTION));
        Assert.assertTrue(runs.get(3).getFont().hasDmlEffect(TextDmlEffect.EFFECT_3_D));
        Assert.assertTrue(runs.get(4).getFont().hasDmlEffect(TextDmlEffect.FILL));
        //ExEnd
    }
}
