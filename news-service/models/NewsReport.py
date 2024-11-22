import datetime

from pydantic import BaseModel
from pydantic_core import Url


class NewsReport(BaseModel):
    title: str
    description: str
    publisher: str
    link: Url
    date: str

    @property
    def age(self) -> int:
        publishedDate = datetime.fromisoformat(self.date)
        return (datetime.now() - publishedDate).days
