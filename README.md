# Annotation Tool for NLP

The radiologists of MRON in collaboration with ZGT (ZiekenhuisGroep Twente / Hospital Group Twente) are doing research on innovations in radiology using AI. For a large part, this research focuses on Natural Language Processing technology, because whenever radiologists evaluate an X-Ray/CT/MRI/etc, they write a textual report. Although they call it a "structured report", it is plain text, unstructured data from a computer science perspective. The "structured" refers to the fact that it complies with the international BI-RADS standard. Their goal is to use Natural Language Processing in order to extract actual structured data (i.e. in XML or json format) from a “structured” natural language report.

However, one of the main problems in Natural Language processing is how to obtain properly annotated data to train the classifiers. This usually happens manually; a very monotonous and intensive work.

The goal of this project is to design and develop an annotation tool that will facilitate this process by making the manual annotation as efficient as possible. This means that the goal is to build a user friendly application that runs smoothly and can be integrated to the hospital work-flow. Moreover, we integrate a Natural Language Processing classifier that is trained to automatically annotate radiology reports with the BIRADS standard. These automatically annotated reports can be checked and accepted or rejected by a human annotator in a later phase. This will make the annotation work semi-automatic.

Although the main target group is radiologists writing reports in the BIRADS standard, we allow for different annotation systems to be defined so that the application usage can be extended to other departments of the hospital.
