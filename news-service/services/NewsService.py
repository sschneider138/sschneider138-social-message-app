import os
from typing import List

from dotenv import load_dotenv
from models.NewsReport import NewsReport
from newsapi import NewsApiClient

# load env vars
load_dotenv()
NEWS_API_KEY = str(os.getenv("NEWS_API_KEY"))

# instantiate news api third party service client
newsAPI = NewsApiClient(api_key=NEWS_API_KEY)


class NewsService:
    def __init__(self) -> None:
        self.client = newsAPI

    def getPaginatedNewsReports(
        self, pageIndex: int, itemsPerPage: int = 10
    ) -> List[NewsReport]:
        response = self.client.get_top_headlines(
            language="en", country="us", page=pageIndex, page_size=itemsPerPage
        )

        print(f"api response: {response}")

        if response.get("status") != "ok":
            raise Exception(f"failed to fetch news {response.get('message')}")

        articles = response.get("articles", [])

        # filter out articles that contain dead fields (represented with ["Removed"] in this api)
        filteredArticles = [
            article
            for article in articles
            if not any(value == "[Removed]" for value in article.values())
        ]
        newsReports: List[NewsReport] = []

        for article in filteredArticles:
            try:
                newsReports.append(
                    NewsReport(
                        title=article["title"],
                        description=article["description"],
                        publisher=article["source"]["name"],
                        link=article["url"],
                        date=article["publishedAt"],
                    )
                )
            except Exception as e:
                print(f"skipping article due to missing field: {e}")

        return newsReports
