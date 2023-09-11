import http from 'k6/http';
import { check } from 'k6';

export default function () {

  var apiKey = `${__ENV.API_KEY}`
  var incorrectApiKey = 'foo'
  var hostName = `${__ENV.HOST_NAME}`
  var testExistingToken = `${__ENV.TEST_EXISTING_TOKEN}`
  var testNotExistingToken = `${__ENV.TEST_NOT_EXISTING_TOKEN}`
  var testNotAllowedToken = `${__ENV.TEST_NOT_ALLOWED_TOKEN}`
  var correctTraceId = 'Root=1-10af8d44-8001aebdbc7c7d1321082928'
  var malformedTraceId = 'Root=1-10af8d4-8001aebdbc7c7d1321082928'

  var requests = [
  {
    method: 'GET',
    url: `https://${hostName}/user-registry/v1/users/${testExistingToken}?fl=fiscalCode,email`,
    params: {
      headers: { 
        'accept': 'application/json',
        'x-api-key': `${apiKey}`,
      },
    },
    test_assertions_params: {
      name: 'Get user from existing token',
      expected_status: 200,
      description: 'This test should return a 200 status code because we are querying an existing token for the current api-key',
    }
  },
  {
    method: 'GET',
    url: `https://${hostName}/user-registry/v1/users/${testNotExistingToken}?fl=fiscalCode,email`,
    params: {
      headers: { 
        'accept': 'application/json',
        'x-api-key': `${apiKey}`,
      },
    },
    test_assertions_params: {
      name: 'Get user from not existing token',
      expected_status: 404,
      description: 'This test should return a 404 status code because we are querying a not existing token for the current api-key',
    }
  },
  {
    method: 'GET',
    url: `https://${hostName}/user-registry/v1/users/${testExistingToken}?fl=fiscalCode,email`,
    params: {
      headers: { 
        'accept': 'application/json',
        'x-api-key': `${apiKey}`,
        'x-amzn-trace-id': `${correctTraceId}`,
      },
    },
    test_assertions_params: {
      name: 'Get user when a valid x-amzn-trace-id is provided',
      expected_status: 200,
      description: 'This test should return a 200 status code because we are sending a valid x-amzn-trace-id',
    }
  },
  {
    method: 'GET',
    url: `https://${hostName}/user-registry/v1/users/${testExistingToken}?fl=fiscalCode,email`,
    params: {
      headers: { 
        'accept': 'application/json',
        'x-api-key': `${apiKey}`,
        'x-amzn-trace-id': `${malformedTraceId}`,
      },
    },
    test_assertions_params: {
      name: 'Get user when a malformed x-amzn-trace-id is provided',
      expected_status: 200,
      description: 'This test should return a 200 status code because we should be resilient to request with a malformed x-amzn-trace-id',
    }
  },
  {
    method: 'GET',
    url: `https://${hostName}/user-registry/v1/users/${testNotAllowedToken}?fl=fiscalCode,email`,
    params: {
      headers: { 
        'accept': 'application/json',
        'x-api-key': `${apiKey}`,
      },
    },
    test_assertions_params: {
      name: 'Get user when a not allowed Token is provided',
      expected_status: 404,
      description: 'This test should return a 404 status code because we are querying a token belonging to a different api key',
    }
  },
  {
    method: 'POST',
    url: `https://${hostName}/user-registry/v1/users/search?fl=birthDate,email`,
    body: JSON.stringify({fiscalCode: 'string'}),
    params: {
      headers: { 
        'Content-Type': 'application/json',
        'x-api-key': `${apiKey}`,
      },
    },
    test_assertions_params: {
      name: 'Get user from pii',
      expected_status: 200,
      description: 'This test should return a 200 status code',
    }
  },
  {
    method: 'POST',
    url: `https://${hostName}/user-registry/v1/users/search`,
    body: JSON.stringify({invalid_parameter: 'string'}),
    params: {
      headers: { 
        'Content-Type': 'application/json',
        'x-api-key': `${apiKey}`,
      },
    },
    test_assertions_params: {
      name: 'Incorrect POST request',
      expected_status: 400,
      description: 'This test should return a 400 status code because the body parameter is invalid',
    }
  },
  {
    method: 'PATCH',
    url: `https://${hostName}/user-registry/v1/users`,
    body: JSON.stringify(
      {
        birthDate: {
          certification: "NONE",
          value: "2023-08-09"
        },
        email: {
          certification: "NONE",
          value: "string"
        },
        familyName: {
          certification: "NONE",
          value: "string"
        },
        fiscalCode: "AAAABBBBB",
        name: {
          certification: "NONE",
          value: "string"
        },
        workContacts: {
          additionalProp1: {
            email: {
              certification: "NONE",
              value: "string"
            }
          },
          additionalProp2: {
            email: {
              certification: "NONE",
              value: "string"
            }
          },
          additionalProp3: {
            email: {
              certification: "NONE",
              value: "string"
            }
          }
        }
      }
    ),
    params: {
      headers: { 
        'Content-Type': 'application/json',
        'x-api-key': `${apiKey}`,
      },
    },
    test_assertions_params: {
      name: 'Upsert user',
      expected_status: 200,
      description: 'This test should return a 200 status code',
    }
  },
  {
    method: 'PATCH',
    url: `https://${hostName}/user-registry/v1/users/${testExistingToken}`,
    body: JSON.stringify(
      {
        birthDate: {
          certification: "NONE",
          value: "2023-08-09"
        },
        email: {
          certification: "NONE",
          value: "string"
        },
        familyName: {
          certification: "NONE",
          value: "string"
        },
        name: {
          certification: "NONE",
          value: "string"
        },
        workContacts: {
          additionalProp1: {
            email: {
              certification: "NONE",
              value: "string"
            }
          },
          additionalProp2: {
            email: {
              certification: "NONE",
              value: "string"
            }
          },
          additionalProp3: {
            email: {
              certification: "NONE",
              value: "string"
            }
          }
        }
      }
    ),
    params: {
      headers: { 
        'Content-Type': 'application/json',
        'x-api-key': `${apiKey}`,
      },
    },
    test_assertions_params: {
      name: 'Upsert user',
      expected_status: 204,
      description: 'This test should return a 204 status code',
    }
  },
  {
    method: 'GET',
    url: `https://${hostName}/user-registry/v1/users/${testExistingToken}?fl=fiscalCode,email`,
    params: {
      headers: { 
        'accept': 'application/json',
        'x-api-key': `${incorrectApiKey}`,
      },
    },
    test_assertions_params: {
      name: 'GET with incorrect api key',
      expected_status: 403,
      description: 'This test should return a 403 status code because an incorrect API key is provided',
    }
  },
]

  const responses = http.batch(requests);

  for(let i = 0; i < responses.length; i++) {
    check(
      responses[i],{
        'is status correct': (res) => res.status === requests[i].test_assertions_params.expected_status,
      }
    )
    if (responses[i].status != requests[i].test_assertions_params.expected_status) {
      console.log(`| Test failed: ${requests[i].test_assertions_params.name} | Expected status code: ${requests[i].test_assertions_params.expected_status} | Current status code: ${responses[i].status} |`);
    }
  }
}

export function handleSummary(data) {

  const checks = data.metrics.checks.values;
  const http_requests = data.metrics.http_reqs.values.count;
  const checks_message = `\nNumber of requests: ${http_requests}, Passes: ${checks.passes}, Failed: ${checks.fails}.\n`;

  return {
    stdout: checks_message,
    "./tmp.json": JSON.stringify({failed: checks.fails===0 ? 0: 1})
  };
}