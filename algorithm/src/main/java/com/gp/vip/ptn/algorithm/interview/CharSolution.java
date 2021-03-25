package com.gp.vip.ptn.algorithm.interview;

/**
 * @author: Fred
 * @date: 2020/8/31 9:27 下午
 * @description: (类的描述)
 */
public class CharSolution {


    public String changeString (String str) {

        StringBuilder result = new StringBuilder();

        if (str.length() == 1) {
            result.append(str.charAt(0));
            result.append('_');
            result.append('1');
            return result.toString();
        } else {

            result.append(str.charAt(0));
            result.append('_');
            int c = 1;

            for (int i = 0; i < str.length() - 1; i++) {
                if (str.charAt(i) == str.charAt(i + 1)) {
                    c++;
                } else {
                    result.append(Integer.toString(c).toCharArray());

                    result.append(str.charAt(i+1));
                    result.append('_');
                    c = 1;
                }
            }
            result.append(Integer.toString(c).toCharArray());
            return result.toString();
        }


        //return null;
    }

    public static void main(String[] args) {
        String s=new CharSolution().changeString("hbbbbblllqqqqqqqqqqqhfffffffflljjjjjjjbbbbbrrdocccdddddddddppppwwhhdcccjjjjjaazzaarrreevkkkkkkiiiiiiicccqggpkkkmmzmdduurrvvvhhhhhffffwwweiiiiiqqqwgbbbbnnnhhhllppprittffffffjjjmmmmmmmyygggoooofffllapxxxwwwwwwwppvmmmmmmmmmmnnnvnbbbbtrrrxxxssqqqbbbffffshhhhhhhhhhhhhuuuullqqqhhrrrrbbhhhhhhhhhhhewwwkkkjjjjjjvvvvveoowuuuuudwhhhhhhzzzvvvvvvvjjjjvsskbqqqqqqftttvvvrlssettteeeeeezwwutttkkkkkkkkklldddddddddddddssssggggyqqjqqffffwwwwwwwwwwwwwwwwwweeeoowwwdddcceejjoslllliixxbbbbzzggggpsaaaawwwwwuuuuwwwwwwwxxxxxxxxxggbbbbbbbbboooommmmmmmjjjvvvmmoooojgggusnnnnnvlliiiilllllssssssqqqqqqttooozzbxxxxxxxxxxxbbbbbbbbbeewwmmmraaffuuuuaaeelzzzzzzzzzzzippppprrrrggggmmmqqqqwvvoooooyyrrrrrrryyyyycysssswwwwwfffccyyrfetiiiiiiimmmmmstttnttttttttagggixzziiyyhhhhbbbbbbbbbbbbbbzkbbbzzwrrddddddgiiiirzzzommsssssssssshhhhhoopiiiiixxxsjttsskkkkkkkhhhhhiifffvvvvvllkddnfffffddddddddtttttttttttteeeeecccccrrrvttuullmnqmmmmmmppppyyyyyyyyyytttttkggggfzzoooooooooooppppppbttfffffftacccsyyyyyaaanzztiiiazfssppuuudddddddttttttttttttddddjjjjjjmmppxaa");
        System.out.println(s);
    }

}
