package Examples;

//////////////////////////////////////////////////////////////////////////
// Copyright (c) 2001-2019 Aspose Pty Ltd. All Rights Reserved.
//
// This file is part of Aspose.Words. The source code in this file
// is only intended as a supplement to the documentation, and is provided
// "as is", without warranty of any kind, either expressed or implied.
//////////////////////////////////////////////////////////////////////////

import com.aspose.words.HeaderFooter;

/**
 * Examples for the .NET vs Java Differences in Aspose.Words in the Programmers Guide.
 */
public class ExDotNetVsJava {
    //ExStart
    //ExId:SaveSignature
    //ExSummary:Shows difference in .NET and Java in signatures of a method with an enum parameter.
    // The saveFormat parameter is a SaveFormat enum value.
    void save(final String fileName, final int saveFormat)
    //ExEnd
    {
        // Do nothing.
    }

    //ExStart
    //ExId:CollectionItemSignature
    //ExSummary:Shows difference in signatures of collection indexers in .NET vs Java.
    public class HeaderFooterCollection {
        // Get by index is an indexer.
        public HeaderFooter get(final int index)                  //ExSkip
        {                   //ExSkip
            return null;    //ExSkip
        }                       //ExSkip

        // Get by header footer type is an overloaded indexer.
        public HeaderFooter getByHeaderFooterType(final int headerFooterType)                  //ExSkip
        {                   //ExSkip
            return null;    //ExSkip
        }                       //ExSkip
    }
    //ExEnd
}

