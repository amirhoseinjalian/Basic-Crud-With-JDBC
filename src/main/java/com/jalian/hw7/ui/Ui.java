package com.jalian.hw7.ui;

import com.jalian.hw7.dao.entities.*;
import com.jalian.hw7.service.Service;

import java.sql.Date;
import java.util.HashSet;
import java.util.Scanner;

public class Ui {
    private static Service service = Service.getInstance();
    private static Scanner scanner = new Scanner(System.in);
    private static Accont accont;
    private static NormalAuthor normalAuthor;
    private static Tag tag;
    private static Category category;
    private static Author author;
    private static Article article;
    private static String username;
    private static String password;
    private static String name;
    private static String nationalCode;
    private static Date birthDate;
    private static String option;

    public static void main(String[] args) {
        //this comment added by github
        System.out.println("1)log in\n2)sign in");
        option = scanner.nextLine();
        switch (option) {
            case "1":
                //log in***************************************************************************************
                System.out.println("insert your username password:\nusername:");
                username = scanner.nextLine();
                System.out.println("password:");
                password = scanner.nextLine();
                //baraye khataye marbut be khat 45 bayad inja az bache new mikardam?????????????????????????????????????????????
                author = new Author(username, password);
                author = service.authenticate(author);
                if (author == null) {
                    //invalid username or password***************************************************************************
                    System.out.println("invalid username or password!!!!!!!!!");
                } else {
                    if (author.getType().equals("admin")) {
                        //admin log in***********************************************************************************
                        adminMenu();
                    } else if (author.getType().equals("normal")) {
                        //normal user log in********************************************************************************
                        //inja behem class cast exception dad??????????????????????????????????????????????????????????????
                        normalAuthor = (NormalAuthor) author;
                        normalUserMenu(normalAuthor);
                    }
                }
                break;
            case "2":
                //sign in**************************************************************************************
                System.out.println("name: ");
                name = scanner.nextLine();
                System.out.println("national code: ");
                nationalCode = scanner.nextLine();
                System.out.println("username: ");
                username = scanner.nextLine();
                System.out.println("password: ");
                password = scanner.nextLine();
                System.out.println("birth date: (this format: year-day-month)");
                String s = scanner.nextLine();
                birthDate = new Date(Integer.parseInt(s.split("-")[0]), Integer.parseInt(s.split("-")[1]), Integer.parseInt(s.split("-")[2]));
                author = new Author(name, nationalCode, password, username, birthDate);
                author.setState(State.registered);
                author.setType("normal");
                int id = service.addAuthor(author);
                if (id != 0) {
                    System.out.println("done!! your id: " + id);
                    author.setId(id);
                } else {
                    System.out.println("you inseted illegal data, maybe your username is duplicate or you didn't comply the format for your bith date");
                }
                break;
            default:
                //invalid data***************************************************************************************
                System.out.println("invalid data!!!!!!!!");
                service.exit();
                break;
        }
        service.exit();
    }

    public static void adminMenu() {
        //admin menu*************************************************************************************************
        System.out.println("hi dear admin!!!\n1)confirm waiting users\n2)block an user\n3)exit");
        option = scanner.nextLine();
        if (option.equals("1")) {
            //confirm user********************************************************************************************
            changeState(State.confirmed);
        } else if (option.equals("2")) {
            //block user***********************************************************************************************
            changeState(State.blocked);
        } else if (option.equals("3")) {
            //exit admin***********************************************************************************************
            service.exit();
        } else {
            System.out.println("invalid data!!!!!!!!!!!");
            service.exit();
        }
    }

    public static void normalUserMenu(NormalAuthor normalAuthor) {
        //normal user menu********************************************************************************************
        if (author.getState().name().equals("blocked")) {
            System.out.println("you are blocked by the admin!!!!!!!!");
        } else if (author.getState().name().equals("registered")) {
            System.out.println("you must wait until the admin confirms you, soryy!!!!");
        } else if (author.getState().name().equals("active")) {
            System.out.println("1)see your articles\n2)add an article\n3)edit an article\n4)charge your account\n5)exit");
            option = scanner.nextLine();
            if (option.equals("1")) {
                //see articles****************************************************************************************
                System.out.println("1)all articles\n2)free articles\n3)premium articles");
                option = scanner.nextLine();
                if (option.equals("1")) {
                    //see all articles*******************************************************************************
                    String allArticles = service.seeArticlesByAnAuthor(normalAuthor, "all");
                    if (allArticles == null) {
                        System.out.println("you don't have any article");
                    } else {
                        System.out.println(allArticles);
                    }
                } else if (option.equals("2")) {
                    //see free articles***************************************************************************************
                    String freeArticles = service.seeArticlesByAnAuthor(normalAuthor, "free");
                    if (freeArticles == null) {
                        System.out.println("you don't have any free article");
                    } else {
                        System.out.println(freeArticles);
                    }
                } else if (option.equals("3")) {
                    //see premium articles************************************************************************************
                    String premiumArticles = service.seeArticlesByAnAuthor(normalAuthor, "premium");
                    if (premiumArticles == null) {
                        System.out.println("you don't have any premium article");
                    } else {
                        System.out.println(premiumArticles);
                    }
                } else {
                    System.out.println("invalid data!!!!!!!!!!");
                    service.exit();
                }
            } else if (option.equals("2")) {
                //add an article**********************************************************************************************
                HashSet<Tag> newTags = new HashSet<>();
                HashSet<Tag> oldTags = new HashSet<>();
                int categoryId = 0;
                int tagId = 0;
                String content = "";
                String brief = "";
                String articleTitle = "";
                System.out.println("content:");
                content = scanner.nextLine();
                System.out.println("brief:");
                brief = scanner.nextLine();
                System.out.println("title:");
                articleTitle = scanner.nextLine();
                System.out.println("This is the list of categories, if your category is in our list, insert its id if no, insert *:");
                System.out.println(service.getAllCategories());
                option = scanner.nextLine();
                if (option.equals("*")) {
                    //ADD A CATEGORY ***************************************************************************
                    System.out.println("insert title: ");
                    String title = scanner.nextLine();
                    System.out.println("insert description: ");
                    String description = scanner.nextLine();
                    category = new Category(description, title);
                    categoryId = service.addACategory(category);
                    if (categoryId != 0) {
                        System.out.println("done" + "category id: " + categoryId);
                        category.setId(categoryId);
                    } else {
                        System.out.println("illegal input!!!!");
                        return;
                    }
                } else {
                    //SELECTED CATEGORY ************************************************************************
                    categoryId = Integer.parseInt(option);
                    category = new Category(categoryId);
                    category = service.getCategory(category);
                    if (category == null) {
                        System.out.println("illegal id!!!");
                    } else {
                        System.out.println("done");
                    }
                }
                System.out.println("This is the list of tags, if your tags are in our list, insert * if want just customizes" +
                        "tags, insert *** if you want both of them insert **:");
                System.out.println(service.getAllTags());
                option = scanner.nextLine();
                if (!option.equals("*") & !option.equals("**") & !option.equals("***")) {
                    System.out.println("illegal input!!!!");
                }
                if (option.contains("**") || option.contains("***")) {
                    System.out.println("insert your titles by this format title1,title2,...");
                    String newTag = scanner.nextLine();
                    for (String title : newTag.split(",")) {
                        tag = new Tag(title);
                        tagId = service.addATag(tag);
                        if (tagId == 0) {
                            System.out.println("illegal title");
                            continue;
                        }
                        tag.setId(tagId);
                        newTags.add(tag);
                    }
                }
                if (option.equals("**") || option.equals("*")) {
                    System.out.println("insert tag ids by this format id1,id2,id3,...");
                    String oldTag = scanner.nextLine();
                    for (String id : oldTag.split(",")) {
                        tag = new Tag(Integer.parseInt(id));
                        tag = service.getTag(tag);
                        if (tag == null) {
                            System.out.println("illegal id!!!");
                            continue;
                        }
                        oldTags.add(tag);
                    }
                }
                System.out.println("1)free\n2)premium");
                String articleType = scanner.nextLine();
                if (!articleType.equals("1") & !articleType.equals("2")) {
                    System.out.println("illegal input!!!!");
                    service.exit();
                    return;
                }
                article = new Article(content, brief, articleTitle, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
                article.setUserUsername(username);
                article.setUserId(author.getId());
                article.setCategories(category);
                HashSet<Tag> tags = new HashSet<>();
                tags.addAll(oldTags);
                tags.addAll(newTags);
                article.setTags(tags);
                if (articleType.equals("1")) {
                    article.setType("free");
                    article.setPrice(0);
                } else {
                    article.setType("premium");
                    System.out.println("price: ");
                    String price = scanner.nextLine();
                    System.out.println("insert your account id to pay the price:");
                    String accountId = scanner.nextLine();
                    accont = new Accont(Integer.parseInt(accountId));
                    accont = service.getAccount(accont);
                    if (accont == null) {
                        System.out.println("invalid id");
                        return;
                    }
                    if (!service.changeAccountAmount(accont, Double.parseDouble(price))) {
                        System.out.println("you  don't have enough money!!");
                        return;
                    }
                    article.setPrice(Double.parseDouble(price));
                }
                int artcleId = service.addAnArticle(article);
                if (artcleId == 0) {
                    System.out.println("illegal input");
                } else {
                    System.out.println("done your article id: " + artcleId);
                }
            } else if (option.equals("3")) {
                //edit an article****************************************************************************************
                System.out.println("insert your article id:");
                String articleId = scanner.nextLine();
                article = service.getArticle(Integer.parseInt(articleId));
                if (article == null) {
                    System.out.println("illegal input!!!");
                    return;
                }
                System.out.println("insert your new content");
                String content = scanner.nextLine();
                System.out.println("insert your new brief");
                String brief = scanner.nextLine();
                System.out.println("insert your new title");
                String title = scanner.nextLine();
                article.setContent(content);
                article.setBrief(brief);
                article.setTitle(title);
                //age mi khastim tag ha va category ro ham taghir bedim ke kheili pichide mishod?????????????????????????????????????????????????????????????????
                System.out.println("do you want to publish it?");
                String isPublished = scanner.nextLine();
                if (!isPublished.equals("no") & !isPublished.equals("yes")) {
                    System.out.println("ilegall input!!!");
                    return;
                }
                article.setIsPublished(isPublished);
                if (service.updateArticle(article)) {
                    System.out.println("done");
                } else {
                    System.out.println("ops maybe one of your datas was ilegall");
                    return;
                }
            } else if (option.equals("4")) {
                //charge acoount********************************************************************************************
                System.out.println("insert your acount id:");
                String accountId = scanner.nextLine();
                accont = new Accont(Integer.parseInt(accountId));
                accont = service.getAccount(accont);
                if (accont == null) {
                    System.out.println("invalid id");
                    service.exit();
                    return;
                }
                System.out.println("your amount:");
                String accountAmount = scanner.nextLine();
                if (service.chargeAccount(accont, Double.parseDouble(accountAmount))) {
                    System.out.println("done");
                } else {
                    System.out.println("invalid input");
                    service.exit();
                    return;
                }
            } else if (option.equals("5")) {
                //exit****************************************************************************************************
                service.exit();
            } else {
                System.out.println("invalid data!!!!!");
                service.exit();
            }
        } else if (author.getState().name().equals("confirmed")) {
            System.out.println("you must create your own account");
            //create account*************************************************************************************
            System.out.println("1)create acount\n2)exit");
            option = scanner.nextLine();
            if (option.equals("1")) {
                System.out.println("insert your amount:");
                String amount = scanner.nextLine();
                accont = new Accont(Double.parseDouble(amount), normalAuthor.getUsername());
                //add account*********************************************************************************************
                int id = service.addAccount(accont);
                if (id == 0) {
                    System.out.println("invalid data");
                } else {
                    accont.setId(id);
                    System.out.println("done" + " your account id: " + id);
                    normalAuthor.setState(State.active);
                    service.updateAuthorState(normalAuthor);
                }
            } else if (option.equals("2")) {
                //exit***************************************************************************************************
                service.exit();
            } else {
                System.out.println("invalid data!!!");
                service.exit();
            }
        }
    }

    public static void changeState(State state) {
        System.out.println("insert author username:");
        String authorUsername = scanner.nextLine();
        NormalAuthor normalAuthor1 = new NormalAuthor(authorUsername);
        normalAuthor1 = (NormalAuthor) service.getAuthor(normalAuthor1);
        if (normalAuthor1 == null) {
            System.out.println("illegal id");
            service.exit();
            return;
        }
        normalAuthor1.setState(state);
        service.updateAuthorState(normalAuthor1);
    }
}
