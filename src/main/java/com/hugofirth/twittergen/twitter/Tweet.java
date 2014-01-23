package com.hugofirth.twittergen.twitter;


/**
 * The type Tweet.
 */
public class Tweet implements NodeInterface {

    private Long nodeId;
    //Relationships
    private User sentBy; // (This)<-Sent-(User)
    private User mentions; // (This)-Mentions->(User)
    private Tweet retweets; // (This)-Retweets->(Tweet)
    private Hashtag contains; // (This)-Contains->(Hashtag)

    /**
     * Constructor - private to ensure non-instantiability
     *
     * @param b the Tweet builder object
     */
    private Tweet(Builder b)
    {
        this.sentBy = b.sentBy;
        this.mentions = b.mentions;

        this.retweets = b.retweets;
        this.contains = b.contains;
    }

    /**
     * Gets sent by User.
     *
     * @return the sent by User
     */
    public User getSentBy() {
        return sentBy;
    }

    /**
     * Gets mentioned User.
     *
     * @return the mentioned User
     */
    public User getMentions() {
        return mentions;
    }

    /**
     * Sets mentioned User.
     *
     * @param mentions the mentioned User
     */
    public void setMentions(User mentions) {
        this.mentions = mentions;
    }

    /**
     * Gets retweeted Tweet.
     *
     * @return the retweeted Tweet
     */
    public Tweet getRetweets() {
        return retweets;
    }

    /**
     * Sets retweeted Tweet.
     *
     * @param retweets the retweeted Tweet
     */
    public void setRetweets(Tweet retweets) {
        this.retweets = retweets;
    }

    /**
     * Gets contained Hashtag.
     *
     * @return the contained Hashtag
     */
    public Hashtag getContains() {
        return contains;
    }

    /**
     * Sets contained Hashtag.
     *
     * @param contains the contained Hashtag
     */
    public void setContains(Hashtag contains) {
        this.contains = contains;
    }

    @Override
    public Long getNodeId() {
        return nodeId;
    }

    @Override
    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    /**
     * Compares an object with this
     *
     * @param o the Object being compared to this
     * @return boolean representing this==o
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tweet tweet = (Tweet) o;

        if (contains != null ? !contains.equals(tweet.contains) : tweet.contains != null) return false;
        if (mentions != null ? !mentions.equals(tweet.mentions) : tweet.mentions != null) return false;
        if (retweets != null ? !retweets.equals(tweet.retweets) : tweet.retweets != null) return false;
        if (!sentBy.equals(tweet.sentBy)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sentBy.hashCode();
        result = 31 * result + (mentions != null ? mentions.hashCode() : 0);
        result = 31 * result + (retweets != null ? retweets.hashCode() : 0);
        result = 31 * result + (contains != null ? contains.hashCode() : 0);
        return result;
    }

    //TODO: update Builder to account for the fact that objects need to be "told" that they are contained within a tweet

    /**
     * The type Builder.
     */
    public static class Builder {

        private User sentBy;
        private User mentions;
        private Tweet retweets;
        private Hashtag contains;

        /**
         * Sets sentBy User field.
         *
         * @param u the sent by User
         * @return Builder object for chaining
         */
        public Builder setSentBy(User u)
        {
            sentBy = u;
            return this;
        }

        /**
         * Sets mentions User field.
         *
         * @param u the mentioned User
         * @return Builder object for chaining
         */
        public Builder setMentions(User u)
        {
            mentions = u;
            return this;
        }

        /**
         * Sets retweets Tweet field.
         *
         * @param t the retweeted tweet
         * @return Builder object for chaining
         */
        public Builder setRetweets(Tweet t)
        {
            retweets = t;
            return this;
        }

        /**
         * Sets contains Hashtag field.
         *
         * @param h the contained hashtag
         * @return Builder object for chaining
         */
        public Builder setContains(Hashtag h)
        {
            contains = h;
            return this;
        }

        /**
         * Build tweet.
         *
         * @return the Tweet object¡
         */
        public Tweet build()
        {
            return new Tweet(this);
        }
    }

}
