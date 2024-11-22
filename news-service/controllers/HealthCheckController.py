from fastapi import APIRouter, HTTPException
from services.HealthCheckService import HealthCheckService


class HealthCheckController:
    def __init__(self, healthCheckService: HealthCheckService) -> None:
        self.router = APIRouter()
        self.healthCheckService = healthCheckService
        self.healthCheckService = HealthCheckService()
        self.router.add_api_route(
            "/check", self.healthCheck, response_model=dict, methods=["GET"]
        )

    async def healthCheck(self):
        try:
            return self.healthCheckService.healthCheck()
        except Exception as e:
            raise HTTPException(status_code=500, detail=str(e))
