package ngrams;

import edu.princeton.cs.algs4.In;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * 提供对 Google NGrams 数据集（或其子集）进行查询的实用方法的对象。
 * NGramMap 存储来自“单词文件”和“计数文件”的相关数据。
 * 严格意义上来说，它不是地图，但它确实提供了额外的功能。
 *
 * @author Josh Hug
 */
public class NGramMap {

    // TODO: Add any necessary static/instance variables.
    //the Integer key is the year
    //the value - WordsCountMap is a TreeMap<String, Integer>
    // holds records of all words and their counts:
    // The String value stands for the words itself
    // The Integer value stands for its count - the number of times it was mentioned

    // wordsInYears -> 复合HashMap类型，key值是年份，value是TreeMap<String, Double>
    // wordCountMap -> key是单词，Integer是该年该单词出现的次数
    // wordsInYears 记录了文件words file的数据
    HashMap<Integer,wordCountMap> wordsInYears;

    // counts 记录的文件count file的数据
    // 本质上是TreeMap<Integer, Double> Integer是年份，Double是该年所有单词出现的总频率
    TimeSeries counts;
    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        // TODO: Fill in this constructor. See the "NGramMap Tips" section of the spec for help.
        wordsInYears = new HashMap<>();
        counts = new TimeSeries();
        setWordsInYearsFromFile(wordsFilename);
        setCountFromFile(countsFilename);
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     *
     * 提供 STARTYEAR 和 ENDYEAR 之间（包括两端）WORD 的历史记录。
     * 返回的 TimeSeries 应该是副本，而不是指向此 NGramMap 的 TimeSeries 的链接。
     * 换句话说，对此函数返回的对象所做的更改不应影响 NGramMap。这也称为“防御性复制”。
     * 如果该词不在数据文件中，则返回一个空的 TimeSeries。
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries hist = new TimeSeries();
        for(int i = startYear; i <= endYear; i++) {
            if(wordsInYears.containsKey(i)) {
                wordCountMap wc = wordsInYears.get(i);
                if(wc.containsKey(word)) {
                    double count = wc.get(word);
                    hist.put(i, count);
                }
            }
        }
        return hist;
    }

    // 返回word在start_year到end_year出现的总次数
    public Double countToTalBetweenSAndE(String word, int startYear, int endYear) {
        TimeSeries hist = countHistory(word, startYear, endYear);
        Double sum = 0.0;
        for(Double count : hist.data()){
            sum += count;
        }
        return sum;
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     * 提供 WORD 的历史记录。
     * 返回的 TimeSeries 应该是副本，而不是指向此 NGramMap 的 TimeSeries 的链接。
     * 换句话说，对此函数返回的对象所做的更改不应影响 NGramMap。
     * 这也称为“防御性副本”。如果该词不在数据文件中，则返回一个空的 TimeSeries。
     */
    public TimeSeries countHistory(String word) {
        // TODO: Fill in this method.
        TimeSeries hist = new TimeSeries();
        for (Map.Entry<Integer, wordCountMap> entry : wordsInYears.entrySet()) {
            wordCountMap wc = entry.getValue();
            if(wc.containsKey(word)) {
                double count = wc.get(word);
                hist.put(entry.getKey(), count);
            }
        }
        return hist;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     * 返回所有卷中每年记录的总字数的防御性副本。
     */
    public TimeSeries totalCountHistory() {
        // TODO: Fill in this method.
        TimeSeries hist = new TimeSeries();
	    hist.putAll(counts);
        return hist;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     * 提供一个时间序列，其中包含 STARTYEAR 和 ENDYEAR 之间（包括两端）WORD 每年的相对频率。
     * 如果数据文件中没有该词，则返回一个空的时间序列。
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries weight = new TimeSeries();
        double count = 0;
        for(Map.Entry<Integer, wordCountMap> entry : wordsInYears.entrySet()) {
            if(entry.getKey() >= startYear && entry.getKey() <= endYear) {
                wordCountMap wc = entry.getValue();
                if(wc.containsKey(word)) {
                    count = wc.get(word) / counts.get(entry.getKey());
                    weight.put(entry.getKey(), count);
                }
            }
        }
        return weight;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     * 提供一个时间序列，其中包含单词每年相对于该年记录的所有单词的相对频率。
     * 如果单词不在数据文件中，则返回一个空的时间序列。
     */
    public TimeSeries weightHistory(String word) {
        // TODO: Fill in this method.
        TimeSeries weight = new TimeSeries();
        double count = 0;
        for(Map.Entry<Integer, wordCountMap> entry : wordsInYears.entrySet()) {
            wordCountMap wc = entry.getValue();
            if(wc.containsKey(word)) {
                count = wc.get(word) / counts.get(entry.getKey());
                weight.put(entry.getKey(), count);
            }

        }
        return weight;
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     *
     * 提供 STARTYEAR 至 ENDYEAR 之间（包括两端）WORDS 中所有单词每年的相对频率总和。
     * 如果某个单词在此时间范围内不存在，则忽略它而不是抛出异常。
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries summedWeight = new TimeSeries();
        Integer year;
        wordCountMap wordCountMap;
        double sum = 0;
        for(Map.Entry<Integer, wordCountMap> entry : wordsInYears.entrySet()) {
            year = entry.getKey();
            if(year >= startYear && year <= endYear) {
                wordCountMap = entry.getValue();
                for(String word : words) {
                    if(wordCountMap.containsKey(word)) {
                        sum += wordCountMap.get(word);
                    }
                }
            }

            if(sum > 0){
                summedWeight.put(year, sum / counts.get(year));
            }
        }
        return summedWeight;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        // TODO: Fill in this method.
        TimeSeries summedWeight = new TimeSeries();
        Integer year;
        wordCountMap wordCountMap;
        double sum = 0;
        for(Map.Entry<Integer, wordCountMap> entry : wordsInYears.entrySet()) {
            year = entry.getKey();
            wordCountMap = entry.getValue();
            for(String word : words) {
                if(wordCountMap.containsKey(word)) {
                    sum += wordCountMap.get(word);
                }
            }
            if(sum > 0){
                summedWeight.put(year, sum / counts.get(year));
            }
        }
        return summedWeight;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.

    //helper method
    //从文件中读取数据，构造wordsInYear
    private void setWordsInYearsFromFile(String filename) {
        In in = new In(filename);
        int year;
        String word;
        double count;
        wordCountMap w;
        while(in.hasNextLine()) {
            //输入数据格式：airport     2007    175702  32788
            //只读前三列，忽略第四列
            word = in.readString();
            year = in.readInt();
            count = in.readDouble();

            //如果不包含year，说明该年第一次读到，创建一个新的wordCountMap放入wordsInYear
            if(!wordsInYears.containsKey(year)) {
                wordsInYears.put(year, new wordCountMap());
            }

            //w获取该年的wordCountMap
            w = wordsInYears.get(year);

            //将word，count放入
            w.put(word, count);

            //读取下一行
            in.readLine();
        }
    }

    //helper method
    //从文件中读取数据，构造count
    private void setCountFromFile(String filename) {
        In in = new In(filename);
        //1470,984,10,1
        //只读第一列 第二列
        int year;
        String line;
        double count;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            while ((line = br.readLine())!= null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    year = Integer.parseInt(parts[0]);
                    count = Double.parseDouble(parts[1]);
                    counts.put(year, count);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
