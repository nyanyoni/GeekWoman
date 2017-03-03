package geekwoman;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(jp.co.geekwoman.R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(jp.co.geekwoman.R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));// XMLに書く？

        fetchTweets();
    }

    private void fetchTweets() {
        // AsyncTaskあまりつかわないけど合ってるのだろうか…
        AsyncTask<Void, Void, QueryResult> task = new AsyncTask<Void, Void, QueryResult>() {
            @Override
            protected QueryResult doInBackground(Void... voids) {

                Twitter twitter = initTwitterFactory();
                Query query = new Query();
                query.setQuery("#gwjpms");

                QueryResult result = null;
                try {
                    result = twitter.search(query);
                } catch (TwitterException e) {
                    e.printStackTrace();
                }

                return result;
            }

            @Override
            protected void onPostExecute(QueryResult result) {
                // adapter class作ったけどMainActivityに書いた方がいいかな？(；・∀・)
                MyAdapter adapter = new MyAdapter(MainActivity.this, result);
                mRecyclerView.setAdapter(adapter);
            }
        };
        task.execute();
    }

    private Twitter initTwitterFactory() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("eFOXsAfbnphdURq9pczQDONBy")
                .setOAuthConsumerSecret("IecUv0ClLf99c7gjkxZ88rJVuNjYdlvvIjDsp82LAim0ztvWFv")
                .setOAuthAccessToken("4811124756-r9FgoXBsjAWSV4SjKZMEtCDKmKmH1Oz6ZUuXPva")
                .setOAuthAccessTokenSecret("JXaofq7DZtu8u1XrE747FGV8aGYssRrMtmkihSzogEC3b");

        TwitterFactory factory = new TwitterFactory(cb.build());
        return factory.getInstance();
    }
}
