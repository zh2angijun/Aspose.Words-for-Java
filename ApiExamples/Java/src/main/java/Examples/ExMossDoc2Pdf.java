package Examples;

//////////////////////////////////////////////////////////////////////////
// Copyright (c) 2001-2019 Aspose Pty Ltd. All Rights Reserved.
//
// This file is part of Aspose.Words. The source code in this file
// is only intended as a supplement to the documentation, and is provided
// "as is", without warranty of any kind, either expressed or implied.
//////////////////////////////////////////////////////////////////////////

//ExStart
//ExId:MossDoc2Pdf
//ExSummary:The following is the complete code of the document converter.

import com.aspose.words.Document;
import com.aspose.words.PdfSaveOptions;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;

/**
 * DOC2PDF document converter for SharePoint.
 * Uses Aspose.Words to perform the conversion.
 */
public class ExMossDoc2Pdf {
    /**
     * The main entry point for the application.
     */
    public static void main(final String[] args) throws Exception {
        // Although SharePoint passes "-log <filename>" to us and we are
        // supposed to log there, for the sake of simplicity, we will use 
        // our own hard coded path to the log file.
        // 
        // Make sure there are permissions to write into this folder.
        // The document converter will be called under the document 
        // conversion account (not sure what name), so for testing purposes 
        // I would give the Users group write permissions into this folder.
        OutputStream os = new FileOutputStream("C:\\Aspose2Pdf\\log.txt", true);
        gLog = new OutputStreamWriter(os, "UTF-8");

        try {
            gLog.write(new Date().toString() + " Started");
            gLog.write(System.getProperty("sun.java.command"));

            parseCommandLine(args);

            // Uncomment the code below when you have purchased a licenses for Aspose.Words.
            //
            // You need to deploy the license in the same folder as your 
            // executable, alternatively you can add the license file as an 
            // embedded resource to your project.
            //
            // // Set license for Aspose.Words.
            // Aspose.Words.License wordsLicense = new Aspose.Words.License();
            // wordsLicense.SetLicense("Aspose.Total.lic");

            convertDoc2Pdf(gInFileName, gOutFileName);
        } catch (Exception e) {
            gLog.write(e.getMessage());
            gLog.close();
            os.close();
            System.exit(100);
        } finally {
            gLog.close();
            os.close();
        }
    }

    private static void parseCommandLine(final String[] args) throws Exception {
        int i = 0;
        while (i < args.length) {
            String s = args[i];
            switch (s.toLowerCase()) {
                case "-in":
                    i++;
                    gInFileName = args[i];
                    break;
                case "-out":
                    i++;
                    gOutFileName = args[i];
                    break;
                case "-config":
                    // Skip the name of the config file and do nothing.
                    i++;
                    break;
                case "-log":
                    // Skip the name of the log file and do nothing.
                    i++;
                    break;
                default:
                    throw new Exception("Unknown command line argument: " + s);
            }
            i++;
        }
    }

    private static void convertDoc2Pdf(final String inFileName, final String outFileName) throws Exception {
        // You can load not only DOC here, but any format supported by
        // Aspose.Words: DOC, DOCX, RTF, WordML, HTML, MHTML, ODT etc.
        Document doc = new Document(inFileName);

        doc.save(outFileName, new PdfSaveOptions());
    }

    private static String gInFileName;
    private static String gOutFileName;
    private static Writer gLog;

}

//ExEnd