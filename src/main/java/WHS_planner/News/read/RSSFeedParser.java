package WHS_planner.News.read;

import WHS_planner.News.model.Feed;
import WHS_planner.News.model.FeedMessage;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class RSSFeedParser {
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String LANGUAGE = "language";
    private static final String COPYRIGHT = "copyright";
    private static final String LINK = "link";
    private static final String AUTHOR = "creator";
    private static final String ITEM = "item";
    private static final String PUB_DATE = "pubDate";
    private static final String GUID = "guid";

    private final URL url;

    public RSSFeedParser(String feedUrl) {
        try {
            this.url = new URL(feedUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Returns a Feed object with the information of a RSS
     * Feed at the URL that was initialized with this class.
     * @return The Feed at the previously set URL
     */
    public Feed readFeed() {
        Feed feed = null;
        try {
            boolean isFeedHeader = true;
            // Set header values initial to the empty string
            String description = "";
            String title = "";
            String link = "";
            String language = "";
            String copyright = "";
            String author = "";
            String pubdate = "";
            String guid = "";

            // First create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            inputFactory.setProperty("javax.xml.stream.isCoalescing", true);

            // Setup a new eventReader
            InputStream in = read();
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            // read the XML document
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement()) {
                    String localPart = event.asStartElement().getName()
                            .getLocalPart();
                    if (localPart.equals(ITEM)) {
                        if (isFeedHeader) {
                            isFeedHeader = false;
                            feed = new Feed(title, link, description, language,
                                    copyright, pubdate);
                        }
                    } else if (localPart.equals(TITLE)) {
                        title = getCharacterData(eventReader);
                    } else if (localPart.equals(DESCRIPTION)) {
                        description = getCharacterData(eventReader);
                    } else if (localPart.equals(LINK)) {
                        link = getCharacterData(eventReader);
                    } else if (localPart.equals(GUID)) {
                        guid = getCharacterData(eventReader);
                    } else if (localPart.equals(LANGUAGE)) {
                        language = getCharacterData(eventReader);
                    } else if (localPart.equals(AUTHOR)) {
                        author = getCharacterData(eventReader);
                    } else if (localPart.equals(PUB_DATE)) {
                        pubdate = getCharacterData(eventReader);
                    } else if (localPart.equals(COPYRIGHT)) {
                        copyright = getCharacterData(eventReader);
                    }

                } else if (event.isEndElement()) {
                    if (event.asEndElement().getName().getLocalPart().equals(ITEM)) {
                        FeedMessage message = new FeedMessage();
                        message.setAuthor(author);
                        message.setDescription(description);
                        message.setGuid(guid);
                        message.setLink(link);
                        message.setTitle(title);
                        feed.getMessages().add(message);
                    }
                }
            }
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
        return feed;
    }

    private String getCharacterData(XMLEventReader eventReader)
            throws XMLStreamException {
        String result = "";
        XMLEvent event = eventReader.nextEvent();
        if (event instanceof Characters) {
            result = event.asCharacters().getData();
        }
        return result;
    }

    private InputStream read() {
        try {
            return url.openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public List<FeedMessage> getNewArticles(List<FeedMessage> oldFeed) {
        List<FeedMessage> currentFeed = readFeed().getMessages();
        List<FeedMessage> newFeed = readFeed().getMessages();
        //LUL
        newFeed.clear();

        if (readFeed() == oldFeed) {
            return oldFeed;
        } else {
            int i = 0;
            while (!(currentFeed.get(i) == oldFeed.get(0))) {
                newFeed.add(0, currentFeed.get(i));
                i++;
            }
            return newFeed;
        }

    }
}