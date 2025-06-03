import { inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

export function loadCsrfToken(): () => Promise<void> {
  const http = inject(HttpClient);
  return () =>
    http.get('/api/auth/csrf', { withCredentials: true }).toPromise().then(() => {
      console.log('[CSRF] Token initialized');
    }).catch((error) => {
      console.error('[CSRF] Initialization failed:', error);
    });
}
