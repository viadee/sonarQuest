export interface SonarQubeConfig {
  name: string;
  sonarServerUrl: string;
  httpBasicAuthUsername?: string;
  httpBasicAuthPassword?: string;
}
