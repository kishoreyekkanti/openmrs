// $ANTLR 2.7.6 (2005-12-22): "ArdenRecognizer.g" -> "ArdenBaseParser.java"$

package org.openmrs.arden;

import java.io.*;
//import org.Syntax.Parser.ArdenToken;
import antlr.CommonAST;
import antlr.collections.AST;
import antlr.*;
import org.openmrs.arden.parser.*;
import org.openmrs.arden.MLMObject;
import org.openmrs.arden.MLMObjectElement;
import java.lang.Integer;

public interface ArdenBaseTreeParserTokenTypes {
	int EOF = 1;
	int NULL_TREE_LOOKAHEAD = 3;
	int AND = 4;
	int WEIRD_IDENT = 5;
	int IS = 6;
	int ARE = 7;
	int WAS = 8;
	int WERE = 9;
	int COUNT = 10;
	int IN = 11;
	int LESS = 12;
	int THE = 13;
	int THAN = 14;
	int FROM = 15;
	int BEFORE = 16;
	int AFTER = 17;
	int AGO = 18;
	int WRITE = 19;
	int AT = 20;
	int LET = 21;
	int NOW = 22;
	int BE = 23;
	int YEAR = 24;
	int YEARS = 25;
	int IF = 26;
	int IT = 27;
	int THEY = 28;
	int NOT = 29;
	int OR = 30;
	int THEN = 31;
	int READ = 32;
	int MINIMUM = 33;
	int MIN = 34;
	int MAXIMUM = 35;
	int MAX = 36;
	int LAST = 37;
	int FIRST = 38;
	int EARLIEST = 39;
	int LATEST = 40;
	int EVENT = 41;
	int WHERE = 42;
	int EXIST = 43;
	int EXISTS = 44;
	int PAST = 45;
	int DAYS = 46;
	int DAY = 47;
	int MONTH = 48;
	int MONTHS = 49;
	int WEEK = 50;
	int WEEKS = 51;
	int AVG = 52;
	int AVERAGE = 53;
	int SUM = 54;
	int MEDIAN = 55;
	int CONCLUDE = 56;
	int ELSE = 57;
	int ELSEIF = 58;
	int ENDIF = 59;
	int TRUE = 60;
	int FALSE = 61;
	int DATA = 62;
	int LOGIC = 63;
	int ACTION = 64;
	int LITERAL_end = 65;
	int COLON = 66;
	int LITERAL_maintenance = 67;
	int LITERAL_library = 68;
	int LITERAL_knowledge = 69;
	int LITERAL_title = 70;
	int ENDBLOCK = 71;
	int LITERAL_mlmname = 72;
	int LITERAL_filename = 73;
	int DOT = 74;
	int MINUS = 75;
	int UNDERSCORE = 76;
	int LITERAL_arden = 77;
	// "ASTM-E" = 78
	int INTLIT = 79;
	int LITERAL_version = 80;
	int DIGIT = 81;
	int LITERAL_institution = 82;
	int LITERAL_author = 83;
	int SEMI = 84;
	int LITERAL_specialist = 85;
	int LITERAL_date = 86;
	int LITERAL_validation = 87;
	int LITERAL_production = 88;
	int LITERAL_research = 89;
	int LITERAL_testing = 90;
	int LITERAL_expired = 91;
	int ID = 92;
	int LPAREN = 93;
	int RPAREN = 94;
	// ":" = 95
	int LITERAL_T = 96;
	int LITERAL_t = 97;
	// "." = 98
	// "+" = 99
	// "-" = 100
	int LITERAL_Z = 101;
	int LITERAL_z = 102;
	int LITERAL_purpose = 103;
	int LITERAL_explanation = 104;
	int LITERAL_keywords = 105;
	int LITERAL_citations = 106;
	int LITERAL_SUPPORT = 107;
	int LITERAL_REFUTE = 108;
	int LITERAL_links = 109;
	int SINGLE_QUOTE = 110;
	int LITERAL_type = 111;
	// "data-driven" = 112
	int LITERAL_data_driven = 113;
	int COMMENT = 114;
	int ML_COMMENT = 115;
	int BECOMES = 116;
	int COMMA = 117;
	int LITERAL_EVENT = 118;
	int LITERAL_Event = 119;
	int ARDEN_CURLY_BRACKETS = 120;
	int LITERAL_PRESENT = 121;
	int LITERAL_NULL = 122;
	int LITERAL_BOOLEAN = 123;
	int LITERAL_NUMBER = 124;
	int LITERAL_TIME = 125;
	int LITERAL_DURATION = 126;
	int LITERAL_STRING = 127;
	int LITERAL_LIST = 128;
	int LITERAL_OBJECT = 129;
	int LITERAL_GREATER = 130;
	int LITERAL_EQUAL = 131;
	int LITERAL_hour = 132;
	int LITERAL_hours = 133;
	int LITERAL_minute = 134;
	int LITERAL_minutes = 135;
	int LITERAL_second = 136;
	int LITERAL_seconds = 137;
	int LITERAL_within = 138;
	int LITERAL_OCCUR = 139;
	int LITERAL_Occur = 140;
	int LITERAL_occur = 141;
	int LITERAL_OCCURS = 142;
	int LITERAL_Occurs = 143;
	int LITERAL_occurs = 144;
	int LITERAL_OCCURRED = 145;
	int LITERAL_Occurred = 146;
	int LITERAL_priority = 147;
	int LITERAL_evoke = 148;
	int LITERAL_CALL = 149;
	int LITERAL_Any = 150;
	int EQUALS = 151;
	int LITERAL_EQ = 152;
	int LT = 153;
	int LITERAL_LT = 154;
	int GT = 155;
	int LITERAL_GT = 156;
	int LTE = 157;
	int LITERAL_LE = 158;
	int GTE = 159;
	int LITERAL_GE = 160;
	int NE = 161;
	int LITERAL_NE = 162;
	int ACTION_OP = 163;
	int LITERAL_urgency = 164;
	int LITERAL_MERGE = 165;
	int LITERAL_SORT = 166;
	int LITERAL_DATA = 167;
	int LITERAL_SEQTO = 168;
	// "||" = 169
	// "*" = 170
	// "/" = 171
	int STRING_LITERAL = 172;
}
