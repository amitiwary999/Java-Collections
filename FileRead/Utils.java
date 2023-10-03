package FileRead;

public class Utils {
    static WordAndCountModel readLineWordCount(String line){
        StringBuilder tempStr = new StringBuilder(line);
        String[] strSplits = tempStr.toString().split("\n");
        int count = 0;
        int charVal = 0;
        for(String strSplit : strSplits){
            String tempSplitStr = strSplit;
            char[] charArr = tempSplitStr.toCharArray();
            for(char charItem : charArr){
                int charItemIntVal = charItem;
                charVal += charItemIntVal;
            }
            int wordLength = strSplit.length();
            count += wordLength;

        }
        return new WordAndCountModel(charVal, tempStr.toString());
    }
}
