import http


class HealthCheckService:
    @staticmethod
    def healthCheck() -> dict:
        return {"status": http.HTTPStatus.OK}
