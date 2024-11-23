from typing import List

from fastapi import APIRouter, HTTPException, Query

from models.NewsReport import NewsReport
from services.NewsService import NewsService


class NewsController:
    def __init__(self, newsService: NewsService) -> None:
        self.router = APIRouter()
        self.newsService = newsService
        self.router.add_api_route(
            "/all/paginated",
            self.getPaginatedNews,
            response_model=List[NewsReport],
            methods=["GET"],
        )

    async def getPaginatedNews(
        self,
        pageIndex: int = Query(1, ge=1, description="page number, starting from 0"),
        itemsPerPage: int = Query(
            10, ge=1, le=20, description="number of items per page (max 20)"
        ),
    ):
        try:
            return self.newsService.getPaginatedNewsReports(pageIndex, itemsPerPage)
        except Exception as e:
            raise HTTPException(status_code=500, detail=str(e))
