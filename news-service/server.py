import uvicorn
from controllers.HealthCheckController import HealthCheckController
from controllers.NewsController import NewsController
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from services.HealthCheckService import HealthCheckService
from services.NewsService import NewsService

app = FastAPI(root_path="/api")

origins = ["*"]

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

newsController = NewsController(newsService=NewsService())
healthCheckController = HealthCheckController(healthCheckService=HealthCheckService())

app.include_router(newsController.router, prefix="/news")
app.include_router(healthCheckController.router, prefix="/health")


if __name__ == "__main__":
    uvicorn.run(app="server:app", host="0.0.0.0", port=8085, reload=True)
