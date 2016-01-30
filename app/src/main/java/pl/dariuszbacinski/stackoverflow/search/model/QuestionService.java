package pl.dariuszbacinski.stackoverflow.search.model;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pl.dariuszbacinski.stackoverflow.BuildConfig;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;
import static rx.Observable.from;

public class QuestionService {

    private final QuestionApiService questionApiService;

    public QuestionService() {
        //TODO move to dagger module
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG ? BODY : NONE);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.stackexchange.com/2.2/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        this.questionApiService = retrofit.create(QuestionApiService.class);
    }

    public QuestionService(QuestionApiService questionApiService) {
        this.questionApiService = questionApiService;
    }

    public Observable<Question> searchByTitle(final String query, Sort sort, Order order) {
        validateQuery(query);
        return questionApiService.searchByTitle(query, sort, order).flatMap(new Func1<ResponseContainer<Question>, Observable<Question>>() {
            @Override
            public Observable<Question> call(ResponseContainer<Question> questionResponseContainer) {
                return from(questionResponseContainer.items);
            }
        }).subscribeOn(Schedulers.io());
    }

    private void validateQuery(String query) {
        if (query == null || query.isEmpty()) {
            throw new IllegalArgumentException("Query must be set!");
        }
    }
}
